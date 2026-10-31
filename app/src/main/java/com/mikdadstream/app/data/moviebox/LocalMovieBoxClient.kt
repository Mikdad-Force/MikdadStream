package com.mikdadstream.app.data.moviebox

import android.util.Base64
import android.util.Log
import com.mikdadstream.app.data.local.CountrySelection
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
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.locks.ReentrantLock
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.concurrent.withLock
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withLock
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
    private const val H5_BASE = "https://h5-api.aoneroom.com"

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

    // Scope latar untuk "race" varian play-info: hasil pertama yang valid dipakai
    // tanpa menunggu varian lain yang masih berjalan (tidak menahan pemutaran).
    private val fetchScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

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
        val location = CountrySelection.current()
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
        info.put("system_language", location.language)
        info.put("net", "wifi")
        info.put("region", location.code)
        info.put("timezone", location.timezone)
        info.put("sp_code", location.spCode)
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
                    server = "Utama",
                    format = fmt,
                    quality = entry.optString("resolution", "").takeIf { it.isNotBlank() } ?: "Auto",
                    url = url,
                    headers = hdrs,
                ),
            )
        }
        return out
    }

    suspend fun trendingV2(page: Int = 1): JSONObject? = withContext(Dispatchers.IO) {
        val token = boot() ?: return@withContext null
        val base = activeHostOrFirst()
        val body = if (page <= 1) "{}" else JSONObject().put("page", page).toString()
        callJson(base, "/wefeed-mobile-bff/subject-api/trending/v2", body, token, false)
    }

    /** Beberapa halaman trending (API hanya 8 per halaman) lalu dedup per subjectId. */
    private suspend fun trendingAll(maxPages: Int = 3): List<JSONObject> {
        val out = LinkedHashMap<String, JSONObject>()
        for (page in 1..maxPages) {
            extractList(trendingV2(page)).forEach { s ->
                val id = subjectIdOf(s)
                if (id.isNotBlank() && !out.containsKey(id)) out[id] = s
            }
        }
        return out.values.toList()
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
        // Pakai cache yang sama dengan fetchStreams: detail yang sudah dimuat
        // layar detail langsung dipakai saat mencari URL video (hemat 1 request
        // sehingga pemutaran lebih cepat / tanpa "mencari sumber").
        fetchSubjectCached(base, subjectId, token)
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
            source = "Utama",
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

    /** Ambil semua subject dari respons tab-operating (rows -> subjects), dedup per id. */
    private fun tabSubjects(root: JSONObject?): List<JSONObject> {
        if (root == null) return emptyList()
        val out = LinkedHashMap<String, JSONObject>()
        fun add(o: JSONObject?) {
            if (o == null) return
            val id = subjectIdOf(o)
            if (id.isNotBlank() && !out.containsKey(id)) out[id] = o
        }
        val items = root.optJSONObject("data")?.optJSONArray("items")
        if (items != null) {
            for (i in 0 until items.length()) {
                val item = items.optJSONObject(i) ?: continue
                val arr = item.optJSONArray("subjects") ?: continue
                for (j in 0 until arr.length()) {
                    val o = arr.optJSONObject(j) ?: continue
                    add(o.optJSONObject("subject") ?: o)
                }
            }
        }
        if (out.isEmpty()) extractList(root).forEach(::add)
        return out.values.toList()
    }

    private var catalogCache: Pair<Long, List<JSONObject>>? = null

    /** Katalog gabungan: trending/v2 + rows tab-operating (tab 1 & 2), dedup per subjectId. */
    suspend fun catalogSubjects(): List<JSONObject> = withContext(Dispatchers.IO) {
        val cached = catalogCache
        if (cached != null && System.currentTimeMillis() - cached.first < 600_000L) {
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
        trendingAll().forEach(::add)
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

    /** Konten berlokasi/produksi Indonesia (bukan sekadar bahasa "Bahasa"). */
    private fun isIndonesiaCountry(o: JSONObject): Boolean =
        o.optString("countryName").lowercase(Locale.ROOT).contains("indonesia")

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

    /** Kategori Indonesia: film asli produksi Indonesia. Katalog metadata + pencarian keyword khas Indonesia secara cepat. */
    private suspend fun indonesiaSubjects(subjects: List<JSONObject>, token: String): List<JSONObject> {
        val cached = indonesiaCache
        if (cached != null && System.currentTimeMillis() - cached.first < 1_800_000L) {
            return cached.second
        }
        val out = LinkedHashMap<String, JSONObject>()
        // 1. Dari katalog lokal/in-memory langsung tampil (0 ms)
        for (s in subjects) {
            if (isIndoText(s.optString("countryName")) || isIndoText(s.optString("language"))) {
                val id = s.optString("subjectId")
                if (id.isNotBlank()) out[id] = s
            }
        }
        // 2. Jika item masih kurang, ambil pencarian tambahan secara paralel (bukan sekuensial)
        if (out.size < 30) {
            coroutineScope {
                listOf("indonesia", "film indonesia").map { q ->
                    async {
                        val body = org.json.JSONObject()
                            .put("keyword", q)
                            .put("page", 1)
                            .put("pageSize", 20)
                            .put("sort", "Latest")
                        runCatching {
                            withContext(Dispatchers.IO) {
                                val base0 = activeHostOrFirst()
                                callJson(base0, "/wefeed-mobile-bff/subject-api/search", body.toString(), token, false)
                            }
                        }.getOrNull()
                    }
                }.awaitAll().forEach { root ->
                    val arr = extractList(root)
                    for (s in arr) {
                        if (isIndoText(s.optString("countryName")) || isIndoText(s.optString("language"))) {
                            val id = s.optString("subjectId")
                            if (id.isNotBlank()) out[id] = s
                        }
                    }
                }
            }
        }
        val list = out.values.toList()
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
    private suspend fun searchSubjects(keyword: String, token: String, page: Int = 1): List<JSONObject> =
        withContext(Dispatchers.IO) {
            try {
                val base = activeHostOrFirst()
                val body = JSONObject()
                    .put("keyword", keyword)
                    .put("page", page)
                    .put("pageSize", 20)
                val root = callJson(base, "/wefeed-mobile-bff/subject-api/search", body.toString(), token, false)
                extractList(root)
            } catch (e: Exception) {
                Log.w(TAG, "searchSubjects '$keyword' fail: ${e.message}")
                emptyList()
            }
        }

    private var categoryCache = java.util.concurrent.ConcurrentHashMap<String, Pair<Long, List<MediaItem>>>()

    /** Tab katalog khusus per kategori (isi jauh lebih banyak dari tab beranda). */
    private val categoryExtraTabs = mapOf(
        "kdrama" to listOf(18),
        "cdrama" to listOf(18),
        "western" to listOf(19),
    )

    private val liveCache = java.util.concurrent.ConcurrentHashMap<String, Pair<Long, List<MediaItem>>>()

    /** Buang cache katalog/live agar lokasi negara baru langsung terpakai. */
    fun onRegionChanged() {
        catalogCache = null
        indonesiaCache = null
        categoryCache.clear()
        liveCache.clear()
        indoCache.clear()
    }

    /**
     * Kanal Live TV / Live Sport dari playlist publik iptv-org.
     * MovieBox tidak menyediakan API live, jadi kanal diputar langsung (HLS).
     * id item = "live:<base64url(url)>|<base64url(nama)>" dan ditangani
     * AppNavGraph.openDetail dengan membuka pemutar langsung.
     */
    suspend fun liveChannelItems(kind: String): List<MediaItem> = withContext(Dispatchers.IO) {
        val key = kind.trim().lowercase(Locale.ROOT)
        liveCache[key]?.let { (t, list) ->
            if (System.currentTimeMillis() - t < 300_000 && list.isNotEmpty()) {
                return@withContext list
            }
        }
        val isSport = key == "livesport"
        // Live TV / Sport TV mengikuti lokasi negara yang dipilih di Pengaturan.
        val location = CountrySelection.current()
        val urls = listOf(
            "https://iptv-org.github.io/iptv/countries/${location.playlist}.m3u",
            "https://raw.githubusercontent.com/iptv-org/iptv/master/streams/${location.playlist}.m3u",
        )
        var body: String? = null
        for (u in urls) {
            body = runCatching {
                http.newCall(
                    okhttp3.Request.Builder().url(u)
                        .header("User-Agent", "MikdadStream/1.0").build(),
                ).execute().use { r -> if (r.isSuccessful) r.body?.string() else null }
            }.getOrNull()
            if (!body.isNullOrBlank()) break
        }
        if (body.isNullOrBlank()) {
            Log.w(TAG, "livePlaylist $key gagal diunduh")
            return@withContext emptyList()
        }
        val out = ArrayList<MediaItem>()
        val seen = HashSet<String>()
        var pendingName: String? = null
        var pendingLogo: String? = null
        var pendingGroup: String? = null
        var pendingId: String? = null
        for (raw in body.lineSequence()) {
            val line = raw.trim()
            if (line.isEmpty()) continue
            if (line.startsWith("#EXTINF", true)) {
                // Nama = teks setelah atribut terakhir (bisa saja ada koma di
                // group-title); fallback ke setelah koma pertama.
                val q = line.lastIndexOf('"')
                val name = if (q >= 0) line.substring(q + 1).removePrefix(",").trim()
                else line.substringAfter(',', "").trim()
                if (name.isEmpty()) continue
                pendingName = name
                pendingLogo = Regex("tvg-logo=\"([^\"]*)\"").find(line)
                    ?.groupValues?.getOrNull(1)?.takeIf { it.isNotBlank() }
                pendingGroup = Regex("group-title=\"([^\"]*)\"").find(line)
                    ?.groupValues?.getOrNull(1)?.takeIf { it.isNotBlank() }
                pendingId = Regex("tvg-id=\"([^\"]*)\"").find(line)
                    ?.groupValues?.getOrNull(1)?.takeIf { it.isNotBlank() }
                continue
            }
            if (line.startsWith("#")) continue
            val name = pendingName
            val url = line
            if (name == null || !url.startsWith("http", true)) {
                pendingName = null
                pendingId = null
                continue
            }
            val channelId = pendingId.orEmpty()
            if (isSport) {
                val n = name.lowercase(Locale.ROOT)
                val g = pendingGroup.orEmpty().lowercase(Locale.ROOT)
                val i = channelId.lowercase(Locale.ROOT)
                val cc = location.playlist
                val inCountry = i.endsWith(".$cc") || i.contains(".$cc@")
                val sporty = g.contains("sport") || listOf(
                    "sport", "bola", "champions", "spotv", "sportstars", "voli",
                    "arena", "fight", "golf", "motogp", "badminton", "liga",
                ).any { n.contains(it) || i.contains(it) }
                if (!inCountry || !sporty) {
                    pendingName = null
                    pendingId = null
                    pendingLogo = null
                    pendingGroup = null
                    continue
                }
            }
            if (seen.add(url)) {
                val label = if (pendingGroup.isNullOrBlank()) name else "$name \u00b7 $pendingGroup"
                val u = android.util.Base64.encodeToString(
                    url.toByteArray(),
                    android.util.Base64.URL_SAFE or android.util.Base64.NO_WRAP,
                )
                val n = android.util.Base64.encodeToString(
                    name.toByteArray(),
                    android.util.Base64.URL_SAFE or android.util.Base64.NO_WRAP,
                )
                val id = "live:$u|$n"
                out.add(
                    MediaItem(
                        key = id,
                        id = id,
                        cover = pendingLogo,
                        title = label,
                        source = if (isSport) "Sport TV" else "Live TV",
                        type = "live",
                    )
                )
            }
            pendingName = null
            pendingLogo = null
            pendingGroup = null
            pendingId = null
            if (out.size >= 400) break
        }
        Log.i(TAG, "livePlaylist $key channels=${out.size}")
        liveCache[key] = System.currentTimeMillis() to out
        out
    }

    /** Satu halaman pencarian Short TV Indonesia (subjectType=7); kembalikan item + hasMore. */
    private suspend fun searchShortTvPage(apiPage: Int, token: String): Pair<List<JSONObject>, Boolean> {
        val base = activeHostOrFirst()
        val body = JSONObject()
            .put("keyword", "indonesia")
            .put("page", apiPage)
            .put("pageSize", 20)
            .put("subjectType", 7)
            .toString()
        val root = callJson(base, "/wefeed-mobile-bff/subject-api/search", body, token, false)
        val subjects = extractList(root)
        val hasMore = root?.optJSONObject("data")?.optJSONObject("pager")
            ?.optBoolean("hasMore", subjects.isNotEmpty()) ?: subjects.isNotEmpty()
        return subjects to hasMore
    }

    /** Short TV Indonesia tanpa batas: tiap halaman UI menarik 3 halaman API (30 judul). */
    private suspend fun shortTvPage(page: Int, apiPagesPerUiPage: Int = 3): Pair<List<MediaItem>, Boolean> =
        withContext(Dispatchers.IO) {
            val token = boot() ?: return@withContext emptyList<MediaItem>() to false
            val out = ArrayList<MediaItem>()
            val seen = HashSet<String>()
            var hasMore = false
            val firstApiPage = (page.coerceAtLeast(1) - 1) * apiPagesPerUiPage + 1
            for (apiPage in firstApiPage until firstApiPage + apiPagesPerUiPage) {
                val (subjects, more) = searchShortTvPage(apiPage, token)
                hasMore = more
                for (s in subjects) {
                    if (!isIndonesiaCountry(s) || subjectTypeOf(s) != 7) continue
                    val m = mapSubjectToMediaItem(s) ?: continue
                    if (seen.add(m.id ?: "")) out.add(m)
                }
                if (!more) break
            }
            Log.i(TAG, "shortTvPage uiPage=$page apiFrom=$firstApiPage items=${out.size} hasMore=$hasMore")
            out to hasMore
        }

    /** Halaman kategori untuk Moviebox Local (Short TV benar-benar berpaginasi). */
    suspend fun categoryPage(categoryId: String, page: Int): Pair<List<MediaItem>, Boolean> {
        val cat = categoryId.trim().lowercase(Locale.ROOT).let { if (it == "hoollywood") "hollywood" else it }
        if (cat == "shorttv") return shortTvPage(page)
        val all = categoryItems(cat)
        val pageSize = 60
        val from = ((page.coerceAtLeast(1)) - 1) * pageSize
        if (from >= all.size) return emptyList<MediaItem>() to false
        val to = minOf(all.size, from + pageSize)
        return all.subList(from, to) to (to < all.size)
    }

    /** Daftar film per kategori sintetis (moviebox-local). */
    suspend fun categoryItems(categoryId: String): List<MediaItem> = withContext(Dispatchers.IO) {
        val cat = categoryId.trim().lowercase(Locale.ROOT).let { if (it == "hoollywood") "hollywood" else it }
        if (cat == "livetv" || cat == "livesport") {
            return@withContext liveChannelItems(cat)
        }
        if (cat == "shorttv") {
            return@withContext shortTvPage(1).first
        }
        categoryCache[cat]?.let { (t, list) ->
            if (System.currentTimeMillis() - t < 1_800_000L && list.isNotEmpty()) return@withContext list
        }
        val token = boot() ?: return@withContext emptyList()
        // TRENDING: ambil langsung dari trendingAll (cepat, tanpa probe subtitle ke tiap judul)
        if (cat == "trending") {
            val raw = trendingAll()
            val use = raw.filterNot {
                isHindiText(it.optString("title")) || isHindiText(it.optString("language"))
            }
            val out = ArrayList<MediaItem>()
            val seen = HashSet<String>()
            for (s in use) {
                val m = mapSubjectToMediaItem(s) ?: continue
                if (seen.add(m.id ?: "")) out.add(m)
            }
            Log.i(TAG, "categoryItems trending count=${out.size}")
            categoryCache[cat] = System.currentTimeMillis() to out
            return@withContext out
        }
        val extra = categoryExtraTabs[cat].orEmpty().flatMap { tabSubjects(tabOperating(it)) }
        val subjects = if (extra.isEmpty()) catalogSubjects() else catalogSubjects() + extra
        val picked: ArrayList<JSONObject>
        if (cat == "indonesia" || cat == "indodrama") {
            // Indonesia = film asli buatan Indonesia; Indo Drama = serialnya saja.
            val indo = indonesiaSubjects(subjects, token)
            picked = ArrayList(if (cat == "indodrama") indo.filter { subjectTypeOf(it) == 2 } else indo)
        } else {
            picked = ArrayList(subjects.filter { categoryMatches(cat, it) })
            // enrichment cepat: jika hasil in-memory kurang dari 12, cari query pertama saja
            if (cat != "trending" && picked.size < 12) {
                val q = categoryQueries[cat]?.firstOrNull()
                if (q != null) {
                    val searchResults = runCatching { searchSubjects(q, token, 1) }.getOrDefault(emptyList())
                    for (s in searchResults) {
                        if (categoryMatches(cat, s)) {
                            val id = s.optString("subjectId")
                            if (id.isNotBlank() && picked.none { it.optString("subjectId") == id }) picked.add(s)
                        }
                    }
                }
            }
        }
        // Filter judul Hindi dan urutkan rilis/update terbaru di atas
        val usable = picked.filterNot {
            isHindiText(it.optString("title")) || isHindiText(it.optString("language"))
        }.sortedByDescending { subjectUpdateScore(it) }

        val out = ArrayList<MediaItem>()
        val seen = HashSet<String>()
        for (s in usable) {
            val m = mapSubjectToMediaItem(s) ?: continue
            if (seen.add(m.id ?: "")) out.add(m)
        }
        android.util.Log.i(TAG, "categoryItems $cat out=${out.size}/pool=${subjects.size}")
        categoryCache[cat] = System.currentTimeMillis() to out
        out
    }

    suspend fun trendingItems(): List<MediaItem> =
        withContext(Dispatchers.IO) {
            val token = boot() ?: return@withContext emptyList()
            val flat = trendingAll()
            val filtered = runCatching { filterIndoOnly(flat, token) }.getOrDefault(emptyList())
            val use = filtered.ifEmpty { flat }
            use.mapNotNull { mapSubjectToMediaItem(it) }
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
            // Bersihkan cache subtitle versi lama yang bisa berisi HTML error
            // atau tertukar antar bahasa (penyebab "pilih Indo keluar Melayu").
            runCatching {
                dir.listFiles()?.forEach { f ->
                    val n = f.name
                    if (n.startsWith("sb2_") || n.startsWith("sb_") ||
                        n.startsWith("sb4_") || n.startsWith("sbshift_")
                    ) {
                        f.delete()
                    }
                }
            }
            // Unduh/konversi semua subtitle PARALEL: sebelumnya berurutan
            // (9 bahasa = 9 request beruntun) sehingga klik episode terasa lama.
            coroutineScope {
                tracks.map { t ->
                    async { prepareSubtitleOne(t, dir) }
                }.awaitAll()
            }
        }

    private suspend fun prepareSubtitleOne(t: SubtitleTrack, dir: java.io.File): SubtitleTrack {
        val url = t.url
        return if (url.isNullOrBlank()) {
                    t
                } else {
                    val pathLower = url.substringBefore('?').lowercase()
                    val ext = pathLower.substringAfterLast('.', "")
                    // Hanya format kaya (TTML/SSA) yang dibiarkan remote. SRT dan
                    // VTT SELALU diunduh + divalidasi + dijadikan VTT lokal:
                    // ExoPlayer TIDAK punya parser SubRip, dan URL .vtt kadang
                    // isinya SRT/HTML. Tanpa ini track gagal terdaftar sehingga
                    // bahasa tidak muncul di menu Subtitle.
                    val directlySupported = ext in setOf("ttml", "dfxp", "ass", "ssa")
                    if (directlySupported) {
                        t
                    } else {
                        // SRT atau ekstensi tak dikenal: unduh, deteksi isi, jadikan VTT lokal.
                        // Sertakan kode bahasa pada cache key agar file Indonesia
                        // dan Melayu tidak pernah berbagi cache yang sama.
                        val key = java.security.MessageDigest.getInstance("MD5")
                            .digest("${t.lang ?: ""}|$url".toByteArray())
                            .joinToString("") { "%02x".format(it) }
                            .take(24)
                        val f = java.io.File(dir, "sb3_$key.vtt")
                        // Cache-hit hanya dipakai bila isinya VTT valid (header +
                        // timestamp). Cache lama/rusak (mis. HTML error page dari
                        // v2.1.24) dihapus dan diunduh ulang.
                        val cachedOk = if (f.exists() && f.length() > 0) {
                            val cached = runCatching { f.readText() }.getOrDefault("")
                            val c = cached.removePrefix("\uFEFF").trimStart()
                            if (c.startsWith("WEBVTT", true) && c.contains("-->")) {
                                true
                            } else {
                                f.delete()
                                false
                            }
                        } else {
                            false
                        }
                        val ok = if (cachedOk) true else run {
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
                                            headTxt.startsWith("WEBVTT", true) -> normalizeVttHours(clean)
                                            Regex("(\\d{1,2}):(\\d{2}):(\\d{2}),(\\d{3})")
                                                .containsMatchIn(clean) -> srtToVtt(clean)
                                            clean.contains("-->") &&
                                                Regex("(\\d{1,2}):(\\d{2}):(\\d{2})\\.(\\d{3})")
                                                    .containsMatchIn(clean) ->
                                                normalizeVttHours("WEBVTT\n\n" + clean.trimStart())
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
                        Log.i(TAG, "prepareSubtitle ${t.lang}/${t.label} ext=$ext cached=$cachedOk converted=$ok -> ${if (ok) "local vtt" else "REMOTE (track bisa tidak terdaftar)"}")
                        if (ok) t.copy(url = android.net.Uri.fromFile(f).toString()) else t
                    }
                }
    }

    /** Konversi konten SRT ke WebVTT: header WEBVTT + koma -> titik pada timestamp. */
    private fun srtToVtt(srt: String): String {
        val clean = srt.removePrefix("\uFEFF").replace("\r\n", "\n").replace('\r', '\n')
        val sb = StringBuilder("WEBVTT\n\n")
        val ts = Regex("(\\d{1,2}):(\\d{2}):(\\d{2}),(\\d{3})")
        sb.append(ts.replace(clean) { m ->
            // WebVTT mewajibkan jam 2 digit; SRT sering memakai "0:00:01,000".
            "%02d:%s:%s.%s".format(
                m.groupValues[1].toInt(),
                m.groupValues[2], m.groupValues[3], m.groupValues[4],
            )
        })
        return sb.toString()
    }

    /** Rapikan jam pada timestamp WebVTT menjadi 2 digit. */
    private fun normalizeVttHours(vtt: String): String {
        val ts = Regex("(\\d{1,2}):(\\d{2}):(\\d{2})\\.(\\d{3})")
        return ts.replace(vtt) { m ->
            "%02d:%s:%s.%s".format(
                m.groupValues[1].toInt(),
                m.groupValues[2], m.groupValues[3], m.groupValues[4],
            )
        }
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

    /**
     * resourceId KHUSUS episode (bukan id paket/series). Caption per episode
     * hanya tersedia di resource id ini; memakai id paket membuat subtitle EP1
     * dipakai untuk semua episode (tidak sinkron).
     */
    private suspend fun episodeResourceId(
        subjectId: String,
        token: String,
        se: Int,
        ep: Int,
    ): String? {
        val ck = "$subjectId|$se|$ep"
        episodeRidCache[ck]?.let { (t, v) ->
            if (System.currentTimeMillis() - t < 10 * 60_000L) return v
        }
        val found = episodeResourceIdUncached(subjectId, token, se, ep)
        if (found != null) episodeRidCache[ck] = System.currentTimeMillis() to found
        return found
    }

    private suspend fun episodeResourceIdUncached(
        subjectId: String,
        token: String,
        se: Int,
        ep: Int,
    ): String? {
        val base = activeHostOrFirst()
        suspend fun fetchPage(page: Int): Pair<List<JSONObject>, Boolean> {
            val root = runCatching {
                callJson(
                    base,
                    "/wefeed-mobile-bff/subject-api/resource?subjectId=$subjectId" +
                        "&se=$se&ep=$ep&page=$page",
                    null,
                    token,
                    false,
                )
            }.getOrNull() ?: return emptyList<JSONObject>() to false
            val data = root.optJSONObject("data") ?: return emptyList<JSONObject>() to false
            val arr = data.optJSONArray("list") ?: return emptyList<JSONObject>() to false
            val items = (0 until arr.length()).mapNotNull { arr.optJSONObject(it) }
            val hasMore = data.optJSONObject("pager")?.optBoolean("hasMore", false) ?: false
            return items to hasMore
        }
        fun pick(items: List<JSONObject>): String? = items.firstOrNull { it ->
            it.optInt("se", se) == se && it.optInt("ep", ep) == ep &&
                it.optString("resourceId").isNotBlank()
        }?.optString("resourceId")?.takeIf { it.isNotBlank() }

        // Halaman yang paling mungkin lebih dulu (10 item/halaman; EP1-9 di hal.1).
        val guessed = if (ep <= 9) 1 else (ep / 10 + 1)
        runCatching { fetchPage(guessed) }.getOrNull()?.let { (items, _) ->
            pick(items)?.let { return it }
        }
        var page = 1
        while (page <= 40) {
            if (page != guessed) {
                val (items, hasMore) = runCatching { fetchPage(page) }
                    .getOrDefault(emptyList<JSONObject>() to false)
                pick(items)?.let { return it }
                if (!hasMore && page > 1) break
            }
            page++
        }
        return null
    }

    // Subtitle eksternal: /subject-api/get-ext-captions (pola APK: subjectId + resourceId [+ se/ep] -> data.extCaptions).
    // Fallback: bila caption kosong, cari versi judul sama (tipe sama) yang punya caption Indonesia.
    suspend fun fetchSubtitles(subjectId: String, se: Int = 1, ep: Int = 1): List<SubtitleTrack> =
        withContext(Dispatchers.IO) {
            try {
                val token = boot() ?: return@withContext emptyList()
                val base = activeHostOrFirst()
                val detailRoot = fetchSubjectCached(base, subjectId, token)
                val detailData = detailRoot?.optJSONObject("data")
                // rid WAJIB milik episode yang diputar. resourceDetectors adalah
                // id PAKET (subtitle EP1 untuk semua episode) -> tidak sinkron.
                val rdArr = detailData?.optJSONArray("resourceDetectors")
                val packRid = rdArr?.optJSONObject(0)?.optString("resourceId")
                    ?.takeIf { it.isNotBlank() }
                val rid = episodeResourceId(subjectId, token, se, ep)
                    ?: packRid

                // HANYA sumber langsung (subjek + resourceId milik video ini).
                val caps = extCaptionsFor(subjectId, rid, token, se, ep)
                val out = captionsToTracks(caps)
                Log.i(
                    TAG,
                    "fetchSubtitles caps=${caps?.length() ?: 0} out=${out.size} subj=$subjectId " +
                        "se=$se ep=$ep rid=$rid packRid=$packRid direct",
                )
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
                        val origRid = episodeResourceId(origId, token, se, ep)
                            ?: subjectDetailDataForRid(origId, ep) ?: rid
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
            server = "Utama",
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
    // Cache singkat agar fetchStreams & fetchSubtitles tidak memanggil detail
    // subjek dua kali untuk episode yang sama.
    private val subjectDetailCache =
        java.util.concurrent.ConcurrentHashMap<String, Pair<Long, JSONObject>>()

    private suspend fun fetchSubjectCached(base: String, id: String, token: String): JSONObject? {
        val now = System.currentTimeMillis()
        subjectDetailCache[id]?.let { (t, j) ->
            if (now - t < 90_000) return j
        }
        val root = runCatching { fetchSubject(base, id, token) }.getOrNull() ?: return null
        subjectDetailCache[id] = now to root
        return root
    }

    private val episodeRidCache =
        java.util.concurrent.ConcurrentHashMap<String, Pair<Long, String>>()

    private fun h5Headers(): Map<String, String> {
        val ts = System.currentTimeMillis() / 1000
        val clientToken = "$ts," + md5Hex(ts.toString().reversed().toByteArray(Charsets.UTF_8))
        val info = JSONObject().put("timezone", java.util.TimeZone.getDefault().id)
        return linkedMapOf(
            "Accept" to "application/json",
            "X-Client-Info" to info.toString(),
            "X-Request-Lang" to "en",
            "X-Client-Token" to clientToken,
            "X-Vip-Restrict" to "1",
            "X-No-High-Risk-Restrict" to "0",
            "X-Source" to "",
            "Origin" to "https://movie-box.co",
            "Referer" to "https://movie-box.co/",
            "User-Agent" to "Mozilla/5.0 (Linux; Android 12) AppleWebKit/537.36",
        )
    }

    private fun callH5Json(pathAndQuery: String): JSONObject? = runCatching {
        val builder = Request.Builder().url(H5_BASE + pathAndQuery).get()
        h5Headers().forEach { (k, v) -> builder.header(k, v) }
        http.newCall(builder.build()).execute().use { resp ->
            val body = resp.body?.string().orEmpty()
            if (!resp.isSuccessful) {
                Log.w(TAG, "H5 HTTP ${resp.code} $pathAndQuery")
                null
            } else if (body.isBlank()) {
                null
            } else {
                JSONObject(body)
            }
        }
    }.getOrNull()

    /**
     * Short TV (subjectType 7) memakai post UGC di H5, bukan play-info.
     * play-info hanya mengembalikan trailer macdn; post UGC berisi MP4 asli
     * (bcdn.hakunaymatata.com/dramabox/...). Post biasanya hanya tersedia
     * untuk episode yang benar-benar diunggah, jadi cocokkan dulu lewat
     * Content-Disposition (contoh: "...-S1E2-480P.mp4") agar episode lain
     * tidak memutar video episode yang salah.
     */
    private fun h5VideoMatchesEpisode(url: String, se: Int, ep: Int): Boolean {
        val disposition = runCatching {
            http.newCall(
                Request.Builder().url(url)
                    .head()
                    .header("User-Agent", "ExoPlayerLib/2.19.1")
                    .build()
            ).execute().use { resp ->
                resp.header("Content-Disposition") ?: resp.header("content-disposition") ?: ""
            }
        }.getOrDefault("")
        if (disposition.isBlank()) return ep <= 1
        val match = Regex("(?i)S(\\d+)E(\\d+)").find(disposition) ?: return ep <= 1
        val s = match.groupValues[1].toIntOrNull() ?: return ep <= 1
        val e = match.groupValues[2].toIntOrNull() ?: return ep <= 1
        return s == se && e == ep
    }

    private fun h5Quality(video: JSONObject): String = when (video.optInt("definition", 0)) {
        3 -> "480"
        5 -> "720"
        7 -> "1080"
        else -> video.optInt("height", 0).takeIf { it > 0 }?.toString() ?: "Auto"
    }

    private fun fetchH5ShortTvStreams(subjectId: String, se: Int, ep: Int): List<MediaStream> {
        val result = ArrayList<MediaStream>()
        val seenQuality = HashSet<String>()
        var page = 0
        while (page < 5) {
            val root = callH5Json(
                "/wefeed-h5-bff/post/list/subject?id=$subjectId&page=$page&perPage=12"
            ) ?: break
            val items = root.optJSONObject("data")?.optJSONArray("items") ?: break
            if (items.length() == 0) break
            for (i in 0 until items.length()) {
                val post = items.optJSONObject(i) ?: continue
                val videos = post.optJSONObject("media")?.optJSONArray("video") ?: continue
                if (videos.length() == 0) continue
                val firstUrl = videos.optJSONObject(0)?.optString("url").orEmpty()
                if (firstUrl.isBlank() || isTrapUrl(firstUrl)) continue
                if (!h5VideoMatchesEpisode(firstUrl, se, ep)) continue
                for (j in 0 until videos.length()) {
                    val video = videos.optJSONObject(j) ?: continue
                    val url = video.optString("url").takeIf { it.isNotBlank() && !isTrapUrl(it) }
                        ?: continue
                    val quality = h5Quality(video)
                    if (!seenQuality.add(quality)) continue
                    result.add(
                        MediaStream(
                            server = "Moviebox UGC",
                            format = "mp4",
                            quality = quality,
                            url = url,
                            headers = mapOf("User-Agent" to "ExoPlayerLib/2.19.1"),
                        )
                    )
                }
            }
            page++
        }
        return result.sortedByDescending {
            it.quality?.filter(Char::isDigit)?.toIntOrNull() ?: 0
        }
    }

    suspend fun fetchStreams(subjectId: String, se: Int = 1, ep: Int = 1): List<MediaStream>? =
        withContext(Dispatchers.IO) {
            val token = boot() ?: return@withContext null
            val base = activeHostOrFirst()
            val detailRoot = fetchSubjectCached(base, subjectId, token)
            val detailData = detailRoot?.optJSONObject("data")
            // resourceId WAJIB per-episode (index = ep-1), bukan selalu index 0 —
            // sebelumnya semua episode memutar konten episode 1.
            val rdArr = detailData?.optJSONArray("resourceDetectors")
            val lastIdx = (rdArr?.length() ?: 1).coerceAtLeast(1) - 1
            val resourceId = rdArr?.optJSONObject((ep - 1).coerceIn(0, lastIdx))
                ?.optString("resourceId")?.takeIf { it.isNotBlank() }
                ?: rdArr?.optJSONObject(0)?.optString("resourceId")?.takeIf { it.isNotBlank() }

            // Short TV: sumber asli ada di post UGC H5, bukan play-info (trailer).
            if (detailData?.optInt("subjectType", 0) == 7) {
                val h5Streams = fetchH5ShortTvStreams(
                    subjectId,
                    if (se > 0) se else 1,
                    if (ep > 0) ep else 1,
                )
                if (h5Streams.isNotEmpty()) {
                    Log.i(TAG, "UGC H5 streams=${h5Streams.size} subj=${subjectId.takeLast(6)} se=$se ep=$ep")
                    return@withContext h5Streams
                }
                Log.w(TAG, "UGC H5 empty subj=${subjectId.takeLast(6)} se=$se ep=$ep")
            }

            // Ketiga varian play-info DIPANGGIL PARALEL: varian pertama yang
            // mengembalikan stream valid langsung dipakai. Dulu berurutan
            // (3x round-trip bila varian awal gagal) sehingga pemutar lama
            // menunggu di spinner "Mencari sumber".
            val variants = listOf<suspend () -> JSONObject?>(
                // rid unik per episode: panggil play-info dengan rid dulu (SANS se/ep)
                // supaya server MENGHORMATI episode yang dipilih (Sebelumnya varian
                // se/ep+rid bisa diabaikan server lalu jatuh ke {1,1} -> EP1 diputar).
                { fetchPlayInfo(base, subjectId, 0, 0, resourceId, token) },
                { fetchPlayInfo(base, subjectId, if (se > 0) se else 1, if (ep > 0) ep else 1, resourceId, token) },
                { fetchPlayInfo(base, subjectId, if (se > 0) se else 1, if (ep > 0) ep else 1, null, token) },
            )
            return@withContext run {
                // Fallback "real streams" dijalankan SEKALI walau beberapa varian
                // paralel sama-sama gagal/trap (hindari 3x request yang sama).
                val realMutex = Mutex()
                var realCache: List<MediaStream>? = null
                suspend fun realStreamsOnce(): List<MediaStream> =
                    realMutex.withLock {
                        realCache ?: fetchRealStreams(
                            subjectId,
                            resourceId,
                            if (se > 0) se else 1,
                            if (ep > 0) ep else 1,
                        ).also { realCache = it }
                    }

                suspend fun runVariant(call: suspend () -> JSONObject?): List<MediaStream>? {
                    val root = runCatching { call() }.getOrNull() ?: return null
                    if (root.optInt("code", -1) != 0) {
                        Log.w(TAG, "play-info code=${root.optInt("code", -1)} msg=${root.optString("message", "")}")
                        return null
                    }
                    val data = root.optJSONObject("data") ?: return null
                    val streams = buildStreams(data)
                    Log.i(TAG, "play-info streams count=${streams.size} subj=${subjectId.takeLast(6)} se=$se ep=$ep")
                    val trap = streams.firstOrNull()?.url?.let { isTrapUrl(it) } == true
                    if (streams.isEmpty() || trap) {
                        val real = realStreamsOnce()
                        android.util.Log.w(
                            TAG,
                            "fetchRealStreams result=${real.size} empty=${streams.isEmpty()} trap=$trap subj=${subjectId.takeLast(6)}",
                        )
                        if (real.isNotEmpty()) return real
                        // Trap tanpa pengganti: jangan kembalikan iklan; kosongkan
                        // (hasil final) agar tidak memutar ad.
                        if (trap) return emptyList()
                        // Streams kosong & fallback kosong: varian lain.
                        return null
                    }
                    Log.i(TAG, "Moviebox Local stream count=${streams.size} subj=${subjectId.takeLast(6)}")
                    return streams
                }
                // Hasil pertama yang valid langsung dipakai tanpa menunggu varian
                // lain (CompletableDeferred selesai seketika; berbeda dengan
                // coroutineScope yang menunggu semua child).
                val done = CompletableDeferred<List<MediaStream>?>()
                val trapOnly = AtomicReference<List<MediaStream>?>(null)
                val remaining = AtomicInteger(variants.size)
                variants.forEach { call ->
                    fetchScope.launch {
                        val r = runCatching { runVariant(call) }.getOrNull()
                        if (r != null && r.isNotEmpty()) {
                            done.complete(r)
                        } else if (r != null) {
                            trapOnly.compareAndSet(null, r)
                        }
                        if (remaining.decrementAndGet() == 0 && !done.isCompleted) {
                            done.complete(trapOnly.get())
                        }
                    }
                }
                done.await()
            }
        }
}
