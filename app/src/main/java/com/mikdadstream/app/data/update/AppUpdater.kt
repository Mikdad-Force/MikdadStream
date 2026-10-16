package com.mikdadstream.app.data.update

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.content.FileProvider
import com.mikdadstream.app.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.util.concurrent.TimeUnit

data class UpdateInfo(
    val version: String,
    val downloadUrl: String,
    val pageUrl: String,
    val notes: String?,
    val sizeBytes: Long?,
)

sealed interface UpdateState {
    data object Idle : UpdateState
    data object Checking : UpdateState
    data object UpToDate : UpdateState
    data class Available(val info: UpdateInfo) : UpdateState
    data class Failed(val message: String) : UpdateState
}

/**
 * Pemeriksa & pemasang pembaruan aplikasi.
 *
 * Sumber pembaruan WAJIB dari GitHub rilis repo (lihat [BuildConfig.UPDATE_REPO]):
 * memakai endpoint `releases/latest`, mengambil aset APK, lalu memasangnya
 * lewat FileProvider. Cek yang gagal (mis. offline) tidak memblokir aplikasi.
 */
class AppUpdater(context: Context) {

    private val appContext = context.applicationContext

    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val _state = MutableStateFlow<UpdateState>(UpdateState.Idle)
    val state: StateFlow<UpdateState> = _state

    private val _downloading = MutableStateFlow(false)
    val downloading: StateFlow<Boolean> = _downloading

    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress

    private val repo: String = BuildConfig.UPDATE_REPO

    val latestUrl: String get() = "https://github.com/$repo/releases/latest"

    /** Cek rilis terbaru di GitHub. Mengembalikan info bila ada versi lebih baru. */
    suspend fun check(): UpdateInfo? = withContext(Dispatchers.IO) {
        _state.value = UpdateState.Checking
        try {
            val request = Request.Builder()
                .url("https://api.github.com/repos/$repo/releases/latest")
                .header("Accept", "application/vnd.github+json")
                .header("User-Agent", "MikdadStream/${BuildConfig.VERSION_NAME}")
                .build()
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    _state.value = UpdateState.Failed("HTTP ${response.code}")
                    return@withContext null
                }
                val json = JSONObject(response.body?.string().orEmpty())
                val remote = normalize(json.optString("tag_name").ifBlank { json.optString("name") })
                val current = normalize(BuildConfig.VERSION_NAME)
                if (remote.isEmpty() || !isNewer(remote, current)) {
                    _state.value = UpdateState.UpToDate
                    return@withContext null
                }
                val asset = pickApk(json.optJSONArray("assets"))
                    ?: run {
                        _state.value = UpdateState.Failed("Aset APK tidak ditemukan")
                        return@withContext null
                    }
                val info = UpdateInfo(
                    version = remote,
                    downloadUrl = asset.optString("browser_download_url"),
                    pageUrl = json.optString("html_url").ifBlank { latestUrl },
                    notes = json.optString("body").ifBlank { null },
                    sizeBytes = asset.optLong("size").takeIf { it > 0 },
                )
                _state.value = UpdateState.Available(info)
                return@withContext info
            }
        } catch (e: Exception) {
            _state.value = UpdateState.Failed(e.message ?: "Gagal memeriksa pembaruan")
            return@withContext null
        }
    }

    private fun pickApk(assets: JSONArray?): JSONObject? {
        if (assets == null || assets.length() == 0) return null
        val list = (0 until assets.length()).mapNotNull { assets.optJSONObject(it) }
        val apks = list.filter { it.optString("name").endsWith(".apk", ignoreCase = true) }
        return apks.firstOrNull { it.optString("name").contains("release", ignoreCase = true) }
            ?: apks.firstOrNull()
    }

    /** Unduh APK pembaruan ke cache. Mengembalikan berkas bila berhasil. */
    suspend fun download(info: UpdateInfo): File? = withContext(Dispatchers.IO) {
        _downloading.value = true
        _progress.value = 0f
        try {
            val request = Request.Builder()
                .url(info.downloadUrl)
                .header("User-Agent", "MikdadStream/${BuildConfig.VERSION_NAME}")
                .build()
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return@withContext null
                val body = response.body ?: return@withContext null
                val total = body.contentLength().takeIf { it > 0 } ?: (info.sizeBytes ?: -1L)
                val dir = File(appContext.cacheDir, "updates").apply { mkdirs() }
                val out = File(dir, "mikdadstream-${info.version}.apk")
                body.byteStream().use { input ->
                    out.outputStream().use { stream ->
                        val buffer = ByteArray(64 * 1024)
                        var done = 0L
                        while (true) {
                            val read = input.read(buffer)
                            if (read <= 0) break
                            stream.write(buffer, 0, read)
                            done += read
                            if (total > 0) _progress.value = (done.toFloat() / total).coerceIn(0f, 1f)
                        }
                    }
                }
                _progress.value = 1f
                return@withContext out
            }
        } catch (e: Exception) {
            return@withContext null
        } finally {
            _downloading.value = false
        }
    }

    fun canInstall(): Boolean =
        Build.VERSION.SDK_INT < Build.VERSION_CODES.O ||
            appContext.packageManager.canRequestPackageInstalls()

    fun install(file: File) {
        val uri: Uri = FileProvider.getUriForFile(
            appContext,
            "${appContext.packageName}.updates",
            file,
        )
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/vnd.android.package-archive")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        appContext.startActivity(intent)
    }

    fun openInstallPermission() {
        runCatching {
            appContext.startActivity(
                Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
                    .setData(Uri.parse("package:${appContext.packageName}"))
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
            )
        }
    }

    fun openPage(url: String = latestUrl) {
        runCatching {
            appContext.startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
            )
        }
    }

    companion object {
        fun normalize(version: String): String = version
            .trim()
            .removePrefix("v")
            .removePrefix("V")
            .substringBefore('-')
            .substringBefore('+')

        fun isNewer(remote: String, current: String): Boolean {
            val a = remote.split('.').map { it.takeWhile(Char::isDigit).toIntOrNull() ?: 0 }
            val b = current.split('.').map { it.takeWhile(Char::isDigit).toIntOrNull() ?: 0 }
            for (i in 0 until maxOf(a.size, b.size)) {
                val x = a.getOrElse(i) { 0 }
                val y = b.getOrElse(i) { 0 }
                if (x != y) return x > y
            }
            return false
        }
    }
}
