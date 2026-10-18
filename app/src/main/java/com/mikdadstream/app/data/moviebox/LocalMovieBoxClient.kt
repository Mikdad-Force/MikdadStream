package com.mikdadstream.app.data.moviebox

import android.util.Base64
import android.util.Log
import com.mikdadstream.app.data.remote.DetailData
import com.mikdadstream.app.data.remote.EpisodeData
import com.mikdadstream.app.data.remote.MediaItem
import com.mikdadstream.app.data.remote.MediaStream
import java.security.MessageDigest
import java.util.Base64 as JavaBase64
import java.util.Locale
import java.util.UUID
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.concurrent.withLock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

object LocalMovieBoxClient {

    private const val TAG = "LocalMovieBoxClient"

    val HOSTS = listOf(
        "https://api6.aoneroom.com",
        "https://api.inmoviebox.com",
        "https://api7.aoneroom.com",
        "https://api5.aoneroom.com",
        "https://api4sg.aoneroom.com",
        "https://api4.aoneroom.com",
        "https://api3.aoneroom.com",
    )

    private const val BOOT_PATH = "/wefeed-mobile-bff/tab-operating"
    const val SUBJECT_GET_PATH = "/wefeed-mobile-bff/subject-api/get"
    const val PLAY_INFO_PATH = "/wefeed-mobile-bff/subject-api/play-info"

    private const val SECRET_DEFAULT = "76iRl07s0xSN9jqmEWAt79EBJZulIQIsV64FZr2O"
    private const val SIGN_BODY_MAX = 102400
    private const val MIN_DURATION_SECONDS = 60L

    private val http: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(12, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .build()

    private val tokenLock = ReentrantLock()
    @Volatile private var guestToken: String? = null
    @Volatile private var activeHostVar: String? = null

    val activeHost: String? get() = activeHostVar

    private val deviceId: String = UUID.randomUUID().toString().replace("-", "").take(32)

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
        val pairs = if (q.isBlank()) emptyList<Pair<String, String>>() else q.split('&')
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

    private fun clientInfo(): String {
        val info = JSONObject()
        info.put("package_name", "com.movieboxpro.android")
        info.put("version_name", "16.2.1")
        info.put("version_code", 16210)
        info.put("os", "android")
        info.put("os_version", "12")
        info.put("install_ch", "googleplay")
        info.put("device_id", deviceId)
        info.put("install_store", "googleplay")
        info.put("gaid", "")
        info.put("brand", "google")
        info.put("model", "Pixel 6")
        info.put("system_language", "en")
        info.put("net", "wifi")
        info.put("region", "US")
        info.put("timezone", "America/New_York")
        info.put("sp_code", "404")
        return info.toString()
    }

    private fun signedHeaders(
        method: String,
        pathAndQuery: String,
        body: String?,
        auth: String?,
        accept: String = "application/json",
        contentType: String = "application/json;charset=UTF-8",
        playMode: Boolean = false,
    ): Map<String, String> {
        val ts = System.currentTimeMillis()
        val headers = mutableMapOf(
            "User-Agent" to "MovieBoxPro/16.2.1 (Android 14; com.community.mbox.in)",
            "Accept" to accept,
            "Content-Type" to contentType,
            "Connection" to "keep-alive",
            "X-Client-Info" to clientInfo(),
            "X-Client-Status" to "0",
            "X-Client-Token" to ("$ts," +
                md5Hex(ts.toString().reversed().toByteArray(Charsets.UTF_8))),
            "x-tr-signature" to run {
                val canon = canonical(method, accept, contentType, pathAndQuery, body, ts)
                val key = try {
                    Base64.decode(SECRET_DEFAULT, Base64.DEFAULT)
                } catch (t: Throwable) {
                    SECRET_DEFAULT.toByteArray(Charsets.UTF_8)
                }
                val mac = hmacMd5(key, canon.toByteArray(Charsets.UTF_8))
                "$ts|2|" + JavaBase64.getEncoder().encodeToString(mac)
            },
        )
        auth?.takeIf { it.isNotBlank() }?.let { headers["Authorization"] = "Bearer $it" }
        if (playMode) headers["X-Play-Mode"] = "2"
        return headers
    }

            fun callJson(
        base: String,
        pathAndQuery: String,
        body: String?,
        auth: String?,
        playMode: Boolean,
    ): JSONObject? {
        val method = if (body != null) "POST" else "GET"
        val builder = Request.Builder()
            .url(base + pathAndQuery)
        if (body != null) builder.post(body.toRequestBody("application/json;charset=UTF-8".toMediaType()))
        
        // Retry up to 3 times for transient failures
        repeat(3) { attempt ->
            // Regenerate headers with fresh timestamp on each attempt
            val headers = signedHeaders(method, pathAndQuery, body, auth, playMode = playMode)
            val builder = Request.Builder()
                .url(base + pathAndQuery)
            headers.forEach { (k, v) -> builder.header(k, v) }
            if (body != null) builder.post(body.toRequestBody("application/json;charset=UTF-8".toMediaType()))
            
            try {
                val response = http.newCall(builder.build()).execute()
                return response.use { resp ->
                    resp.header("x-user")?.let { xu ->
                        runCatching {
                            val tok = if (xu.trim().startsWith("{")) JSONObject(xu).optString("token", "") else xu
                            if (tok.isNotBlank()) {
                                tokenLock.withLock { guestToken = tok }
                            }
                        }
                    }
                    return resp.body?.string()?.let { JSONObject(it) }
                }
            } catch (t: Throwable) {
                if (attempt == 2) {
                    Log.w(TAG, "callJson failed after 3 attempts: ${t.javaClass.simpleName}: ${t.message}")
                    return null
                }
                Thread.sleep((500L * (attempt + 1))) // Exponential backoff
            }
        }
        return null
    }
    fun boot(): String? {
        tokenLock.withLock { guestToken?.let { if (it.isNotBlank()) return it } }
        for (base in HOSTS) {
            val path = "$BOOT_PATH?host=api.inmoviebox.com&page=1&pageSize=24&tabId=1"
            val root = callJson(base, path, null, null, false)
            if (root == null) {
                Log.w(TAG, "boot null host=$base")
                continue
            }
            val code = root.optInt("code", -1)
            if (code == 0) {
                val token = root.optJSONObject("data")?.optString("token")?.takeIf { it.isNotBlank() }
                    ?: tokenLock.withLock { guestToken }
                if (!token.isNullOrBlank()) {
                    tokenLock.withLock {
                        guestToken = token
                        activeHostVar = base
                    }
                    Log.i(TAG, "bootstrap ok host=$base")
                    return token
                }
            } else {
                Log.w(TAG, "boot failed host=$base code=$code msg=${root.optString("message", "")}")
            }
        }
        return null
    }

    fun activeHostOrFirst(): String = activeHost ?: HOSTS.first()

    fun fetchSubject(base: String, subjectId: String, token: String): JSONObject? =
        callJson(base, "$SUBJECT_GET_PATH?subject_id=$subjectId", null, token, false)

    fun fetchPlayInfo(
        base: String,
        subjectId: String,
        se: Int,
        ep: Int,
        resourceId: String?,
        token: String,
    ): JSONObject? {
        val sb = StringBuilder("$PLAY_INFO_PATH?subjectId=$subjectId&host=api6.aoneroom.com")
        if (se > 0) sb.append("&se=$se")
        if (ep > 0) sb.append("&ep=$ep")
        resourceId?.takeIf { it.isNotBlank() }?.let { sb.append("&resourceId=$it") }
        return callJson(base, sb.toString(), null, token, true)
    }

    fun manifestFromSignCookie(signCookie: String): Pair<String, String>? {
        val cf = Regex("CloudFront-Policy=([^;]+)").find(signCookie)?.groupValues?.getOrNull(1)
        if (!cf.isNullOrBlank()) {
            val normalized = cf.replace('-', '+').replace('_', '=').replace('~', '/')
                .let { it + "=".repeat((4 - it.length % 4) % 4) }
            val json = runCatching {
                String(JavaBase64.getDecoder().decode(normalized), Charsets.UTF_8)
            }.getOrNull() ?: return null
            val resource = runCatching {
                JSONObject(json).optJSONArray("Statement")
                    ?.optJSONObject(0)?.optString("Resource") ?: ""
            }.getOrNull() ?: ""
            if (resource.isNotBlank()) {
                val base = if (resource.endsWith("/*")) resource.dropLast(1) else resource
                val dir = base.substringBeforeLast('/', base)
                val fmt = if (dir.contains("/dash/") || base.contains(".mpd")) "dash" else "hls"
                if (base.endsWith(".mpd") || base.endsWith(".m3u8")) return fmt to base
                val index = if (fmt == "dash") "index_web.mpd" else "index.m3u8"
                return fmt to "$dir/$index"
            }
        }
        val b64 = Regex("urlprefix=([^:]+)").find(signCookie)?.groupValues?.getOrNull(1)
        if (!b64.isNullOrBlank()) {
            val normalized = b64.replace('-', '+').replace('_', '=').replace('~', '/')
                .let { it + "=".repeat((4 - it.length % 4) % 4) }
            val decoded = runCatching {
                JavaBase64.getDecoder().decode(normalized).toString(Charsets.UTF_8).trimEnd('/')
            }.getOrNull()
            if (!decoded.isNullOrBlank()) {
                val fmt = if (decoded.contains("/dash/")) "dash" else "hls"
                val index = if (fmt == "dash") "index_web.mpd" else "index.m3u8"
                return fmt to "$decoded/$index"
            }
        }
        return null
    }

    fun buildStreams(data: JSONObject): List<MediaStream> {
        val arr = data.optJSONArray("streamList") ?: data.optJSONArray("streams") ?: return emptyList()
        val out = ArrayList<MediaStream>()
        val seen = HashSet<String>()
        for (i in 0 until arr.length()) {
            val entry = arr.optJSONObject(i) ?: continue
            val signCookie = entry.optString("signCookie", "")
            val direct = entry.optString("url", "")
            var finalUrl: String? = null
            var fmt = "mp4"
            manifestFromSignCookie(signCookie)?.let { (f, u) ->
                fmt = f
                finalUrl = u
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
                    server = "Moviebox Local",
                    format = fmt,
                    quality = entry.optString("resolution", "").takeIf { it.isNotBlank() } ?: "Auto",
                    url = url,
                    headers = hdrs,
                ),
            )
        }
        return out
    }

    suspend fun trendingV2(): JSONObject? = withContext(Dispatchers.IO) {
        val token = boot() ?: return@withContext null
        val base = activeHostOrFirst()
        callJson(base, "/wefeed-mobile-bff/subject-api/trending/v2", "{}", token, false)
    }

    suspend fun search(keyword: String, page: Int = 1): JSONObject? = withContext(Dispatchers.IO) {
        val token = boot() ?: return@withContext null
        val base = activeHostOrFirst()
        val body = JSONObject()
            .put("keyword", keyword)
            .put("page", page)
            .put("pageSize", 20)
            .toString()
        callJson(base, "/wefeed-mobile-bff/subject-api/search", body, token, false)
    }

    suspend fun subjectDetail(subjectId: String): JSONObject? = withContext(Dispatchers.IO) {
        val token = boot() ?: return@withContext null
        val base = activeHostOrFirst()
        callJson(base, "$SUBJECT_GET_PATH?subject_id=$subjectId", null, token, false)
    }

    suspend fun seasonInfo(subjectId: String): JSONObject? = withContext(Dispatchers.IO) {
        val token = boot() ?: return@withContext null
        val base = activeHostOrFirst()
        callJson(base, "/wefeed-mobile-bff/subject-api/season-info?subjectId=$subjectId", null, token, false)
    }

    suspend fun topRec(): JSONObject? = withContext(Dispatchers.IO) {
        val token = boot() ?: return@withContext null
        val base = activeHostOrFirst()
        callJson(base, "/wefeed-mobile-bff/subject-api/top-rec", "{}", token, false)
    }

    fun extractList(root: JSONObject?): List<JSONObject> {
        if (root == null) return emptyList()
        val data = root.optJSONObject("data") ?: root
        val arr = data.optJSONArray("list") ?: data.optJSONArray("items") ?: data.optJSONArray("subjects")
            ?: root.optJSONArray("list") ?: root.optJSONArray("items") ?: root.optJSONArray("subjects")
            ?: return emptyList()
        val out = ArrayList<JSONObject>()
        for (i in 0 until arr.length()) {
            val o = arr.optJSONObject(i) ?: continue
            out.add(o.optJSONObject("subject") ?: o)
        }
        return out
    }

    fun mapSubjectToMediaItem(o: JSONObject): MediaItem? {
        val id = o.optString("subjectId").ifBlank { o.optString("id") }
            .ifBlank { o.optString("subject_id") }.ifBlank { o.optString("subjectId") }
        if (id.isBlank()) return null
        val title = o.optString("title").ifBlank { o.optString("name") }
            .ifBlank { o.optString("subjectName") }.ifBlank { "Tanpa Judul" }
        val cover = (o.optJSONObject("cover")?.optString("url"))
            ?: o.optString("cover").takeIf { it.isNotBlank() }
            ?: o.optString("poster").takeIf { it.isNotBlank() }
            ?: o.optString("thumbnail").takeIf { it.isNotBlank() }
            ?: ""
        val type = o.optString("subjectType").ifBlank { o.optString("type") }
        val episodeCount = o.optInt("episodeCount", 0).let { if (it > 0) it else o.optInt("totalEpisode", 0) }
        val detailPath = o.optString("detailPath").takeIf { it.isNotBlank() }
        return MediaItem(
            id = id,
            title = title,
            cover = cover,
            type = type,
            episodeCount = if (episodeCount > 0) episodeCount else null,
            source = "Moviebox Local",
            detailPath = detailPath,
        )
    }

    suspend fun trendingItems(): List<MediaItem> =
        extractList(trendingV2()).mapNotNull { mapSubjectToMediaItem(it) }

    suspend fun searchItems(keyword: String, page: Int = 1): List<MediaItem> =
        extractList(search(keyword, page)).mapNotNull { mapSubjectToMediaItem(it) }

    suspend fun subjectDetailData(subjectId: String): DetailData? {
        val root = subjectDetail(subjectId) ?: return null
        val data = root.optJSONObject("data") ?: return null
        val title = data.optString("title").ifBlank { data.optString("name") }.ifBlank { "Tanpa Judul" }
        val desc = data.optString("description").takeIf { it.isNotBlank() }
        val cover = (data.optJSONObject("cover")?.optString("url"))
            ?: data.optString("cover").takeIf { it.isNotBlank() }
        val totalEp = data.optJSONArray("resourceDetectors")?.optJSONObject(0)?.optInt("totalEpisode", 0) ?: 0
        val episodes = seasonInfo(subjectId)?.let { mapSeasonToEpisodes(it) }?.takeIf { it.isNotEmpty() }
            ?: if (totalEp > 0) (1..totalEp).map { EpisodeData(episodeId = "1_$it", episodeNumber = it) }
            else listOf(EpisodeData(episodeId = "1_1", episodeNumber = 1))
        return DetailData(
            id = subjectId,
            title = title,
            description = desc,
            cover = cover,
            modelId = "moviebox-local",
            episodes = episodes,
        )
    }

    fun mapSeasonToEpisodes(root: JSONObject): List<EpisodeData> {
        val data = root.optJSONObject("data") ?: root
        val seasons = data.optJSONArray("seasons") ?: return emptyList()
        val out = ArrayList<EpisodeData>()
        for (si in 0 until seasons.length()) {
            val season = seasons.optJSONObject(si) ?: continue
            val se = season.optInt("se", 0).let { if (it > 0) it else season.optInt("season", 0) }
                .let { if (it > 0) it else (si + 1) }
            val maxEp = season.optInt("maxEp", 0).let { if (it > 0) it else season.optInt("maxEpisode", 0) }
                .let { if (it > 0) it else season.optInt("epNum", 0) }
            if (maxEp <= 0) continue
            for (ep in 1..maxEp) {
                out.add(
                    EpisodeData(
                        episodeId = "${se}_${ep}",
                        episodeNumber = ep,
                    )
                )
            }
        }
        if (out.isEmpty()) {
            // fallback: jika server mengirim episodes langsung
            val arr = data.optJSONArray("episodes") ?: data.optJSONArray("list") ?: data.optJSONArray("items")
            if (arr != null) {
                for (i in 0 until arr.length()) {
                    val o = arr.optJSONObject(i) ?: continue
                    val eid = o.optString("episodeId").ifBlank { o.optString("id") }.ifBlank { o.optString("episode_id") }
                    val num = o.optInt("episodeNumber", 0).let { if (it > 0) it else o.optInt("episode", 0) }
                        .let { if (it > 0) it else o.optInt("ep", 0) }
                    out.add(
                        EpisodeData(
                            episodeId = eid.ifBlank { "${i + 1}" },
                            episodeNumber = if (num > 0) num else (i + 1),
                        )
                    )
                }
            }
        }
        return out
    }

    private fun isTrapUrl(url: String): Boolean {
        val u = url.lowercase()
        return u.contains("macdn.aoneroom.com") ||
            u.contains("please_update") ||
            u.contains("update_app") ||
            u.contains("b164fbfb43") ||
            u.contains("check") ||
            u.contains("captcha") ||
            u.contains("verify") ||
            u.contains("challenge") ||
            u.contains("recaptcha")
    }

    fun firstBySubject(subjectId: String): JSONObject? {
        val token = boot() ?: return null
        val base = activeHostOrFirst()
        return callJson(base, "/wefeed-mobile-bff/ugc/video/first-by-subject?subjectId=$subjectId", null, token, false)
    }

    fun collectionDownloadList(collectionId: String, page: Int = 1, perPage: Int = 50): JSONObject? {
        val token = boot() ?: return null
        val base = activeHostOrFirst()
        return callJson(
            base,
            "/wefeed-mobile-bff/ugc/collection/download-list?collectionId=$collectionId&page=$page&perPage=$perPage",
            null,
            token,
            false,
        )
    }

    suspend fun fetchRealStream(subjectId: String, resourceId: String?): MediaStream? {
        val rid = resourceId ?: run {
            val d = subjectDetail(subjectId)
            d?.optJSONObject("data")?.optJSONArray("resourceDetectors")?.optJSONObject(0)?.optString("resourceId")
        }
        val first = firstBySubject(subjectId)
        if (first == null) { android.util.Log.w(TAG, "firstBySubject null"); return null }
        val collectionId = first.optJSONObject("data")
            ?.optJSONObject("belongToCollection")
            ?.optString("collectionId")
            ?.takeIf { it.isNotBlank() } ?: run { android.util.Log.w(TAG, "collectionId null"); return null }
        val list = collectionDownloadList(collectionId, 1, 50)
        if (list == null) { android.util.Log.w(TAG, "collectionDownloadList null"); return null }
        val items = list.optJSONObject("data")?.optJSONArray("items") ?: run { android.util.Log.w(TAG, "items null"); return null }
        for (i in 0 until items.length()) {
            val item = items.optJSONObject(i) ?: continue
            val resources = item.optJSONArray("resources") ?: continue
            for (r in 0 until resources.length()) {
                val res = resources.optJSONObject(r) ?: continue
                if (rid != null && rid.isNotBlank() && res.optString("id") != rid) continue
                val url = res.optString("url").takeIf { it.isNotBlank() } ?: continue
                android.util.Log.i(TAG, "Moviebox Local real stream found: $url")
                val hdrs = LinkedHashMap<String, String>()
                hdrs["Referer"] = "https://www.movieboxpro.app/"
                hdrs["User-Agent"] = "ExoPlayerLib/2.19.1"
                return MediaStream(
                    server = "Moviebox Local",
                    format = res.optString("format", "mp4").lowercase(),
                    quality = res.optString("resolutions").takeIf { it.isNotBlank() } ?: "Auto",
                    url = url,
                    headers = hdrs,
                )
            }
        }
        // fallback: first resource of first item
        val firstItem = items.optJSONObject(0) ?: return null
        val firstRes = firstItem.optJSONArray("resources")?.optJSONObject(0) ?: return null
        val url = firstRes.optString("url").takeIf { it.isNotBlank() } ?: return null
        val hdrs = LinkedHashMap<String, String>()
        hdrs["Referer"] = "https://www.movieboxpro.app/"
        hdrs["User-Agent"] = "ExoPlayerLib/2.19.1"
        return MediaStream(
            server = "Moviebox Local",
            format = firstRes.optString("format", "mp4").lowercase(),
            quality = firstRes.optString("resolutions").takeIf { it.isNotBlank() } ?: "Auto",
            url = url,
            headers = hdrs,
        )
    }

    suspend fun fetchStreams(subjectId: String, se: Int = 1, ep: Int = 1): List<MediaStream>? =
        withContext(Dispatchers.IO) {
            val token = boot() ?: return@withContext null
            val base = activeHostOrFirst()
            val detailRoot = runCatching { fetchSubject(base, subjectId, token) }.getOrNull()
            val detailData = detailRoot?.optJSONObject("data")
            val resourceId = detailData?.optJSONArray("resourceDetectors")
                ?.optJSONObject(0)?.optString("resourceId")?.takeIf { it.isNotBlank() }

            val variants = listOf(
                { fetchPlayInfo(base, subjectId, if (se > 0) se else 1, if (ep > 0) ep else 1, resourceId, token) },
                { fetchPlayInfo(base, subjectId, 1, 1, resourceId, token) },
                { fetchPlayInfo(base, subjectId, 0, 0, resourceId, token) },
            )
            for (call in variants) {
                val root = call() ?: continue
                if (root.optInt("code", -1) != 0) {
                    Log.w(TAG, "play-info code=${root.optInt("code", -1)} msg=${root.optString("message", "")}")
                    continue
                }
                val data = root.optJSONObject("data") ?: continue
                val streams = buildStreams(data)
            val trap = streams.firstOrNull()?.url?.let { isTrapUrl(it) } == true
            if (trap) {
                val real = fetchRealStream(subjectId, resourceId)
                android.util.Log.w(TAG, "fetchRealStream result=${real != null} subj=${subjectId.takeLast(6)}")
                if (real != null) {
                    Log.i(TAG, "Moviebox Local real stream subj=${subjectId.takeLast(6)}")
                    return@withContext listOf(real)
                }
                // Jangan kembalikan iklan/trap; kosongkan agar tidak memutar ad.
                return@withContext emptyList()
            }
            if (streams.isNotEmpty()) {
                Log.i(TAG, "Moviebox Local stream count=${streams.size} subj=${subjectId.takeLast(6)}")
                return@withContext streams
            }
            }
            null
        }
}
