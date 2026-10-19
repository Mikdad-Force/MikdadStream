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
        // "Bahasa Melayu"/"Malaysia" bukan Indonesia meski sama-sama "bahasa".
        if (t.contains("melayu") || t.contains("malaysia")) return false
        return t.contains("indonesia") || t.contains("bahasa") || t.trim() == "indo"
    }

    private val indoCache = java.util.concurrent.ConcurrentHashMap<String, Boolean>()

    /** Info subtitle 1 film (1 request detail, cache 12 jam). */
    private data class SubInfo(
        val ts: Long,
        val indoProd: Boolean,
        val indoSub: Boolean,
        val hindi: Boolean,
        val canonical: String?,
    )

    private val subInfoCache = java.util.concurrent.ConcurrentHashMap<String, SubInfo>()
    private val SUB_INFO_TTL = 12 * 60 * 60 * 1000L

    private fun subjectIdOf(s: JSONObject): String =
        s.optString("subjectId").ifBlank { s.optString("id") }.ifBlank { s.optString("subject_id") }

    /** Judul / metadata bertanda Hindi. */
    private fun isHindiText(s: String?): Boolean {
        val t = s?.lowercase() ?: return false
        return t.contains("hindi") || t.contains("[hin]") || t.contains("hindia") ||
            t.split(',', '/', '|').map { it.trim() }.any { it == "hi" || it == "hin" }
    }

    private suspend fun subtitleInfo(subject: JSONObject, token: String): SubInfo? {
        val id = subjectIdOf(subject)
        if (id.isBlank()) return null
        subInfoCache[id]?.let { if (System.currentTimeMillis() - it.ts < SUB_INFO_TTL) return it }
        val base = activeHostOrFirst()
        val data = runCatching { fetchSubject(base, id, token) }
            .getOrNull()?.optJSONObject("data") ?: return null
        // Produksi Indonesia (dari detail atau metadata daftar) -> selalu tampil.
        val indoProd = isIndoText(data.optString("countryName")) || isIndoText(data.optString("language")) ||
            isIndoText(subject.optString("countryName")) || isIndoText(subject.optString("language"))
        // Subtitle Indonesia: field detail bertipe string koma atau array.
        var hasIndoSub = when (val f = data.opt("subtitles")) {
            is String -> isIndoText(f)
            is org.json.JSONArray -> (0 until f.length()).any { isIndoText(f.optString(it)) }
            else -> false
        }
        // Verifikasi ke endpoint yang dipakai pemutar. Metadata detail kadang
        // mengklaim "Indonesian" padahal extCaptions kosong -> film tampil tapi
        // subtitle tidak pernah muncul. Hanya untuk film non-Indonesia.
        if (!indoProd && hasIndoSub) {
            val rdArr = data.optJSONArray("resourceDetectors")
            val rid = rdArr?.optJSONObject(0)?.optString("resourceId")?.takeIf { it.isNotBlank() }
            val caps = if (rid != null) extCaptionsFor(id, rid, token, 1, 1) else null
            val capsIndo = caps != null && (0 until caps.length()).any { i ->
                val c = caps.optJSONObject(i) ?: return@any false
                normalizedCaptionLang(c.optString("lan")) == "id" ||
                    isIndoText(c.optString("lanName"))
            }
            if (!capsIndo) hasIndoSub = false
        }
        // ID film asal + bahasa audio asli (semua versi dub/Hindi berbagi ID ini).
        val dubs = data.optJSONArray("dubs")
        val origDub = dubs?.let { arr ->
            (0 until arr.length()).mapNotNull { arr.optJSONObject(it) }
                .firstOrNull { it.optBoolean("original", false) }
        }
        val canonical = origDub?.optString("subjectId")?.takeIf { it.isNotBlank() } ?: id
        val origAudio = origDub?.optString("lanCode")?.ifBlank { null }
            ?: origDub?.optString("lanName")?.ifBlank { null }
            ?: data.optString("language")
        // Film Hindi (asli maupun versi dub berlabel Hindi) tidak ditampilkan.
        val hindi = isHindiText(origAudio) || isHindiText(data.optString("language")) ||
            isHindiText(subject.optString("language")) || isHindiText(subject.optString("title"))
        val info = SubInfo(System.currentTimeMillis(), indoProd, hasIndoSub, hindi, canonical)
        subInfoCache[id] = info
        return info
    }

    /**
     * Saring agar semua film yang tampil punya subtitle Indonesia:
     * - Produksi Indonesia: SELALU tampil (tanpa syarat subtitle).
     * - Film lain: wajib ada subtitle Indonesia.
     * - Versi duplikat (KR/EN dub/Hindi dari film sama) digabung -> satu kartu.
     * - Jika pengecekan gagal (jaringan), kartu tetap ditampilkan (fail-soft).
     * Cache 12 jam; paralel max 6 request.
     */
    suspend fun filterIndoOnly(subjects: List<JSONObject>, token: String): List<JSONObject> =
        coroutineScope {
            val sem = Semaphore(permits = 10)
            val infos = subjects.map { s ->
                async { sem.withPermit { s to runCatching { subtitleInfo(s, token) }.getOrNull() } }
            }.awaitAll()
            val groups = LinkedHashMap<String, MutableList<Pair<JSONObject, SubInfo?>>>()
            for ((s, info) in infos) {
                val id = subjectIdOf(s)
                if (id.isBlank()) continue
                val key = info?.canonical ?: id
                groups.getOrPut(key) { mutableListOf() }.add(s to info)
            }
            val out = ArrayList<JSONObject>()
            for ((canonical, group) in groups) {
                val qualified = group.filter { (_, info) ->
                    // Versi Hindi (dub [Hindi] / audio Hindi) tidak ditampilkan sama sekali.
                    if (info?.hindi == true) return@filter false
                    info == null || info.indoProd || info.indoSub
                }
                if (qualified.isEmpty()) continue
                val best = qualified.minWithOrNull(
                    compareBy<Pair<JSONObject, SubInfo?>>(
                        { (_, i) -> if (i?.indoProd == true) 0 else 1 },
                        { (_, i) -> if (i?.indoSub == true) 0 else 1 },
                        { (s, _) -> if (subjectIdOf(s) == canonical) 0 else 1 },
                    )
                )
                best?.first?.let { out.add(it) }
            }
            Log.i(TAG, "filterIndoOnly in=${subjects.size} out=${out.size} groups=${groups.size}")
            out
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

    private fun countryBlob(o: JSONObject): String =
        (o.optString("countryName") + " " + o.optString("language")).lowercase(Locale.ROOT)

    private fun genreBlob(o: JSONObject): String =
        (o.optString("genre") + " " + o.optString("genreList")).lowercase(Locale.ROOT)

    private fun categoryMatches(categoryId: String, o: JSONObject): Boolean {
        val country = countryBlob(o)
        val genre = genreBlob(o)
        val type = subjectTypeOf(o)
        fun c(vararg keys: String) = keys.any { country.contains(it) }
        fun g(vararg keys: String) = keys.any { genre.contains(it) }
        return when (categoryId) {
            // --- 12 kategori utama ---
            "kdrama" -> type == 2 && c("korea", "korean")
            "indodrama" -> type == 2 && c("indonesia")
            "cdrama" -> type == 2 && c("china", "chinese", "mandarin", "hong kong", "taiwan")
            "anime" -> g("anime")
            "thaidrama" -> type == 2 && c("thailand", "thai")
            "western" -> c(
                "united states", "united kingdom", "america", "canada", "australia",
                "new zealand", "ireland", "hollywood", "western",
            )
            "reality" -> g("reality", "variety", "talk show", "game show", "competition")
            "jdrama" -> type == 2 && c("japan", "japanese")
            "malaydrama" -> type == 2 && c("malaysia", "malay")
            "romance" -> g("romance", "romantic")
            "action" -> g("action")
            "mystery" -> g("mystery", "misteri")
            // --- alias lama (kompatibilitas) ---
            "movie" -> type == 1
            "series" -> type == 2
            "hoollywood", "hollywood" ->
                c("united states", "united kingdom", "america", "canada", "hollywood")
            "asia" -> c(
                "japan", "korea", "china", "hong kong", "taiwan", "thailand", "vietnam",
                "philippines", "india", "indonesia", "malaysia", "singapore", "nepal",
                "sri lanka", "bangladesh",
            )
            "horror" -> g("horror", "horor")
            "indonesia" -> c("indonesia")
            else -> true
        }
    }

    private var indonesiaCache: Pair<Long, List<JSONObject>>? = null

    /** Upload/update resource terbaru epoch (detik), fallback date terbaru. */
    private fun subjectUpdateScore(o: JSONObject): Long {
        var best = 0L
        o.optJSONArray("resourceDetectors")?.let { arr ->
            for (i in 0 until arr.length()) {
                val rd = arr.optJSONObject(i) ?: continue
                val up = rd.optString("uploadTime").toLongOrNull()
                if (up != null && up > best) best = up
            }
        }
        if (best > 0) return best
        val rd = o.optString("releaseDate").replace("-", "")
        return rd.toLongOrNull() ?: 0L
    }

    /** Kategori Indonesia: film asli produksi Indonesia. Katalog metadata + pencarian keyword khas Indonesia, urut terbaru/terupdate. */
    private suspend fun indonesiaSubjects(subjects: List<JSONObject>, token: String): List<JSONObject> {
        val cached = indonesiaCache
        if (cached != null && System.currentTimeMillis() - cached.first < 600_000) {
            return cached.second
        }
        val base = activeHostOrFirst()
        val out = LinkedHashMap<String, JSONObject>()
        // 1. Dari katalog: produksi Indonesia berdasar negara/bahasa.
        for (s in subjects) {
            if (isIndoText(s.optString("countryName")) || isIndoText(s.optString("language"))) {
                out[s.optString("subjectId")] = s
            }
        }
        // 2. Dari pencarian: keyword generik + sort terbaru (filter negara tetap ketat di bawah).
        for (q in listOf("indonesia", "horor", "komedi indonesia", "drama indonesia", "sinetron", "series indonesia")) {
            val body = org.json.JSONObject()
                .put("keyword", q)
                .put("page", 1)
                .put("pageSize", 20)
                .put("sort", "Latest")
            val root = runCatching {
                kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                    val base0 = activeHostOrFirst()
                    callJson(base0, "/wefeed-mobile-bff/subject-api/search", body.toString(), token, false)
                }
            }.getOrNull()
            val arr = extractList(root)
            for (s in arr) {
                if (isIndoText(s.optString("countryName")) || isIndoText(s.optString("language"))) {
                    val id = s.optString("subjectId")
                    if (id.isNotBlank()) out[id] = s
                }
            }
        }
        val list = out.values.toList()
            // Terbaru/terupdate di atas: skor uploadTime resource (fallback releaseDate).
            val sorted = list.sortedByDescending { subjectUpdateScore(it) }
            android.util.Log.i(TAG, "indonesiaSubjects count=${sorted.size}")
            indonesiaCache = System.currentTimeMillis() to sorted
            return sorted
    }

    private val categoryQueries = mapOf(
        "kdrama" to listOf("drama korea", "korean drama"),
        "indodrama" to listOf("drama indonesia", "sinetron indonesia"),
        "cdrama" to listOf("drama china", "chinese drama"),
        "anime" to listOf(
            "anime", "naruto", "one piece", "jujutsu kaisen", "solo leveling",
            "demon slayer", "attack on titan",
        ),
        "thaidrama" to listOf("drama thailand", "thai drama"),
        "jdrama" to listOf(
            "japanese drama", "j drama", "japan drama", "japanese series",
            "japanese tv drama", "alice in borderland", "midnight diner", "first love",
        ),
        "malaydrama" to listOf(
            "drama malaysia", "melayu drama", "malay drama", "drama melayu",
            "malaysian drama", "rindu", "cinta melayu",
        ),
        "western" to listOf("hollywood", "marvel", "netflix", "english movie", "top gun"),
        "reality" to listOf(
            "reality show", "variety show", "running man", "tempting island", "singles inferno",
        ),
        "romance" to listOf("romance movie", "romantic movie", "romance drama", "love story"),
        "action" to listOf("action movie", "john wick", "mission impossible"),
        "mystery" to listOf("mystery movie", "detective", "murder mystery", "thriller"),
        "hollywood" to listOf("hollywood", "marvel", "superhero"),
        "asia" to listOf("korea", "japan", "thailand"),
        "horror" to listOf("horror", "horor"),
        "series" to listOf("series", "season"),
        "movie" to listOf("full movie", "2026 movie"),
    )

    /** Hasil pencarian (subject) untuk satu keyword; gagal -> kosong. */
    private suspend fun searchSubjects(keyword: String, token: String): List<JSONObject> =
        withContext(Dispatchers.IO) {
            try {
                val base = activeHostOrFirst()
                val body = JSONObject()
                    .put("keyword", keyword)
                    .put("page", 1)
                    .put("pageSize", 20)
                val root = callJson(base, "/wefeed-mobile-bff/subject-api/search", body.toString(), token, false)
                extractList(root)
            } catch (e: Exception) {
                Log.w(TAG, "searchSubjects '$keyword' fail: ${e.message}")
                emptyList()
            }
        }

    private var categoryCache = java.util.concurrent.ConcurrentHashMap<String, Pair<Long, List<MediaItem>>>()

    /** Daftar film per kategori sintetis (moviebox-local). */
    suspend fun categoryItems(categoryId: String): List<MediaItem> = withContext(Dispatchers.IO) {
        val cat = categoryId.trim().lowercase(Locale.ROOT).let { if (it == "hoollywood") "hollywood" else it }
        categoryCache[cat]?.let { (t, list) ->
            if (System.currentTimeMillis() - t < 300_000 && list.isNotEmpty()) return@withContext list
        }
        val subjects = catalogSubjects()
        val token = boot() ?: return@withContext emptyList()
        val picked: ArrayList<JSONObject>
        if (cat == "indonesia" || cat == "indodrama") {
            // Indonesia = film asli buatan Indonesia; Indo Drama = serialnya saja.
            val indo = indonesiaSubjects(subjects, token)
            picked = ArrayList(if (cat == "indodrama") indo.filter { subjectTypeOf(it) == 2 } else indo)
        } else {
            picked = ArrayList(
                if (cat == "trending") subjects
                else subjects.filter { categoryMatches(cat, it) }
            )
            // enrichment: kategori wajib ada isinya -> tambah hasil pencarian yang lolos predikat
            if (cat != "trending" && picked.size < 24) {
                val queries = categoryQueries[cat] ?: emptyList()
                for (q in queries) {
                    for (s in searchSubjects(q, token)) {
                        if (categoryMatches(cat, s)) {
                            val id = s.optString("subjectId")
                            if (id.isNotBlank() && picked.none { it.optString("subjectId") == id }) picked.add(s)
                        }
                    }
                    if (picked.size >= 32) break
                }
                // fallback terakhir: jika strict tetap kosong, ambil hasil pencarian mentah agar kategori tidak kosong
                if (picked.isEmpty()) {
                    for (q in queries.take(2)) {
                        for (s in searchSubjects(q, token)) picked.add(s)
                    }
                }
            }
        }
        // Indonesian production selalu tampil; kategori lain wajib ber-subtitle
        // Indonesia & digabung per film (versi dub/Hindi hilang).
        val needsFilter = cat != "indonesia" && cat != "indodrama"
        val qualified = if (needsFilter) filterIndoOnly(picked, token) else picked
        val out = ArrayList<MediaItem>()
        val seen = HashSet<String>()
        for (s in qualified) {
            val m = mapSubjectToMediaItem(s) ?: continue
            if (seen.add(m.id ?: "")) out.add(m)
            if (out.size >= 48) break
        }
        android.util.Log.i(TAG, "categoryItems $cat picked=${picked.size} qualified=${qualified.size} out=${out.size}/pool=${subjects.size}")
        categoryCache[cat] = System.currentTimeMillis() to out
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
        // Seri: daftar episode diambil dari KOLEKSI UGC nyata (epTitle S{se}EP{ep}),
        // sumber yang benar-benar bisa diputar — bukan season-info yang bisa
        // berbeda dari isi koleksi (mis. Black Clover S2: season-info 3, koleksi 2).
        val fromCollection = if (totalEp > 1 || data.optInt("subjectType", 0) == 2) {
            runCatching { collectionEpisodes(subjectId) }.getOrNull()
        } else null
        val episodes = fromCollection?.takeIf { it.isNotEmpty() }
            ?: seasonInfo(subjectId)?.let { mapSeasonToEpisodes(it) }?.takeIf { it.isNotEmpty() }
            ?: if (totalEp > 0) (1..totalEp).map { EpisodeData(episodeId = "1_$it", episodeNumber = it) }
            else listOf(EpisodeData(episodeId = "1_1", episodeNumber = 1))
        val score = data.optDouble("score", 0.0).let { if (it <= 0.0) data.optDouble("showScore", 0.0) else it }
        val year = data.optString("releaseDate").takeIf { it.isNotBlank() }?.take(4)
            ?: data.optString("year").takeIf { it.isNotBlank() }
        val country = data.optString("countryName").takeIf { it.isNotBlank() }
            ?: data.optString("language").takeIf { it.isNotBlank() }
        val genre = data.optJSONArray("genreList")?.run {
            (0 until length()).mapNotNull { optJSONObject(it)?.optString("name")?.takeIf { n -> n.isNotBlank() } }
        }?.joinToString(" • ")?.takeIf { it.isNotBlank() }
            ?: data.optString("parent_type").takeIf { it.isNotBlank() }
        val metaLine = listOfNotNull(
            year,
            score.takeIf { it > 0.0 }?.let { String.format(java.util.Locale.US, "%.1f", it) },
            country,
            genre,
        ).joinToString("  •  ").takeIf { it.isNotBlank() }
        return DetailData(
            id = subjectId,
            title = title,
            description = desc,
            metaLine = metaLine,
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

    /**
     * Konversi SRT -> WebVTT ke file cache (ExoPlayer lebih andal dengan VTT).
     * Hasil URL berupa URI file lokal "...sb_<hash>.vtt"; hasil disimpan agar tidak diunduh ulang.
     */
    private suspend fun prepareSubtitleFiles(tracks: List<SubtitleTrack>): List<SubtitleTrack> =
        withContext(Dispatchers.IO) {
            val ctx = try { com.mikdadstream.app.AppGraph.appContext } catch (e: Throwable) { null }
            if (ctx == null) return@withContext tracks
            val dir = java.io.File(ctx.cacheDir, "subs").apply { mkdirs() }
            tracks.map { t ->
                val url = t.url
                if (url.isNullOrBlank()) {
                    t
                } else {
                    val pathLower = url.substringBefore('?').lowercase()
                    val ext = pathLower.substringAfterLast('.', "")
                    // Format yang sudah didukung ExoPlayer langsung.
                    val directlySupported = ext in setOf("vtt", "ttml", "dfxp", "ass", "ssa")
                    if (directlySupported) {
                        t
                    } else {
                        // SRT atau ekstensi tak dikenal: unduh, deteksi isi, jadikan VTT lokal.
                        val key = java.security.MessageDigest.getInstance("MD5")
                            .digest(url.toByteArray()).joinToString("") { "%02x".format(it) }
                            .take(24)
                        val f = java.io.File(dir, "sb2_$key.vtt")
                        val ok = if (f.exists() && f.length() > 0) true else run {
                            try {
                                val resp = http.newCall(
                                    okhttp3.Request.Builder().url(url).header("User-Agent", "ExoPlayerLib/2.19.1").build()
                                ).execute()
                                resp.use { r ->
                                    val code = r.code
                                    val body = r.body?.string()
                                    if (r.isSuccessful && !body.isNullOrBlank()) {
                                        val clean = body.removePrefix("\uFEFF")
                                        val headTxt = clean.trimStart()
                                        // Deteksi format dari ISI file (bukan ekstensi):
                                        // WEBVTT sudah jadi VTT; SRT punya timestamp
                                        // "00:00:01,000". Isi lain (HTML/JSON/galat)
                                        // JANGAN disimpan sebagai subtitle.
                                        val vtt = when {
                                            headTxt.startsWith("WEBVTT", true) -> clean
                                            Regex("(\\d{1,2}):(\\d{2}):(\\d{2}),(\\d{3})")
                                                .containsMatchIn(clean) -> srtToVtt(body)
                                            else -> null
                                        }
                                        if (vtt != null) {
                                            f.writeText(vtt)
                                            true
                                        } else {
                                            Log.w(
                                                TAG,
                                                "prepareSubtitle isi bukan SRT/VTT http=$code " +
                                                    "head=\"${headTxt.take(40)}\" ($url)",
                                            )
                                            false
                                        }
                                    } else {
                                        Log.w(TAG, "prepareSubtitle fetch gagal http=$code ($url)")
                                        false
                                    }
                                }
                            } catch (e: Exception) {
                                android.util.Log.w(TAG, "subtitle fetch fail ($url): ${e.message}")
                                false
                            }
                        }
                        Log.i(TAG, "prepareSubtitle ${t.lang}/${t.label} ext=$ext converted=$ok -> ${if (ok) "local vtt" else "remote"}")
                        if (ok) t.copy(url = android.net.Uri.fromFile(f).toString()) else t
                    }
                }
            }
        }

    /** Konversi konten SRT ke WebVTT: header WEBVTT + koma -> titik pada timestamp. */
    private fun srtToVtt(srt: String): String {
        val clean = srt.removePrefix("\uFEFF").replace("\r\n", "\n").replace('\r', '\n')
        val sb = StringBuilder("WEBVTT\n\n")
        val ts = Regex("(\\d{1,2}):(\\d{2}):(\\d{2}),(\\d{3})")
        sb.append(ts.replace(clean) { m -> "${m.groupValues[1]}:${m.groupValues[2]}:${m.groupValues[3]}.${m.groupValues[4]}" })
        return sb.toString()
    }

    fun firstBySubject(subjectId: String): JSONObject? {
        val token = boot() ?: return null
        val base = activeHostOrFirst()
        return callJson(base, "/wefeed-mobile-bff/ugc/video/first-by-subject?subjectId=$subjectId", null, token, false)
    }

    private val collectionEpCache = java.util.concurrent.ConcurrentHashMap<String, Pair<Long, List<EpisodeData>>>()

    // Daftar episode dari koleksi UGC (download-list, 50/halaman) = episode yang
    // benar-benar punya sumber video. epTitle: "S1EP1", "S2EP1", atau "EP1".
    suspend fun collectionEpisodes(subjectId: String): List<EpisodeData>? = withContext(Dispatchers.IO) {
        val now = System.currentTimeMillis()
        collectionEpCache[subjectId]?.let { (t, list) ->
            if (now - t < 5 * 60_000L) return@withContext list
        }
        try {
            val first = firstBySubject(subjectId) ?: return@withContext null
            val cid = first.optJSONObject("data")
                ?.optJSONObject("belongToCollection")?.optString("collectionId")
                ?.takeIf { it.isNotBlank() } ?: return@withContext null
            val out = ArrayList<EpisodeData>()
            var page = 1
            while (page <= 40) {
                val list = collectionDownloadList(cid, page, 50) ?: break
                val items = list.optJSONObject("data")?.optJSONArray("items") ?: break
                if (items.length() == 0) break
                for (i in 0 until items.length()) {
                    val item = items.optJSONObject(i) ?: continue
                    val t = item.optString("epTitle").trim().uppercase(Locale.ROOT)
                    if (t.isBlank()) continue
                    val m = Regex("^S(\\d+)EP(\\d+)$").find(t)
                    val m2 = Regex("^EP(\\d+)$").find(t)
                    val se = m?.groupValues?.getOrNull(1)?.toIntOrNull() ?: 1
                    val ep = m?.groupValues?.getOrNull(2)?.toIntOrNull()
                        ?: m2?.groupValues?.getOrNull(1)?.toIntOrNull()
                        ?: continue
                    out.add(EpisodeData(episodeId = "${se}_${ep}", episodeNumber = ep))
                }
                if (items.length() < 50) break
                page++
            }
            val episodes = out.distinctBy { it.episodeId }
            if (episodes.isEmpty()) null else {
                collectionEpCache[subjectId] = System.currentTimeMillis() to episodes
                episodes
            }
        } catch (e: Exception) {
            Log.w(TAG, "collectionEpisodes error: ${e.message}")
            null
        }
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

    private fun normalizedCaptionLang(lan: String): String {
        val raw = lan.trim().lowercase()
        return when {
            raw.startsWith("in_") || raw.startsWith("id_") || raw == "in" || raw == "id" || raw.contains("indonesi") || raw.contains("bahasa") -> "id"
            raw.isBlank() -> raw
            else -> raw.substringBefore('_').substringBefore('-')
        }
    }

    /** Panggil get-ext-captions di beberapa host sampai dapat list tidak kosong. */
    private fun extCaptionsFor(subjectId: String, rid: String?, token: String, se: Int, ep: Int): org.json.JSONArray? {
        val base = activeHostOrFirst()
        val hostOrder = (listOf(base) + HOSTS).distinct()
        for (h in hostOrder) {
            var pq = "/wefeed-mobile-bff/subject-api/get-ext-captions?subjectId=$subjectId"
            if (!rid.isNullOrBlank()) pq += "&resourceId=$rid"
            if (ep > 1) pq += "&se=${if (se > 0) se else 1}&ep=$ep"
            val root = runCatching { callJson(h, pq, null, token, false) }.getOrNull()
            val data = root?.optJSONObject("data")
            val caps = data?.optJSONArray("extCaptions")
                ?: data?.optJSONArray("subTitleList")
                ?: data?.optJSONArray("captions")
            if (caps != null && caps.length() > 0) return caps
        }
        return null
    }

    private fun captionsToTracks(caps: org.json.JSONArray?): List<SubtitleTrack> {
        val out = ArrayList<SubtitleTrack>()
        if (caps == null) return out
        val seen = HashSet<String>()
        // Indonesia & Melayu TERPISAH: tidak ada priorisasi/penggabungan,
        // setiap kode bahasa jadi track masing-masing.
        for (i in 0 until caps.length()) {
            val c = caps.optJSONObject(i) ?: continue
            val url = c.optString("url").takeIf { it.isNotBlank() } ?: continue
            val lan = c.optString("lan").takeIf { it.isNotBlank() } ?: continue
            val norm = normalizedCaptionLang(lan)
            // Dedup per kode bahasa hasil normalisasi (hindari track dobel).
            if (!seen.add(norm)) continue
            out.add(
                SubtitleTrack(
                    label = c.optString("lanName").takeIf { it.isNotBlank() }
                        ?: if (norm == "id") "Indonesian" else lan,
                    // Simpan kode yang sudah dinormalisasi ("in_id" -> "id") agar
                    // pemutar tidak salah mengenali sebagai Melayu.
                    lang = norm,
                    url = url,
                )
            )
        }
        return out
    }

    // Subtitle eksternal: /subject-api/get-ext-captions (pola APK: subjectId + resourceId [+ se/ep] -> data.extCaptions).
    // Fallback: bila caption kosong, cari versi judul sama (tipe sama) yang punya caption Indonesia.
    suspend fun fetchSubtitles(subjectId: String, se: Int = 1, ep: Int = 1): List<SubtitleTrack> =
        withContext(Dispatchers.IO) {
            try {
                val token = boot() ?: return@withContext emptyList()
                val base = activeHostOrFirst()
                val detailRoot = runCatching { fetchSubject(base, subjectId, token) }.getOrNull()
                val detailData = detailRoot?.optJSONObject("data")
                // rid WAJIB per-episode: resourceDetectors index = episode-1.
                // Sebelumnya selalu index 0 -> subtitle EP1 dipakai di EP2+ (tidak sinkron).
                val rdArr = detailData?.optJSONArray("resourceDetectors")
                val lastIdx = (rdArr?.length() ?: 1).coerceAtLeast(1) - 1
                val rd = rdArr?.optJSONObject((ep - 1).coerceIn(0, lastIdx)) ?: rdArr?.optJSONObject(0)
                val rid = rd?.optString("resourceId")?.takeIf { it.isNotBlank() }
                    ?: rdArr?.optJSONObject(0)?.optString("resourceId")?.takeIf { it.isNotBlank() }

                // HANYA sumber langsung (subjek + resourceId milik video ini).
                val caps = extCaptionsFor(subjectId, rid, token, se, ep)
                val out = captionsToTracks(caps)
                Log.i(TAG, "fetchSubtitles caps=${caps?.length() ?: 0} out=${out.size} subj=$subjectId se=$se ep=$ep direct")
                // Bila versi yang diputar TIDAK punya caption Indonesia (mis. versi
                // dub US/UK/India), ambil caption dari versi ASLI film yang sama
                // (dubs.original) — video film sama, jadi tetap sinkron.
                val hasIndo = out.any { it.lang == "id" || it.label?.contains("ndonesi", true) == true }
                if (!hasIndo) {
                    val dubs = detailData?.optJSONArray("dubs")
                    val origId = dubs?.let { arr ->
                        (0 until arr.length()).mapNotNull { arr.optJSONObject(it) }
                            .firstOrNull { it.optBoolean("original", false) }
                            ?.optString("subjectId")?.takeIf { it.isNotBlank() }
                    }
                    if (!origId.isNullOrBlank() && origId != subjectId) {
                        val origRid = subjectDetailDataForRid(origId, ep) ?: rid
                        val origCaps = extCaptionsFor(origId, origRid, token, se, ep)
                        val origOut = captionsToTracks(origCaps)
                        val origIndo = origOut.any {
                            it.lang == "id" || it.label?.contains("ndonesi", true) == true
                        }
                        Log.i(
                            TAG,
                            "fetchSubtitles versi dub tanpa Indonesia -> original=$origId indo=$origIndo",
                        )
                        if (origIndo) return@withContext prepareSubtitleFiles(origOut)
                    }
                }
                prepareSubtitleFiles(out)
            } catch (e: Exception) {
                Log.w(TAG, "fetchSubtitles error: ${e.message}")
                emptyList()
            }
        }

    private suspend fun subjectDetailDataForRid(subjectId: String, ep: Int = 1): String? {
        val d = subjectDetail(subjectId)
        val arr = d?.optJSONObject("data")?.optJSONArray("resourceDetectors")
        val lastIdx = (arr?.length() ?: 1).coerceAtLeast(1) - 1
        return arr?.optJSONObject((ep - 1).coerceIn(0, lastIdx))
            ?.optString("resourceId")?.takeIf { it.isNotBlank() }
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
            val arr = d?.optJSONObject("data")?.optJSONArray("resourceDetectors")
            val last = (arr?.length() ?: 1).coerceAtLeast(1) - 1
            val r = arr?.optJSONObject((ep - 1).coerceIn(0, last))
                ?.optString("resourceId")
                ?: arr?.optJSONObject(0)?.optString("resourceId")
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
            // ANAlisis API: download-list max 50 item/halaman ("S1EP1".."S2EP2"),
            // episode musim lanjut ada di halaman berikutnya. WAJIB telusuri
            // semua halaman — sebelumnya hanya halaman 1 lalu fallback item[0]
            // (S1EP1) sehingga episode musim 2 selalu memutar EP1.
            val allItems = ArrayList<JSONObject>()
            var page = 1
            while (page <= 40) {
                val list = collectionDownloadList(collectionId, page, 50)
                if (list == null) {
                    android.util.Log.w(TAG, "collectionDownloadList null page=$page")
                    break
                }
                val dataObj = list.optJSONObject("data")
                val items = dataObj?.optJSONArray("items")
                    ?: dataObj?.optJSONArray("list")
                if (items == null || items.length() == 0) break
                for (i in 0 until items.length()) items.optJSONObject(i)?.let { allItems.add(it) }
                if (items.length() < 50) break
                page++
            }
            android.util.Log.i(TAG, "fetchRealStream items total=${allItems.size} pages=$page se=$se ep=$ep")

            fun epTitleOf(item: JSONObject?): String =
                item?.optString("epTitle")?.trim()?.uppercase(Locale.ROOT) ?: ""

            // Cocokkan judul persis: "S{se}EP{ep}" (format API) — utamakan musim yang diminta.
            var matched = allItems.firstOrNull { epTitleOf(it) == "S${se}EP${ep}" }
            if (matched == null) {
                // Format lama/anime tanpa musim: "EP{ep}" atau "E{ep}" (tepat, bukan contains).
                matched = allItems.firstOrNull {
                    val t = epTitleOf(it)
                    t == "EP$ep" || t == "E$ep"
                }
            }
            if (matched == null && se <= 1) {
                matched = allItems.firstOrNull { epTitleOf(it) == "S1EP$ep" }
            }
            if (matched != null) {
                android.util.Log.i(TAG, "fetchRealStream matched epTitle=${epTitleOf(matched)}")
                collectCleanResources(matched, candidates)
            }
            // Jika judul tak cocok, telusuri semua item by resource id (rid unik per seri).
            if (candidates.isEmpty() && !rid.isNullOrBlank()) {
                for (item in allItems) {
                    val resources = item.optJSONArray("resources") ?: continue
                    var hit = false
                    for (r in 0 until resources.length()) {
                        val res = resources.optJSONObject(r) ?: continue
                        if (res.optString("id") == rid) { hit = true; break }
                    }
                    if (hit) {
                        collectCleanResources(item, candidates)
                        if (candidates.isNotEmpty()) {
                            android.util.Log.i(TAG, "fetchRealStream matched by rid epTitle=${epTitleOf(item)}")
                            break
                        }
                    }
                }
            }
            // Fallback item pertama HANYA untuk episode 1 musim 1 (film/episode tunggal).
            // Jangan pernah memakai item[0] untuk episode lain — itulah S1EP1.
            if (candidates.isEmpty() && se <= 1 && ep <= 1) {
                collectCleanResources(allItems.firstOrNull(), candidates)
            }
        }

        // Fallback APK: /ugc/video/download?ugcVideoId= saat list kosong.
        // JANGAN dipakai untuk episode tertentu: endpoint ini mengembalikan video
        // PERTAMA (S1EP1) sehingga episode lain akan memutar EP1.
        val allowFirstVideo = collectionId == null || (se <= 1 && ep <= 1)
        if (candidates.isEmpty() && allowFirstVideo && !ugcVideoId.isNullOrBlank()) {
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
            // resourceId WAJIB per-episode (index = ep-1), bukan selalu index 0 —
            // sebelumnya semua episode memutar konten episode 1.
            val rdArr = detailData?.optJSONArray("resourceDetectors")
            val lastIdx = (rdArr?.length() ?: 1).coerceAtLeast(1) - 1
            val resourceId = rdArr?.optJSONObject((ep - 1).coerceIn(0, lastIdx))
                ?.optString("resourceId")?.takeIf { it.isNotBlank() }
                ?: rdArr?.optJSONObject(0)?.optString("resourceId")?.takeIf { it.isNotBlank() }

            val variants = listOf(
                // rid unik per episode: panggil play-info dengan rid dulu (SANS se/ep)
                // supaya server MENGHORMATI episode yang dipilih (Sebelumnya varian
                // se/ep+rid bisa diabaikan server lalu jatuh ke {1,1} -> EP1 diputar).
                { fetchPlayInfo(base, subjectId, 0, 0, resourceId, token) },
                { fetchPlayInfo(base, subjectId, if (se > 0) se else 1, if (ep > 0) ep else 1, resourceId, token) },
                { fetchPlayInfo(base, subjectId, if (se > 0) se else 1, if (ep > 0) ep else 1, null, token) },
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
