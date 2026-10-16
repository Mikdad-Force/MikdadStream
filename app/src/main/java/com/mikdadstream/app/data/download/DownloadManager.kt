package com.mikdadstream.app.data.download

import android.content.Context
import android.os.StatFs
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mikdadstream.app.data.local.DownloadDao
import com.mikdadstream.app.data.local.DownloadEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit

/** Ringkasan kapasitas penyimpanan perangkat (lokasi unduhan). */
data class StorageInfo(
    val totalBytes: Long,
    val freeBytes: Long,
) {
    val usedBytes: Long get() = (totalBytes - freeBytes).coerceAtLeast(0L)
    val usedFraction: Float get() = if (totalBytes > 0) usedBytes.toFloat() / totalBytes else 0f
}

/**
 * Manajer unduhan: mengunduh file video ke penyimpanan aplikasi, menyimpan
 * progres ke Room, dan menyediakan info sisa penyimpanan.
 */
class DownloadManager(
    context: Context,
    private val dao: DownloadDao,
) {
    private val appContext = context.applicationContext
    private val gson = Gson()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val queue = Channel<String>(Channel.UNLIMITED)
    private val client = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .followRedirects(true)
        .build()

    val downloads: Flow<List<DownloadEntity>> = dao.observeAll()

    init {
        repeat(2) {
            scope.launch {
                for (key in queue) {
                    runCatching { download(key) }
                }
            }
        }
    }

    private fun dir(): File {
        val base = appContext.getExternalFilesDir(null) ?: appContext.filesDir
        val d = File(base, "downloads")
        if (!d.exists()) d.mkdirs()
        return d
    }

    fun storageInfo(): StorageInfo {
        val target = appContext.getExternalFilesDir(null) ?: appContext.filesDir
        return runCatching {
            val stat = StatFs(target.path)
            StorageInfo(stat.totalBytes, stat.availableBytes)
        }.getOrDefault(StorageInfo(0L, 0L))
    }

    fun enqueue(entry: DownloadEntity) {
        scope.launch {
            dao.upsert(entry.copy(status = STATUS_QUEUED, bytesDone = 0L, progress = 0f))
            queue.trySend(entry.key)
        }
    }

    fun remove(key: String, filePath: String?) {
        scope.launch {
            filePath?.let { path -> runCatching { File(path).delete() } }
            runCatching { File(filePath + ".part").delete() }
            dao.delete(key)
        }
    }

    fun clearAll() {
        scope.launch {
            runCatching { dir().listFiles()?.forEach { it.delete() } }
            dao.clear()
        }
    }

    private suspend fun download(key: String) {
        val item = dao.find(key) ?: return
        if (item.url.isBlank()) {
            dao.upsert(item.copy(status = STATUS_FAILED, progress = 0f))
            return
        }
        dao.upsert(item.copy(status = STATUS_DOWNLOADING))

        val ext = when ((item.format ?: "").lowercase()) {
            "hls", "m3u8" -> "m3u8"
            "dash", "mpd" -> "mpd"
            "flv" -> "flv"
            else -> "mp4"
        }
        val name = buildString {
            append(sanitize(item.title))
            append("-ep").append(item.episodeNumber ?: 1)
            append("-").append(sanitize(item.quality ?: "sd"))
            append(".").append(ext)
        }
        val file = File(dir(), name)
        val tmp = File(file.absolutePath + ".part")

        try {
            val builder = Request.Builder().url(item.url)
            parseHeaders(item.headersJson).forEach { (k, v) -> builder.header(k, v) }
            client.newCall(builder.build()).execute().use { resp ->
                if (!resp.isSuccessful) throw IllegalStateException("HTTP ${resp.code}")
                val body = resp.body ?: throw IllegalStateException("Respons kosong")
                val total = body.contentLength().let { if (it > 0) it else item.bytesTotal }
                var done = 0L
                var lastEmit = 0L
                FileOutputStream(tmp).use { out ->
                    body.byteStream().use { input ->
                        val buf = ByteArray(64 * 1024)
                        var n: Int
                        while (input.read(buf).also { n = it } > 0) {
                            out.write(buf, 0, n)
                            done += n
                            val now = System.currentTimeMillis()
                            if (now - lastEmit > 400L) {
                                lastEmit = now
                                val prog = if (total > 0) (done.toFloat() / total).coerceIn(0f, 1f) else 0f
                                dao.updateProgress(key, done, total, prog, STATUS_DOWNLOADING)
                            }
                        }
                    }
                    out.flush()
                }
            }
            if (file.exists()) file.delete()
            if (!tmp.renameTo(file)) {
                tmp.copyTo(file, overwrite = true)
                tmp.delete()
            }
            val size = file.length()
            dao.find(key)?.let { current ->
                dao.upsert(
                    current.copy(
                        status = STATUS_DONE,
                        filePath = file.absolutePath,
                        bytesDone = size,
                        bytesTotal = size,
                        progress = 1f,
                    )
                )
            }
        } catch (e: Exception) {
            runCatching { tmp.delete() }
            dao.find(key)?.let { current ->
                dao.upsert(current.copy(status = STATUS_FAILED))
            }
        }
    }

    private fun parseHeaders(json: String?): Map<String, String> {
        if (json.isNullOrBlank()) return emptyMap()
        return runCatching {
            val type = object : TypeToken<Map<String, String>>() {}.type
            gson.fromJson<Map<String, String>>(json, type) ?: emptyMap()
        }.getOrDefault(emptyMap())
    }

    private fun sanitize(text: String): String =
        text.trim().replace(Regex("[^A-Za-z0-9._-]+"), "_").take(40).ifBlank { "video" }

    companion object {
        const val STATUS_QUEUED = "queued"
        const val STATUS_DOWNLOADING = "downloading"
        const val STATUS_DONE = "done"
        const val STATUS_FAILED = "failed"
    }
}
