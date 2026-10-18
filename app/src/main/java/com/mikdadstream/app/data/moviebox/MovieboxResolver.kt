package com.mikdadstream.app.data.moviebox

import android.os.Build
import com.mikdadstream.app.data.remote.MediaStream
import com.mikdadstream.app.data.remote.SourceData
import java.security.MessageDigest
import java.util.Base64
import java.util.Locale
import java.util.UUID
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.concurrent.withLock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Headers.Companion.toHeaders
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

/**
 * Resolver Moviebox langsung dari aplikasi (signed V3 client).
 *
 * Aplikasi membuat request bertanda tangan ke server Moviebox seolah klien
 * resmi; URL bertoken di katalog yang kedaluwarsa diperbarui on-the-fly.
 */
object MovieboxResolver {

    private const val TAG = "MovieboxResolver"

    private val HOSTS = listOf(
        "https://api7.aoneroom.com",
        "https://api6.aoneroom.com",
        "https://api.inmoviebox.com",
        "https://api5.aoneroom.com",
        "https://api4sg.aoneroom.com",
        "https://api4.aoneroom.com",
        "https://api3.aoneroom.com",
    )

    private const val BOOT_PATH = "/wefeed-mobile-bff/tab-operating"
    private const val PLAY_INFO_PATH = "/wefeed-mobile-bff/subject-api/play-info"

    private const val SECRET_DEFAULT = "76iRl07s0xSN9jqmEWAt79EBJZulIQIsV64FZr2O"
    private const val SIGN_BODY_MAX = 102400

    // Stream di bawah durasi ini dianggap video trap (anti-piracy).
    private const val MIN_DURATION_SECONDS = 60L

    private val http: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(12, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .build()

    private val tokenLock = ReentrantLock()
    private var guestToken: String? = null
    private var activeHost: String? = null

    private val deviceId: String = UUID.randomUUID().toString().replace("-", "").take(32)

    private fun androidUa(): String {
        val versionCode = 50020045
        return "com.community.oneroom/$versionCode (Linux; U; Android ${Build.VERSION.RELEASE}; " +
            "en_US; ${Build.MODEL}; Build/${Build.BRAND}_${Build.DEVICE}; Cronet/135.0.7012.3)"
    }

    private fun clientInfo(): String {
        val info = JSONObject()
        info.put("package_name", "com.community.oneroom")
        info.put("version_name", "3.0.03.0529.03")
        info.put("version_code", 50020045)
        info.put("os", "android")
        info.put("os_version", Build.VERSION.RELEASE ?: "12")
        info.put("install_ch", "ps")
        info.put("device_id", deviceId)
        info.put("install_store", "ps")
        info.put("gaid", UUID.randomUUID().toString())
        info.put("brand", Build.BRAND ?: "Redmi")
        info.put("model", Build.MODEL ?: "M2012K11AG")
        info.put("system_language", Locale.getDefault().language.ifBlank { "en" })
        info.put("net", "NETWORK_WIFI")
        info.put("region", "US")
        info.put("timezone", java.util.TimeZone.getDefault().id)
        info.put("sp_code", Locale.getDefault().country.ifBlank { "40401" })
        info.put("X-Play-Mode", "2")
        return info.toString()
    }

    private fun md5Hex(bytes: ByteArray): String =
        MessageDigest.getInstance("MD5").digest(bytes).joinToString("") { "%02x".format(it) }

    private fun hmacMd5(key: ByteArray, data: ByteArray): ByteArray {
        val mac = Mac.getInstance("HmacMD5")
        mac.init(SecretKeySpec(key, "HmacMD5"))
        return mac.doFinal(data)
    }

    private fun canonical(
        method: String,
        accept: String,
        contentType: String,
        pathAndQuery: String,
        body: String?,
        tsMs: Long,
    ): String {
        val qIdx = pathAndQuery.indexOf('?')
        val path = if (qIdx >= 0) pathAndQuery.substring(0, qIdx) else pathAndQuery
        val q = if (qIdx >= 0) pathAndQuery.substring(qIdx + 1) else ""
        val pairs = if (q.isBlank()) emptyList<String>() else q.split('&')
            .filter { it.isNotBlank() }
            .map { val i = it.indexOf('='); if (i >= 0) it.substring(0, i) to it.substring(i + 1) else it to "" }
            .sortedBy { it.first }
            .map { "${it.first}=${it.second}" }
        val canonicalUrl = if (pairs.isEmpty()) path else "$path?${pairs.joinToString("&")}"
        val bodyBytes = body?.toByteArray(Charsets.UTF_8)
        val bodyHash = bodyBytes?.takeIf { it.isNotEmpty() }
            ?.let { md5Hex(if (it.size > SIGN_BODY_MAX) it.copyOf(SIGN_BODY_MAX) else it) } ?: ""
        val bodyLen = bodyBytes?.size?.toString() ?: ""
        return listOf(
            method.uppercase(),
            accept,
            contentType,
            bodyLen,
            tsMs.toString(),
            bodyHash,
            canonicalUrl,
        ).joinToString("\n")
    }

    private fun signedHeaders(
        method: String,
        pathAndQuery: String,
        body: String?,
        auth: String?,
        accept: String = "application/json",
        contentType: String = "application/json",
        playMode: Boolean = false,
    ): Map<String, String> {
        val ts = System.currentTimeMillis()
        val headers = mutableMapOf(
            "User-Agent" to androidUa(),
            "Accept" to accept,
            "Content-Type" to contentType,
            "Connection" to "keep-alive",
            "X-Client-Info" to clientInfo(),
            "X-Client-Status" to "0",
            "X-Client-Token" to ("$ts," +
                md5Hex(ts.toString().reversed().toByteArray(Charsets.UTF_8))),
            "x-tr-signature" to run {
                val canon = canonical(method, accept, contentType, pathAndQuery, body, ts)
                val mac = hmacMd5(Base64.getDecoder().decode(SECRET_DEFAULT), canon.toByteArray(Charsets.UTF_8))
                "$ts|2|" + Base64.getEncoder().encodeToString(mac)
            },
        )
        auth?.takeIf { it.isNotBlank() }?.let { headers["Authorization"] = "Bearer $it" }
        if (playMode) headers["X-Play-Mode"] = "2"
        return headers
    }

    private fun callJson(
        base: String,
        pathAndQuery: String,
        body: String?,
        auth: String?,
        playMode: Boolean,
    ): JSONObject? {
        val method = if (body != null) "POST" else "GET"
        val headers = signedHeaders(method, pathAndQuery, body, auth, playMode = playMode)
        val builder = Request.Builder()
            .url(base + pathAndQuery)
            .headers(headers.toHeaders())
        if (body != null) builder.post(body.toRequestBody("application/json".toMediaType()))
        return try {
            http.newCall(builder.build()).execute().use { resp ->
                resp.header("x-user")?.let { xu ->
                    runCatching {
                        JSONObject(xu).optString("token", "").takeIf { it.isNotBlank() }
                    }.getOrNull()?.let { tok ->
                        tokenLock.withLock { guestToken = tok }
                    }
                }
                resp.body?.string()?.let { JSONObject(it) }
            }
        } catch (t: Throwable) {
            null
        }
    }

    private fun boot(): String? {
        tokenLock.withLock { guestToken?.let { if (it.isNotBlank()) return it } }
        for (base in HOSTS) {
            val root = callJson(base, "$BOOT_PATH?page=1&tabId=0&version=", null, null, false)
                ?: continue
            if (root.optInt("code", -1) == 0) {
                val token = root.optJSONObject("data")?.optString("token")?.takeIf { it.isNotBlank() }
                if (token != null) {
                    tokenLock.withLock {
                        guestToken = token
                        activeHost = base
                    }
                    android.util.Log.i(TAG, "bootstrap ok host=$base")
                    return token
                }
            }
        }
        return null
    }

    /** Coba tarik stream baru untuk subjectId tertentu berdasarkan keahlian ep. */
    suspend fun fetchStreams(subjectId: String, ep: Int = 1): List<MediaStream>? =
        withContext(Dispatchers.IO) {
            val token = boot()
            if (token == null) {
                android.util.Log.w(TAG, "bootstrap gagal (semua host)")
                return@withContext null
            }
            val base = activeHost ?: HOSTS.first()
            val variants = listOf(
                "?subjectId=$subjectId&se=1&ep=$ep&resolution=0",
                "?subjectId=$subjectId&se=1&ep=1&resolution=0",
                "?subjectId=$subjectId&se=0&ep=0&resolution=0",
            )
            for (q in variants) {
                val root = callJson(base, PLAY_INFO_PATH + q, null, token, true) ?: continue
                if (root.optInt("code", -1) != 0) {
                    android.util.Log.w(TAG, "play-info code=${root.optInt("code", -1)} msg=${root.optString("message", "")}")
                    continue
                }
                val data = root.optJSONObject("data") ?: continue
                val streams = buildStreams(data)
                if (streams.isNotEmpty()) {
                    android.util.Log.i(TAG, "play-info ok subj=${subjectId.takeLast(6)} ep=$ep streams=${streams.size} q=$q")
                    return@withContext streams
                }
                val rawLen = data.optJSONArray("streamList")?.length() ?: data.optJSONArray("streams")?.length() ?: -1
                android.util.Log.w(TAG, "play-info streamList=$rawLen subj=${subjectId.takeLast(6)} q=$q")
            }
            null
        }

    private fun buildStreams(data: JSONObject): List<MediaStream> {
        val arr = data.optJSONArray("streamList") ?: data.optJSONArray("streams") ?: return emptyList()
        val out = ArrayList<MediaStream>()
        val seen = HashSet<String>()
        for (i in 0 until arr.length()) {
            val entry = arr.optJSONObject(i) ?: continue
            val signCookie = entry.optString("signCookie", "")
            val direct = entry.optString("url", "")
            val duration = entry.optLong("duration", 0L)
            if (duration in 1 until MIN_DURATION_SECONDS) continue
            var finalUrl: String? = null
            var fmt = "mp4"
            if (signCookie.contains("urlprefix=")) {
                val b64 = signCookie.substringAfter("urlprefix=", "")
                    .substringBefore(":sign=", "")
                val decoded = runCatching {
                    Base64.getDecoder().decode(b64).toString(Charsets.UTF_8).trimEnd('/')
                }.getOrNull()
                if (!decoded.isNullOrBlank()) {
                    fmt = if (decoded.contains("/dash/")) "dash" else "hls"
                    val index = if (fmt == "dash") "index.mpd" else "index.m3u8"
                    finalUrl = "$decoded/$index"
                }
            }
            if (finalUrl == null && direct.isNotBlank()) {
                fmt = when {
                    direct.contains(".m3u8") -> "hls"
                    direct.contains(".mpd") -> "dash"
                    else -> "mp4"
                }
                finalUrl = direct
            }
            val url = finalUrl?.takeIf { it.isNotBlank() } ?: continue
            if (!seen.add(url)) continue
            val hdrs = LinkedHashMap<String, String>()
            if (signCookie.isNotBlank()) hdrs["Cookie"] = signCookie
            hdrs["Referer"] = "https://www.movieboxpro.app/"
            hdrs["User-Agent"] = "ExoPlayerLib/2.19.1"
            out.add(
                MediaStream(
                    server = "Moviebox",
                    format = fmt,
                    quality = entry.optString("resolution", "").takeIf { it.isNotBlank() } ?: "Auto",
                    url = url,
                    headers = hdrs,
                ),
            )
        }
        return out
    }

    /** Deteksi sumber moviebox & subjectId/episode dari URL lama di katalog. */
    fun detectFromStreams(streams: List<MediaStream>): Pair<String, Int>? {
        for (s in streams) {
            val u = s.safeUrl ?: continue
            val host = runCatching { u.toHttpUrl().host }.getOrNull() ?: continue
            if (!(host.contains("hakunaymatata.com") || host.contains("aoneroom.com"))) continue
            val subject = Regex("\\d{17,21}").find(u)?.value ?: continue
            val ep = Regex("\\d{17,21}_(\\d+)_(\\d+)").find(u)
                ?.groupValues?.getOrNull(2)?.toIntOrNull() ?: 1
            return subject to ep
        }
        return null
    }

    /** true bila stream membawa `t=<epoch>` yang sudah lewat. */
    fun isExpired(streams: List<MediaStream>): Boolean {
        val now = System.currentTimeMillis() / 1000L + 60L
        var flagged = false
        for (s in streams) {
            val u = s.safeUrl ?: continue
            val cookie = s.headers?.get("Cookie") ?: s.headers?.get("cookie") ?: ""
            val hay = "$u $cookie"
            for (m in Regex("[?&:]t=(\\d{9,})").findAll(hay)) {
                flagged = true
                if (m.groupValues[1].toLong() > now) return false
            }
        }
        return flagged
    }

    /**
     * Bila SourceData merupakan sumber moviebox yang kedaluwarsa, tarik
     * stream baru via protokol Moviebox; bila gagal, SourceData asli dipakai.
     */
    suspend fun maybeRefresh(data: SourceData): SourceData {
        val streams = data.playableStreams
        if (streams.isEmpty()) return data
        val detected = detectFromStreams(streams) ?: return data
        if (!isExpired(streams)) return data
        val fresh = try {
            fetchStreams(detected.first, detected.second)
        } catch (t: Throwable) {
            android.util.Log.w(TAG, "resolver error: ${t.javaClass.simpleName}: ${t.message}")
            null
        } ?: return data
        if (fresh.isEmpty()) return data
        val old = streams.filter { old -> fresh.none { it.url == old.url } }
        return data.copy(streams = fresh + old, subtitles = data.subtitles)
    }
}
