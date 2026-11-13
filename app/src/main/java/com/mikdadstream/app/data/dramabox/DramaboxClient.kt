package com.mikdadstream.app.data.dramabox

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.Signature
import java.security.spec.PKCS8EncodedKeySpec
import java.util.Base64
import java.util.Locale

/**
 * Klien tak resmi untuk API DramaBox (short TV vertikal, termasuk dub Indo) —
 * port dari SDK unofficial `@zhadev/dramabox`, sejajar dengan klien MovieBox
 * yang sudah ada di aplikasi. HANYA konten gratis yang dikembalikan:
 * episode VIP (`isCharge == 1`) ditandai terkunci, bukan dibuka paksa.
 *
 * Tidak ada endpoint tersembunyi yang dipakai untuk menembus paywall;
 * kunci tanda tangan APP (bukan akun pengguna) sama seperti SDK aslinya.
 *
 * CATATAN: API ini WAF-protected (Akamai). Bila HTTP 403 dari jaringan
 * pengguna, sumber ini gagal dan hanya menampilkan pesan kesalahan.
 */
object DramaboxClient {
    private const val TAG = "Dramabox"
    private const val BASE = "https://sapi.dramaboxdb.com"
    private const val VERSION = "490"
    private const val UA = "okhttp/4.10.0"

    private val http: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(12, java.util.concurrent.TimeUnit.SECONDS)
        .readTimeout(20, java.util.concurrent.TimeUnit.SECONDS)
        .writeTimeout(20, java.util.concurrent.TimeUnit.SECONDS)
        .build()

    // Stabil per sesi aplikasi (mirip SDK yang generate 1x per sesi).
    private val deviceId: String = java.util.UUID.randomUUID().toString()
    private val androidId: String = "00000000" +
        java.security.MessageDigest.getInstance("MD5")
            .digest("android-id".toByteArray())
            .joinToString("") { "%02x".format(it.toInt() and 0xff) }
            .take(16) + "00000000"
    private val instanceId: String = java.security.MessageDigest.getInstance("MD5")
        .digest(deviceId.toByteArray()).joinToString("") { "%02x".format(it.toInt() and 0xff) }
    private val afid: String = "${System.currentTimeMillis()}-${(kotlin.random.Random.nextLong(0, 1_000_000_000_000_000L))}"
    private val ins: String = System.currentTimeMillis().toString()

    private data class Token(val value: String, val deviceId: String, val androidId: String, val createdAt: Long) {
        val expiry: Long get() = createdAt + 24 * 60 * 60 * 1000L
    }

    @Volatile
    private var cachedToken: Token? = null

    // ---------- Kunci privat (signature DramaBox) ----------

    private val privateKey: PrivateKey by lazy {
        val pem = Text.PEM_BASE64
        val der = Base64.getDecoder().decode(pem)
        KeyFactory.getInstance("RSA").generatePrivate(PKCS8EncodedKeySpec(der))
    }

    private fun sign(input: String): String {
        val sig = Signature.getInstance("SHA256withRSA")
        sig.initSign(privateKey)
        sig.update(input.toByteArray(Charsets.UTF_8))
        return Base64.getEncoder().encodeToString(sig.sign())
    }

    private fun localTime(): String {
        val fmt = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS '+0700'", Locale.US)
        fmt.timeZone = java.util.TimeZone.getTimeZone("GMT+7")
        return fmt.format(java.util.Date())
    }

    private fun baseHeaders(tn: String?): MutableMap<String, String> {
        val headers = mutableMapOf(
            "accept-encoding" to "gzip",
            "active-time" to kotlin.random.Random.nextInt(0, 60_000).toString(),
            "afid" to afid,
            "android-id" to androidId,
            "apn" to "0",
            "brand" to "Xiaomi",
            "build" to "Build/QQ3A.200805.001",
            "cid" to "DAUAG1064236",
            "content-type" to "application/json; charset=UTF-8",
            "country-code" to "ID",
            "current-language" to "in",
            "device-id" to deviceId,
            "device-score" to "55",
            "host" to "sapi.dramaboxdb.com",
            "ins" to ins,
            "instanceid" to instanceId,
            "is_emulator" to "0",
            "is_root" to "1",
            "is_vpn" to "1",
            "language" to "in",
            "lat" to "0",
            "local-time" to localTime(),
            "locale" to "in_ID",
            "mbid" to "60000000000",
            "mcc" to "510",
            "mchid" to "DAUAG1050238",
            "md" to "V2309A",
            "mf" to "VIVO",
            "nchid" to "DRA1000000",
            "ov" to "9",
            "over-flow" to "new-fly",
            "p" to "51",
            "package-name" to "com.storymatrix.drama",
            "pline" to "ANDROID",
            "srn" to "900x1600",
            "store-source" to "store_google",
            "time-zone" to "+0800",
            "tn" to (tn ?: ""),
            "tz" to "-480",
            "user-agent" to UA,
            "userid" to "359146421",
            "version" to VERSION,
            "vn" to "4.9.0",
        )
        return headers
    }

    private fun bootstrapHeaders(body: String, timestamp: Long): Map<String, String> {
        val payload = "timestamp=$timestamp$body$deviceId$androidId"
        val sn = runCatching { sign(payload) }.getOrElse {
            Log.w(TAG, "sign gagal: ${it.message}")
            return emptyMap()
        }
        val h = baseHeaders(null)
        h["sn"] = sn
        return h
    }

    private fun sapiHeaders(tnValue: String, body: String, deviceId: String, androidId: String, timestamp: Long): Map<String, String> {
        val payload = "timestamp=$timestamp$body${deviceId}${androidId}$tnValue"
        val sn = runCatching { sign(payload) }.getOrElse {
            Log.w(TAG, "sign gagal: ${it.message}")
            return emptyMap()
        }
        val h = baseHeaders(tnValue)
        h["sn"] = sn
        return h
    }

    private fun callOnce(base: String, pathAndQuery: String, headers: Map<String, String>, body: String?): JSONObject? =
        runCatching {
            val builder = Request.Builder().url(base + pathAndQuery)
            headers.forEach { (k, v) -> builder.header(k, v) }
            if (body != null) builder.post(body.toRequestBody("application/json;charset=UTF-8".toMediaType()))
            http.newCall(builder.build()).execute().use { resp ->
                val text = resp.body?.string().orEmpty()
                if (resp.code != 200) {
                    Log.w(TAG, "HTTP ${resp.code} $pathAndQuery: ${text.take(220)}")
                    return null
                }
                if (text.isBlank()) null else JSONObject(text)
            }
        }.getOrElse { Log.w(TAG, "call gagal $pathAndQuery: ${it.javaClass.simpleName}: ${it.message}"); null }

    private suspend fun generateToken(): Token? = withContext(Dispatchers.IO) {
        val ts = System.currentTimeMillis()
        val body = """{"distinctId":null}"""
        val headers = bootstrapHeaders(body, ts)
        if (headers.isEmpty()) return@withContext null
        val root = callOnce(BASE, "/drama-box/ap001/bootstrap?timestamp=$ts", headers, body)
        val user = root?.optJSONObject("data")?.optJSONObject("user")
        val token = user?.optString("token")?.takeIf { it.isNotBlank() }
        if (token == null) {
            Log.w(TAG, "bootstrap: token tidak ada di response (code=${root?.optInt("code")} msg=${root?.optString("message")})")
            return@withContext null
        }
        Token(token, deviceId, androidId, System.currentTimeMillis()).also { cachedToken = it }
    }

    /** Token valid (cache 24 jam). */
    private suspend fun token(): Token? {
        cachedToken?.let { if (it.expiry - System.currentTimeMillis() > 5 * 60_000L) return it }
        return generateToken()
    }

    private suspend fun sapi(endpoint: String, payload: JSONObject, attempt: Int = 0): JSONObject? =
        withContext(Dispatchers.IO) {
            val tok = token() ?: return@withContext null
            val body = payload.toString()
            val ts = System.currentTimeMillis()
            val hold = "Bearer ${tok.value}"
            val headers = sapiHeaders(hold, body, tok.deviceId, tok.androidId, ts)
            if (headers.isEmpty()) return@withContext null
            val root = callOnce(BASE, "$endpoint?timestamp=$ts", headers, body)
            if (root == null) return@withContext null
            val success = root.optBoolean("success", true)
            val code = root.optInt("code", 0)
            val msg = root.optString("message", "")
            // 401 / hapus token & coba sekali lagi (seperti SDK).
            if (!success && (code == 401 || msg.contains("token", true)) && attempt == 0) {
                cachedToken = null
                return@withContext sapi(endpoint, payload, 1)
            }
            if (!success) {
                Log.w(TAG, "sapi $endpoint success=false code=$code msg=$msg")
                return@withContext null
            }
            root
        }

    // ---------- Model data ----------

    data class DramaboxBook(
        val bookId: String,
        val bookName: String,
        val cover: String?,
        val introduction: String?,
        val chapterCount: Int,
        val tagV3s: List<String>,
        val playCount: Long,
    )

    private fun mapBook(o: JSONObject?): DramaboxBook? {
        if (o == null) return null
        val id = o.optString("bookId").ifBlank { return null }
        val name = o.optString("bookName").ifBlank { return null }
        val tags = o.optJSONArray("tagV3s")?.let { a ->
            (0 until a.length()).mapNotNull { a.optString(it).takeIf { s -> s.isNotBlank() } }
        }.orEmpty()
        return DramaboxBook(
            bookId = id,
            bookName = name,
            cover = o.optString("coverWap").takeIf { it.isNotBlank() }
                ?: o.optString("cover").takeIf { it.isNotBlank() },
            introduction = o.optString("introduction").takeIf { it.isNotBlank() },
            chapterCount = o.optInt("chapterCount", 0),
            tagV3s = tags,
            playCount = o.optLong("playCount", 0L),
        )
    }

    /** Daftar drama dub Indo (DramaBox classify, tampilan Dub Indo di app). */
    suspend fun dubIndoList(pageNo: Int = 1, pageSize: Int = 30): Pair<List<DramaboxBook>, Boolean>? {
        val payload = JSONObject().apply {
            put("typeList", org.json.JSONArray().apply {
                put(JSONObject().put("type", 1).put("value", ""))
                put(JSONObject().put("type", 2).put("value", "1"))
                put(JSONObject().put("type", 3).put("value", ""))
                put(JSONObject().put("type", 4).put("value", ""))
                put(JSONObject().put("type", 4).put("value", ""))
                put(JSONObject().put("type", 5).put("value", "1"))
            })
            put("showLabels", false)
            put("pageNo", pageNo.toString())
            put("pageSize", pageSize.toString())
        }
        val root = sapi("/drama-box/he001/classify", payload) ?: return null
        val list = root.optJSONObject("data")?.optJSONObject("classifyBookList") ?: return emptyList<DramaboxBook>() to false
        val records = list.optJSONArray("records") ?: return emptyList<DramaboxBook>() to false
        val books = (0 until records.length()).mapNotNull { mapBook(records.optJSONObject(it)) }
        return books to (list.optInt("isMore", 0) == 1)
    }

    /** Pencarian drama (kata kunci apa pun). */
    suspend fun searchList(keyword: String, pageNo: Int = 1, pageSize: Int = 20): List<DramaboxBook>? {
        val payload = JSONObject().apply {
            put("searchSource", "搜索按钮")
            put("pageNo", pageNo)
            put("pageSize", pageSize)
            put("from", "search_sug")
            put("keyword", keyword)
        }
        val root = sapi("/drama-box/search/search", payload) ?: return null
        val raw = root.optJSONObject("data")?.optJSONArray("searchList") ?: return emptyList()
        return (0 until raw.length()).mapNotNull { mapBook(raw.optJSONObject(it)) }
    }

    /** Trending terbaru. */
    suspend fun trendingList(pageNo: Int = 1): List<DramaboxBook>? {
        val payload = JSONObject().apply {
            put("newChannelStyle", 1)
            put("isNeedRank", 1)
            put("pageNo", pageNo)
            put("index", 0)
            put("channelId", 175)
        }
        val root = sapi("/drama-box/he001/theater", payload) ?: return null
        val columns = root.optJSONObject("data")?.optJSONArray("columnVoList")
            ?: return emptyList()
        val out = LinkedHashMap<String, DramaboxBook>()
        for (i in 0 until columns.length()) {
            val books = columns.optJSONObject(i)?.optJSONArray("bookList") ?: continue
            for (j in 0 until books.length()) {
                val b = mapBook(books.optJSONObject(j)) ?: continue
                if (!out.containsKey(b.bookId)) out[b.bookId] = b
            }
        }
        return out.values.toList()
    }

    /** Detail (buku) satu drama. */
    suspend fun detail(bookId: String): DramaboxBook? {
        val payload = JSONObject().apply {
            put("needRecommend", true)
            put("from", "book_album")
            put("bookId", bookId)
        }
        val root = sapi("/drama-box/chapterv2/detail", payload) ?: return null
        return mapBook(root.optJSONObject("data")?.optJSONObject("book"))
    }

    /** Satu chapter (episode) dalam format mentah API. */
    data class DramaboxChapter(
        val chapterId: String,
        val chapterIndex: Int,
        val chapterName: String,
        val duration: Int,
        val cover: String?,
        val isCharge: Boolean,
        val videoPath: String?,
        val videoQuality: Int?,
    )

    /** Daftar chapter + jumlah total (urut chapterIndex). */
    suspend fun chapters(bookId: String): Pair<List<DramaboxChapter>, Int>? {
        val payload = JSONObject().apply {
            put("boundaryIndex", 0)
            put("comingPlaySectionId", -1)
            put("index", 1)
            put("currencyPlaySource", "discover_new_rec_new")
            put("needEndRecommend", 0)
            put("currencyPlaySourceName", "")
            put("preLoad", false)
            put("rid", "")
            put("pullCid", "")
            put("loadDirection", 0)
            put("bookId", bookId)
        }
        val root = sapi("/drama-box/chapterv2/batch/load", payload) ?: return null
        val data = root.optJSONObject("data") ?: return emptyList<DramaboxChapter>() to 0
        val total = data.optInt("chapterCount", 0)
        val list = data.optJSONArray("chapterList") ?: return emptyList<DramaboxChapter>() to total
        val out = ArrayList<DramaboxChapter>()
        for (i in 0 until list.length()) {
            val ch = list.optJSONObject(i) ?: continue
            val cdns = ch.optJSONArray("cdnList")
            var chosen: JSONObject? = null
            if (cdns != null && cdns.length() > 0) {
                val all = (0 until cdns.length()).mapNotNull { cdns.optJSONObject(it) }
                chosen = all.firstOrNull { it.optInt("isDefault", 0) == 1 } ?: all.firstOrNull()
            }
            val paths = chosen?.optJSONArray("videoPathList")
            var videoPath: String? = null
            var quality: Int? = null
            if (paths != null && paths.length() > 0) {
                val allPaths = (0 until paths.length()).mapNotNull { paths.optJSONObject(it) }
                val vp = allPaths.firstOrNull { it.optInt("isDefault", 0) == 1 } ?: allPaths.firstOrNull()
                videoPath = vp?.optString("videoPath")?.takeIf { it.isNotBlank() }
                quality = vp?.optInt("quality")?.takeIf { it > 0 }
            }
            val idx = ch.optInt("chapterIndex", 0)
            if (idx <= 0) continue
            out.add(
                DramaboxChapter(
                    chapterId = ch.optString("chapterId").ifBlank { idx.toString() },
                    chapterIndex = idx,
                    chapterName = ch.optString("chapterName").ifBlank { "Episode $idx" },
                    duration = ch.optInt("duration", 0),
                    cover = ch.optString("cover").takeIf { it.isNotBlank() },
                    isCharge = ch.optInt("isCharge", 0) == 1,
                    videoPath = videoPath,
                    videoQuality = quality,
                )
            )
        }
        out.sortBy { it.chapterIndex }
        return out to total
    }
}

/** PEM base64 halaman private key DramaBox (PKCS8, tanpa header). */
private object Text {
    const val PEM_BASE64: String =
        "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC9Q4Y5QX5j08HrnbY3irfKdkEllAU2OORnAjlXDyCzcm2Z6ZRrGvtTZUAMelfU5PWS6XGEm3d4kJEKbXi4Crl8o2E/E3YJPk1lQD1d0JTdrvZleETN1ViHZFSQwS3L94Woh0E3TPebaEYq88eExvKu1tDdjSoFjBbgMezySnas5Nc2xF28" +
        "XhPuC8m15u+dectsrJl+ALGcTDX3Lv3FURuwV/dN7WMEkgcseIKVMdJxzUB0PeSqCNftfxmdBV/U4yXFRxPhnSFSXCrkj6uJjickiYq1pQ1aZfrQe1eLD3MB2hKq7crhMcA3kpggQlnmy1wRR4BAttmSU4fPb/yF8D3hAgMBAAECggEBAJdru6p5RLZ3h/GLF2rud8bqv4piF51e/RWQyPFnMAGBrkByiYT7bFI3cnvJMhYpLHRigqjWfUofV3thRDDym54lVLtTRZ91khRMxgwVwdRuk8Fw7JNFenOwCJxbgdlq6iuAMuQclwll7qWUrm8DgMvzH93xf8o6X171cp4Sh0og1Ra7E9GZ37dzBlX2aJBK8VBfctZntuDP" +
        "x52e71nafqfbjXxZuEtpu92oJd6A9mWbd0BZTk72ZHUmDcKcqjfcEH19SWOphMJFYkxU5FRoIEr3/zisyTO4Mt33ZmwELOrY9PdlyAAyed7ZoH+hlTr7c025QROvb2LmqgRiUT56tMECgYEA+jH5m6iMRK6XjiBhSUnlr3DzRybwlQrtIj5sZprWe2my5uYHG3jbViYIO7GtQvMTnDrBCxNhuM6dPrL0cRnbsp/iBMXe3pyjT/aWveBkn4R+UpBsnbtDn28r1MZpCDtr5UNc0TPj4KFJvjnV/e8oGoyYEroECqcw1LqNOGDiLhkCgYEAwaemNePYrXW+MVX/hatfLQ96tpxwf7yuHdENZ2q5AFw73GJWYvC8VY+TcoKPAmeoCUMltI3TrS6K5Q/GoLd5K2BsoJrSxQNQFd3ehWAtdOuPDvQ5rn/2fsvgvc3rOvJh7uNnwEZCI/45WQg+UFWref4PPc+ArNtp9Xj2y7LndwkCgYARojIQeXmhYZjG6JtSugWZLuHGkwUDzChYcIPd" +
        "W25ndluokG/RzNvQn4+W/XfTryQjr7RpXm1VxCIrCBvYWNU2KrSYV4XUtL+B5ERNj6In6AOrOAifuVITy5cQQQeoD+AT4YKKMBkQfO2gnZzqb8+ox130e+3K/mufoqJPZeyrCQKBgC2fobjwhQvYwYY+DIUharri+rYrBRYTDbJYnh/PNOaw1CmHwXJt5PEDcml3+NlIMn58I1X2U/hpDrAIl3MlxpZBkVYFI8LmlOeR7ereTddN59ZOE4jY/OnCfqA480Jf+FKfoMHby5lPO5OOLaAfjtae1FhrmpUe3EfIx9wVuhKBAoGBAPFzHKQZbGhkqmyPW2ctTEIWLdUHyO37fm8dj1WjN4wjRAI4ohNiKQJRh3QE11E1PzBTl9lZVWT8QtEsSjnrA/tpGr378fcUT7WGBgTmBRaAnv1P1n/Tp0TSvh5XpIhhMuxcitIgrhYMIG3GbP9JNAarxO/qPW6Gi0xWaF7il7Or"
}
