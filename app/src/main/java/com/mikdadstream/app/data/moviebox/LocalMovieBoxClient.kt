package com.mikdadstream.app.data.moviebox

import android.util.Base64
import android.util.Log
import com.mikdadstream.app.data.remote.DetailData
import com.mikdadstream.app.data.remote.EpisodeData
import com.mikdadstream.app.data.remote.MediaItem
import com.mikdadstream.app.data.remote.MediaStream
import com.mikdadstream.app.data.remote.SubtitleTrack
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
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
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
            "X-Client-Status" to "1",
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
        var attempt = 0
        while (attempt < 3) {
            // Regenerate headers with fresh timestamp on each attempt
            val headers = signedHeaders(method, pathAndQuery, body, auth, playMode = playMode)
            val builder = Request.Builder()
                .url(base + pathAndQuery)
            headers.forEach { (k, v) -> builder.header(k, v) }
            if (body != null) builder.post(body.toRequestBody("application/json;charset=UTF-8".toMediaType()))
            
            try {
                val response = http.newCall(builder.build()).execute()
                val responseFinal = response
                val result = responseFinal.use { resp ->
                    val statusCode = resp.code
                    val bodyString = resp.body?.string() ?: ""
                    
                    // Log HTTP status for debugging
                    Log.i(TAG, "callJson HTTP $statusCode ${resp.request.url}")
                    
                    // Handle HTTP error status codes
                    if (statusCode !in 200..299) {
                        Log.w(TAG, "callJson HTTP error $statusCode: $bodyString")
                        if (attempt == 2) {
                            Log.w(TAG, "callJson HTTP error after 3 attempts: $statusCode $bodyString")
                            null
                        } else {
                            Thread.sleep((500L * (attempt + 1))) // Exponential backoff
                            null
                        }
                    } else {
                        resp.header("x-user")?.let { xu ->
                            runCatching {
                                val tok = if (xu.trim().startsWith("{")) JSONObject(xu).optString("token", "") else xu
                                if (tok.isNotBlank()) {
                                    tokenLock.withLock { guestToken = tok }
                                }
                            }
                        }
                        if (bodyString.isNotBlank()) JSONObject(bodyString) else null
                    }
                }
                // If result is null (HTTP error), continue to retry
                if (result == null) {
                    if (attempt == 2) return null
                    Thread.sleep((500L * (attempt + 1))) // Exponential backoff
                    attempt++
                    continue
                }
                return result
            } catch (t: Throwable) {
                if (attempt == 2) {
                    Log.w(TAG, "callJson failed after 3 attempts: ${t.javaClass.simpleName}: ${t.message}")
                    return null
                }
                Thread.sleep((500L * (attempt + 1))) // Exponential backoff
                attempt++
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
        val url = sb.toString()
        android.util.Log.i(TAG, "fetchPlayInfo url=$url se=$se ep=$ep resourceId=$resourceId")
        val result = callJson(base, url, null, token, true)
        android.util.Log.i(TAG, "fetchPlayInfo result code=${result?.optInt("code", -1)} msg=${result?.optString("message", "")}")
        return result
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
            // Tanpa Referer: CDN memblokir request ber-Referer.
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

    private fun isIndoText(s: String?): Boolean {
        if (s.isNullOrBlank()) return false
        val t = s.lowercase(Locale.ROOT)
        return t.contains("indonesia") || t.contains("bahasa")
    }

    private val indoCache = java.util.concurrent.ConcurrentHashMap<String, Boolean>()

    private suspend fun subjectQualifiesIndo(subject: JSONObject, base: String, token: String): Boolean {
        val id = subject.optString("subjectId").ifBlank { subject.optString("id") }
            .ifBlank { subject.optString("subject_id") }
        if (id.isBlank()) return false
        indoCache[id]?.let { return it }
        val qualifies: Boolean
        if (isIndoText(subject.optString("countryName")) || isIndoText(subject.optString("language"))) {
            qualifies = true
        } else {
            val root = runCatching { fetchSubject(base, id, token) }.getOrNull()
            val data = root?.optJSONObject("data")
            val subStr = data?.optString("subtitles")?.takeIf { it.isNotBlank() }
            qualifies = if (subStr != null) {
                isIndoText(subStr)
            } else {
                val arr = data?.optJSONArray("subtitles")
                if (arr != null) {
                    (0 until arr.length()).any { isIndoText(arr.optString(it)) }
                } else false
            }
        }
        indoCache[id] = qualifies
        return qualifies
    }

    // Filter: hanya subject berbahasa Indonesia atau ber-subtitle Indonesia.
    // Metadata (countryName/language) dicek tanpa request; sisanya via detail (paralel, hasilnya dicache).
    suspend fun filterIndoOnly(subjects: List<JSONObject>, token: String): List<JSONObject> =
        coroutineScope {
            val base = activeHostOrFirst()
            val sem = Semaphore(permits = 6)
            subjects.map { s ->
                async {
                    sem.withPermit {
                        if (subjectQualifiesIndo(s, base, token)) s else null
                    }
                }
            }.awaitAll().filterNotNull()
        }

    suspend fun tabOperating(tabId: Int): JSONObject? = withContext(Dispatchers.IO) {
        val token = boot() ?: return@withContext null
        val base = activeHostOrFirst()
        callJson(base, "/wefeed-mobile-bff/tab-operating?host=api.inmoviebox.com&page=1&pageSize=24&tabId=$tabId", null, token, false)
    }

    private var catalogCache: Pair<Long, List<JSONObject>>? = null

    /** Katalog gabungan: trending/v2 + rows tab-operating (tab 1 & 2), dedup per subjectId. */
    suspend fun catalogSubjects(): List<JSONObject> = withContext(Dispatchers.IO) {
        val cached = catalogCache
        if (cached != null && System.currentTimeMillis() - cached.first < 120_000) {
            return@withContext cached.second
        }
        val token = boot() ?: return@withContext emptyList()
        val base = activeHostOrFirst()
        val out = LinkedHashMap<String, JSONObject>()
        fun add(subject: JSONObject?) {
            if (subject == null) return
            val id = subject.optString("subjectId")
            if (id.isNotBlank() && !out.containsKey(id)) out[id] = subject
        }
        extractList(trendingV2()).forEach(::add)
        android.util.Log.i(TAG, "catalogSubjects trending=${out.size}")
        for (tab in listOf(1, 2)) {
            val root = tabOperating(tab) ?: continue
            val items = root.optJSONObject("data")?.optJSONArray("items") ?: continue
            var extra = 0
            for (i in 0 until items.length()) {
                val item = items.optJSONObject(i) ?: continue
                val arr = item.optJSONArray("subjects") ?: continue
                for (j in 0 until arr.length()) {
                    val o = arr.optJSONObject(j) ?: continue
                    val before = out.size
                    add(o.optJSONObject("subject") ?: o)
                    if (out.size > before) extra++
                }
            }
            android.util.Log.i(TAG, "catalogSubjects tab=$tab added=$extra")
        }
        val list = out.values.toList()
        catalogCache = System.currentTimeMillis() to list
        list
    }

    private fun subjectTypeOf(o: JSONObject): Int =
        o.optString("subjectType").toIntOrNull() ?: o.optInt("subjectType", 0)

    private fun categoryMatches(categoryId: String, o: JSONObject): Boolean {
        val country = (o.optString("countryName") + " " + o.optString("language")).lowercase(Locale.ROOT)
        val genre = o.optString("genre").lowercase(Locale.ROOT)
        return when (categoryId) {
            "movie" -> subjectTypeOf(o) == 1
            "series" -> subjectTypeOf(o) == 2
            "anime" -> genre.contains("anime") || genre.contains("animation")
            "hoollywood", "hollywood" ->
                listOf("united states", "united kingdom", "america", "canada", "hollywood")
                    .any { country.contains(it) || o.optString("countryName").lowercase(Locale.ROOT).contains(it) }
            "asia" -> listOf(
                "japan", "korea", "china", "hong kong", "taiwan", "thailand", "vietnam",
                "philippines", "india", "indonesia", "malaysia", "singapore", "nepal",
                "sri lanka", "bangladesh",
            ).any { country.contains(it) }
            "horror" -> genre.contains("horror") || genre.contains("horor")
            else -> true
        }
    }

    /** Daftar film per kategori sintetis (moviebox-local). */
    suspend fun categoryItems(categoryId: String): List<MediaItem> = withContext(Dispatchers.IO) {
        val subjects = catalogSubjects()
        if (subjects.isEmpty()) return@withContext emptyList()
        val picked: List<JSONObject> = when (categoryId.trim().lowercase(Locale.ROOT)) {
            "trending" -> subjects
            // Indonesia = film asli buatan Indonesia (negara/bahasa produksi), bukan sekadar ber-subtitle Indonesia.
            "indonesia" -> subjects.filter {
                isIndoText(it.optString("countryName")) || isIndoText(it.optString("language"))
            }
            else -> subjects.filter { categoryMatches(categoryId.trim().lowercase(Locale.ROOT), it) }
        }
        android.util.Log.i(TAG, "categoryItems $categoryId picked=${picked.size}/pool=${subjects.size}")
        val out = ArrayList<MediaItem>()
        val seen = HashSet<String>()
        for (s in picked) {
            val m = mapSubjectToMediaItem(s) ?: continue
            if (seen.add(m.id ?: "")) out.add(m)
            if (out.size >= 48) break
        }
        out
    }

    suspend fun trendingItems(): List<MediaItem> =
        withContext(Dispatchers.IO) {
            val token = boot() ?: return@withContext emptyList()
            val flat = extractList(trendingV2())
            filterIndoOnly(flat, token).mapNotNull { mapSubjectToMediaItem(it) }
        }

    suspend fun searchItems(keyword: String, page: Int = 1): List<MediaItem> =
        withContext(Dispatchers.IO) {
            val token = boot() ?: return@withContext emptyList()
            val flat = extractList(search(keyword, page))
            filterIndoOnly(flat, token).mapNotNull { mapSubjectToMediaItem(it) }
        }

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
            u.contains("b164fbfb43")
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

    // Subtitle eksternal: /subject-api/get-ext-captions (pola APK: subjectId + resourceId [+ se/ep] -> data.extCaptions).
    suspend fun fetchSubtitles(subjectId: String, se: Int = 1, ep: Int = 1): List<SubtitleTrack> =
        withContext(Dispatchers.IO) {
            try {
                val token = boot() ?: return@withContext emptyList()
                val base = activeHostOrFirst()
                val detailRoot = runCatching { fetchSubject(base, subjectId, token) }.getOrNull()
                val rid = detailRoot?.optJSONObject("data")?.optJSONArray("resourceDetectors")
                    ?.optJSONObject(0)?.optString("resourceId")?.takeIf { it.isNotBlank() }
                var pq = "/wefeed-mobile-bff/subject-api/get-ext-captions?subjectId=$subjectId"
                if (!rid.isNullOrBlank()) pq += "&resourceId=$rid"
                if (ep > 1) {
                    pq += "&se=${if (se > 0) se else 1}&ep=$ep"
                }
                val root = callJson(base, pq, null, token, false)
                val data = root?.optJSONObject("data")
                val caps = data?.optJSONArray("extCaptions")
                    ?: data?.optJSONArray("subTitleList")
                    ?: data?.optJSONArray("captions")
                val out = ArrayList<SubtitleTrack>()
                if (caps != null) {
                    for (i in 0 until caps.length()) {
                        val c = caps.optJSONObject(i) ?: continue
                        val url = c.optString("url").takeIf { it.isNotBlank() } ?: continue
                        val lan = c.optString("lan").takeIf { it.isNotBlank() } ?: continue
                        out.add(
                            SubtitleTrack(
                                label = c.optString("lanName").takeIf { it.isNotBlank() } ?: lan,
                                lang = lan,
                                url = url,
                            )
                        )
                    }
                }
                Log.i(TAG, "fetchSubtitles count=${out.size} subj=${subjectId.takeLast(6)}")
                out
            } catch (e: Exception) {
                Log.w(TAG, "fetchSubtitles error: ${e.message}")
                emptyList()
            }
        }

    private fun streamFromUgcRes(res: JSONObject, referer: String = "https://www.movieboxpro.app/"): MediaStream? {
        val url = res.optString("url").takeIf { it.isNotBlank() } ?: return null
        val signCookie = res.optString("signCookie").takeIf { it.isNotBlank() }
        // Pola APK: manifest bisa dikodekan di signCookie "urlprefix=..." atau CloudFront-Policy
        val manifest = signCookie?.let { manifestFromSignCookie(it) }
        val finalUrl = manifest?.second
            ?: res.optString("playUrl").takeIf { it.isNotBlank() }
            ?: url
        val fmt = manifest?.first
            ?: when {
                finalUrl.contains(".m3u8") -> "hls"
                finalUrl.contains(".mpd") -> "dash"
                else -> res.optString("format", "mp4").ifBlank { "mp4" }.lowercase()
            }
        val hdrs = LinkedHashMap<String, String>()
        signCookie?.let { hdrs["Cookie"] = it }
        // PENTING: tanpa Referer — CDN (bcdn.hakunaymatata.com) memblokir request yang membawa Referer (HTTP 429/433).
        hdrs["User-Agent"] = "ExoPlayerLib/2.19.1"
        return MediaStream(
            server = "Moviebox Local",
            format = fmt,
            quality = res.optString("resolutions").takeIf { it.isNotBlank() }
                ?: res.optString("quality").takeIf { it.isNotBlank() } ?: "Auto",
            url = finalUrl,
            headers = hdrs,
        )
    }

    private fun collectCleanResources(item: JSONObject?, out: MutableList<JSONObject>) {
        if (item == null) return
        val resources = item.optJSONArray("resources") ?: return
        for (r in 0 until resources.length()) {
            val res = resources.optJSONObject(r) ?: continue
            val url = res.optString("url")
            if (url.isBlank()) continue
            if (isTrapUrl(url)) continue
            out.add(res)
        }
    }

    private fun qualityScore(r: JSONObject): Int {
        val q = (r.optString("resolutions").takeIf { it.isNotBlank() }
            ?: r.optString("quality").takeIf { it.isNotBlank() } ?: "")
        return when {
            q.contains("1080") -> 5
            q.contains("720") -> 4
            q.contains("540") -> 3
            q.contains("480") -> 2
            q.contains("360") -> 1
            else -> 0
        }
    }

    private fun chooseBestResource(candidates: List<JSONObject>): JSONObject? {
        // Pola APK: resource disortir kualitas tersendiri; ambil kualitas tertinggi yang tersedia.
        return candidates.maxByOrNull { qualityScore(it) }
    }

    suspend fun fetchRealStreams(subjectId: String, resourceId: String?, se: Int = 1, ep: Int = 1): List<MediaStream> {
        android.util.Log.i(TAG, "fetchRealStreams start subj=${subjectId.takeLast(6)} rid=$resourceId se=$se ep=$ep")
        val rid = resourceId?.takeIf { it.isNotBlank() } ?: run {
            val d = subjectDetail(subjectId)
            val r = d?.optJSONObject("data")?.optJSONArray("resourceDetectors")
                ?.optJSONObject(0)?.optString("resourceId")
            android.util.Log.i(TAG, "fetchRealStreams extracted resourceId=${r}")
            r
        }
        val first = firstBySubject(subjectId)
        if (first == null) { android.util.Log.w(TAG, "firstBySubject null"); return emptyList() }
        val firstData = first.optJSONObject("data")
        val ugcVideoId = firstData?.optString("ugcVideoId")?.takeIf { it.isNotBlank() }
        val collectionId = firstData?.optJSONObject("belongToCollection")
            ?.optString("collectionId")?.takeIf { it.isNotBlank() }
        android.util.Log.i(TAG, "fetchRealStream collectionId=$collectionId ugcVideoId=$ugcVideoId")

        val candidates = ArrayList<JSONObject>()

        if (collectionId != null) {
            val list = collectionDownloadList(collectionId, 1, 50)
            if (list == null) {
                android.util.Log.w(TAG, "collectionDownloadList null")
            } else {
                val dataObj = list.optJSONObject("data")
                val items = dataObj?.optJSONArray("items")
                    ?: dataObj?.optJSONArray("list")
                    ?: org.json.JSONArray()
                val count = items.length()
                android.util.Log.i(TAG, "fetchRealStream items count=$count")
                if (count > 0) {
                    // Pola APK: pilih episode by epTitle "S{se}EP{ep}" atau "E{ep}"; jika rid/ep kosong pakai item pertama.
                    var matched: JSONObject? = null
                    for (i in 0 until count) {
                        val item = items.optJSONObject(i) ?: continue
                        val epTitle = item.optString("epTitle").uppercase(Locale.ROOT)
                        if (epTitle.isBlank()) continue
                        val sEp = "S${se}_EP${ep}"
                        val eEp = "E${ep}"
                        if (epTitle.contains(sEp) || epTitle.contains(eEp)) { matched = item; break }
                    }
                    if (matched == null && rid.isNullOrBlank()) matched = items.optJSONObject(0)
                    if (matched != null) {
                        android.util.Log.i(TAG, "fetchRealStream matched item epTitle=${matched.optString("epTitle")}")
                        collectCleanResources(matched, candidates)
                    }
                    // Jika resource item terpilih kosong dan rid diketahui, telusuri semua item by resource id.
                    if (candidates.isEmpty() && !rid.isNullOrBlank()) {
                        for (i in 0 until count) {
                            val item = items.optJSONObject(i) ?: continue
                            val resources = item.optJSONArray("resources") ?: continue
                            for (r in 0 until resources.length()) {
                                val res = resources.optJSONObject(r) ?: continue
                                val resId = res.optString("id")
                                if (resId.isNotBlank() && resId == rid) {
                                    collectCleanResources(item, candidates)
                                    break
                                }
                            }
                            if (candidates.isNotEmpty()) break
                        }
                    }
                    // Terakhir: kumpulkan resource bersih pertama dari item pertama (fallback APK).
                    if (candidates.isEmpty()) collectCleanResources(items.optJSONObject(0), candidates)
                }
            }
        }

        // Fallback APK: /ugc/video/download?ugcVideoId= saat list kosong.
        if (candidates.isEmpty() && !ugcVideoId.isNullOrBlank()) {
            val token = boot() ?: return emptyList()
            val base = activeHostOrFirst()
            val dl = callJson(base, "/wefeed-mobile-bff/ugc/video/download?ugcVideoId=$ugcVideoId", null, token, false)
            val dlItems = dl?.optJSONObject("data")?.optJSONArray("items")
                ?: dl?.optJSONObject("data")?.optJSONArray("list")
            if (dlItems != null) {
                for (i in 0 until dlItems.length()) {
                    collectCleanResources(dlItems.optJSONObject(i), candidates)
                }
            }
            android.util.Log.i(TAG, "fetchRealStream ugc download candidates=${candidates.size}")
        }

        val out = ArrayList<MediaStream>()
        val seen = HashSet<String>()
        for (res in candidates.sortedByDescending { qualityScore(it) }) {
            val s = streamFromUgcRes(res) ?: continue
            // Satu sumber per kualitas, seperti MovieBox: resolusi sama dipilih URL pertama (terbaik).
            val key = s.quality ?: "Auto"
            if (seen.add(key)) out.add(s)
        }
        android.util.Log.i(TAG, "Moviebox Local real streams count=${out.size} qualities=${out.joinToString { (it.quality ?: "Auto") }} subj=${subjectId.takeLast(6)}")
        return out
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
                    Log.w(TAG, "play-info code=${root.optInt("code", -1)} msg=${root.optString("message", "")} url=${call.toString()}")
                    continue
                }
                val data = root.optJSONObject("data") ?: continue
                val streams = buildStreams(data)
                Log.i(TAG, "play-info streams count=${streams.size} subj=${subjectId.takeLast(6)} se=$se ep=$ep")
            val trap = streams.firstOrNull()?.url?.let { isTrapUrl(it) } == true
            if (streams.isEmpty() || trap) {
                val real = fetchRealStreams(subjectId, resourceId, if (se > 0) se else 1, if (ep > 0) ep else 1)
                android.util.Log.w(TAG, "fetchRealStreams result=${real.size} empty=${streams.isEmpty()} trap=$trap subj=${subjectId.takeLast(6)}")
                if (real.isNotEmpty()) {
                    Log.i(TAG, "Moviebox Local real streams=${real.size} subj=${subjectId.takeLast(6)}")
                    return@withContext real
                }
                // Trap tanpa pengganti: jangan kembalikan iklan; kosongkan agar tidak memutar ad.
                if (trap) return@withContext emptyList()
                // Streams kosong & fallback kosong: coba varian play-info berikutnya.
                continue
            }
            if (streams.isNotEmpty()) {
                Log.i(TAG, "Moviebox Local stream count=${streams.size} subj=${subjectId.takeLast(6)}")
                return@withContext streams
            }
            }
            null
        }
}
