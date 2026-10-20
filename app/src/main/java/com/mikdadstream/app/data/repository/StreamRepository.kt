package com.mikdadstream.app.data.repository

import com.mikdadstream.app.data.local.HistoryDao
import com.mikdadstream.app.data.local.SearchHistoryDao
import com.mikdadstream.app.data.local.WatchlistDao
import com.mikdadstream.app.data.moviebox.LocalMovieBoxClient
import com.mikdadstream.app.data.moviebox.MovieboxResolver
import com.mikdadstream.app.data.remote.AccountData
import com.mikdadstream.app.data.remote.ApiError
import com.mikdadstream.app.data.remote.CategoriesData
import com.mikdadstream.app.data.remote.DetailData
import com.mikdadstream.app.data.remote.Envelope
import com.mikdadstream.app.data.remote.MediaItem
import com.mikdadstream.app.data.remote.MikdadApi
import com.mikdadstream.app.data.remote.ModelsEnvelope
import com.mikdadstream.app.data.remote.SearchBody
import com.mikdadstream.app.data.remote.SourceData
import com.mikdadstream.app.data.remote.SourceModel
import com.mikdadstream.app.data.remote.VideosData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import retrofit2.HttpException

data class ModelsResult(
    val models: List<SourceModel>,
    val searchAllMovie: Boolean,
)

class StreamRepository(
    private val api: MikdadApi,
    val watchlistDao: WatchlistDao,
    val historyDao: HistoryDao,
    val searchHistoryDao: SearchHistoryDao,
) {

    private var modelsCache: ModelsResult? = null
    // Cache hasil source per episode (5 menit) supaya kembali ke episode yang
    // sama / prefetch episode berikutnya langsung tampil tanpa request ulang.
    private val episodeSourceCache =
        java.util.concurrent.ConcurrentHashMap<String, Pair<Long, SourceData>>()
    private val nameIndex = mutableMapOf<String, String>()
    private val _modelsState = MutableStateFlow<ModelsResult?>(null)
    val modelsState: StateFlow<ModelsResult?> = _modelsState

    private fun mapError(e: Throwable): ApiError = when (e) {
        is ApiError -> e
        is HttpException -> when (e.code()) {
            401 -> ApiError("Sesi berakhir. Silakan coba lagi.", 401, authRequired = true)
            404 -> ApiError("Konten tidak ditemukan.", 404)
            in 500..599 -> ApiError("Server sedang bermasalah. Coba lagi nanti.", e.code())
            else -> ApiError("Permintaan gagal (${e.code()}).", e.code(), e.code() == 401)
        }
        else -> ApiError(
            e.message?.takeIf { it.isNotBlank() } ?: "Tidak bisa terhubung ke server. Periksa internet Anda.",
        )
    }

    private suspend fun <T> call(block: suspend () -> T): T = try {
        withContext(Dispatchers.IO) { block() }
    } catch (e: Exception) {
        throw mapError(e)
    }

    private fun <T> unwrap(envelope: Envelope<T>?, fallbackMessage: String): T {
        if (envelope == null || envelope.status == "error" || envelope.code?.let { it !in 200..299 } == true) {
            val message = envelope?.message?.takeIf { it.isNotBlank() }?.let(::friendlyMessage) ?: fallbackMessage
            throw ApiError(message, envelope?.code ?: 0)
        }
        return envelope.data ?: throw ApiError(fallbackMessage, envelope.code ?: 0)
    }

    private fun friendlyMessage(raw: String): String = when (raw) {
        "access_token_required" -> "Sesi berakhir. Masuk ulang untuk melanjutkan."
        "invalid_refresh_token" -> "Sesi kedaluwarsa. Masuk ulang untuk melanjutkan."
        else -> raw
    }

    // ---------- Sumber video (models) ----------

    suspend fun models(force: Boolean = false): ModelsResult {
        modelsCache?.let { if (!force) return it }
        val envelope: ModelsEnvelope = call { api.models() }
        if (envelope.status == "error" || envelope.data == null) {
            throw ApiError("Gagal memuat daftar sumber video.")
        }
        val baseModels = envelope.data.orEmpty().filter { !it.safeId.isBlank() }
        val localModel = SourceModel(
            id = "moviebox-local",
            name = "Moviebox Local",
            status = "active",
            contentType = "movie_tv",
            description = "Sumber video Moviebox via client lokal aplikasi",
        )
        val models = baseModels + localModel
        val result = ModelsResult(models, envelope.searchAllMovie ?: true)
        nameIndex.clear()
        result.models.forEach { model ->
            nameIndex[normalize(model.safeName)] = model.safeId
            nameIndex[normalize(model.safeId)] = model.safeId
        }
        nameIndex[normalize("Moviebox Local")] = "moviebox-local"
        nameIndex[normalize("movieboxlocal")] = "moviebox-local"
        modelsCache = result
        _modelsState.value = result
        return result
    }

    private fun normalize(value: String): String =
        value.lowercase().replace(Regex("[^a-z0-9]"), "")

    /** Petakan source sintetis lokal ke id Moviebox untuk call API katalog. */
    private fun apiModelId(modelId: String): String =
        if (modelId.equals("moviebox-local", true)) "moviebox" else modelId

    /** Petakan nama sumber (dari hasil pencarian) ke model_id. */
    fun modelIdForSourceName(sourceName: String?): String? {
        if (sourceName.isNullOrBlank()) return null
        val key = normalize(sourceName)
        nameIndex[key]?.let { return it }
        nameIndex.entries.firstOrNull { key.startsWith(it.key) || it.key.startsWith(key) }?.let { return it.value }
        return null
    }

    // ---------- Kategori & daftar video ----------

    suspend fun categories(modelId: String): List<CategoryBundle> {
        if (modelId.equals("moviebox-local", true)) {
            return listOf(
                CategoryBundle("trending", "Trending"),
                CategoryBundle("indonesia", "Indonesia"),
                CategoryBundle("livetv", "Live TV"),
                CategoryBundle("livesport", "Live Sport"),
                CategoryBundle("kdrama", "K-Drama"),
                CategoryBundle("indodrama", "Indo Drama"),
                CategoryBundle("cdrama", "C-Drama"),
                CategoryBundle("anime", "Anime"),
                CategoryBundle("thaidrama", "Thai-Drama"),
                CategoryBundle("western", "Western"),
                CategoryBundle("reality", "Reality"),
                CategoryBundle("jdrama", "J-Drama"),
                CategoryBundle("malaydrama", "Malay Drama"),
                CategoryBundle("romance", "Romance"),
                CategoryBundle("action", "Action"),
                CategoryBundle("mystery", "Mystery"),
            )
        }
        val envelope: Envelope<CategoriesData> = call { api.categories(apiModelId(modelId)) }
        val data = unwrap(envelope, "Gagal memuat kategori.")
        return data.data.orEmpty()
            .filter { !it.safeId.isBlank() }
            .map { CategoryBundle(it.safeId, it.safeName) }
    }

    suspend fun videos(modelId: String, categoryId: String, page: Int = 1): VideosData {
        if (modelId.equals("moviebox-local", true)) {
            val items = runCatching { LocalMovieBoxClient.categoryItems(categoryId) }
                .getOrElse { emptyList() }
                .ifEmpty { runCatching { LocalMovieBoxClient.trendingItems() }.getOrDefault(emptyList()) }
            android.util.Log.i("MovieboxLocal", "videos cat=$categoryId items=${items.size}")
            return VideosData(
                type = "movie_tv",
                moduleName = "Moviebox Local",
                items = items,
                page = page,
                hasMore = false,
            )
        }
        return unwrap(call { api.videos(apiModelId(modelId), categoryId, page) }, "Gagal memuat daftar video.")
    }

    suspend fun search(query: String, page: Int = 1): List<MediaItem> {
        val trimmed = query.trim()
        if (trimmed.isBlank()) return emptyList()
        val envelope = call {
            api.search(SearchBody(query = trimmed, q = trimmed, page = page))
        }
        return unwrap(envelope, "Pencarian gagal.").items.orEmpty()
    }

    /** Pencarian untuk source tertentu; Moviebox Local memakai client ported. */
    suspend fun search(modelId: String?, query: String, page: Int = 1): List<MediaItem> {
        if (modelId?.equals("moviebox-local", true) == true) {
            return LocalMovieBoxClient.searchItems(query.trim(), page)
        }
        return search(query, page)
    }

    // ---------- Detail ----------

    suspend fun detail(modelId: String, id: String): DetailData {
        if (modelId.equals("moviebox-local", true)) {
            val d = LocalMovieBoxClient.subjectDetailData(id)
            android.util.Log.i("MovieboxLocal", "detail id=$id episodes=${d?.episodeList?.size}")
            return d ?: throw ApiError("Detail Moviebox Local tidak ditemukan.")
        }
        return unwrap(call { api.detail(apiModelId(modelId), id) }, "Detail tidak ditemukan.")
    }

    /**
     * Detail dengan pemetaan model otomatis: bila modelId tidak diketahui
     * (mis. hasil pencarian lintas sumber), coba kandidat berdasarkan nama
     * sumber lalu probing ke model aktif lainnya.
     */
    suspend fun detailResolve(id: String, modelId: String?, sourceName: String?): DetailData {
        val candidates = linkedSetOf<String>()
        modelId?.takeIf { it.isNotBlank() }?.let(candidates::add)
        modelIdForSourceName(sourceName)?.let(candidates::add)
        models().models.filter { it.isActive }.forEach { candidates.add(it.safeId) }

        var lastError: Exception? = null
        for (candidate in candidates) {
            try {
                return detail(candidate, id)
            } catch (e: ApiError) {
                if (e.code == 401) throw e
                lastError = e
            }
        }
        throw lastError ?: ApiError("Konten tidak ditemukan di semua sumber.")
    }

    // ---------- Sumber pemutaran ----------

    suspend fun source(modelId: String, episodeId: String, id: String): SourceData {
        if (modelId.equals("moviebox-local", true)) {
            val cacheKey = "$id|$episodeId"
            episodeSourceCache[cacheKey]?.let { (t, cached) ->
                if (System.currentTimeMillis() - t < 5 * 60_000L) {
                    android.util.Log.i("MovieboxLocal", "source cache-hit id=$id ep=$episodeId")
                    return cached
                }
            }
            val parts = episodeId.split("_")
            val se = parts.getOrNull(0)?.toIntOrNull() ?: 1
            val ep = parts.getOrNull(1)?.toIntOrNull() ?: 1
            // Stream & subtitle diambil PARALEL: sebelumnya berurutan sehingga
            // klik episode menunggu dua rangkaian request.
            return coroutineScope {
                val streamsAsync = async {
                    LocalMovieBoxClient.fetchStreams(id, se, ep) ?: emptyList()
                }
                val subsAsync = async {
                    runCatching { LocalMovieBoxClient.fetchSubtitles(id, se, ep) }
                        .getOrDefault(emptyList())
                }
                val streams = streamsAsync.await()
                val subtitles = subsAsync.await()
                android.util.Log.i(
                    "MovieboxLocal",
                    "source streams=${streams.size} subs=${subtitles.size} id=$id ep=$episodeId se=$se epNum=$ep",
                )
                val result = SourceData(streams = streams, subtitles = subtitles)
                if (streams.isNotEmpty()) {
                    episodeSourceCache[cacheKey] = System.currentTimeMillis() to result
                }
                result
            }
        }
        val data = unwrap(call { api.source(apiModelId(modelId), episodeId, id) }, "Sumber video tidak tersedia.")
        val refreshed = MovieboxResolver.maybeRefresh(data, id)
        return if (refreshed.playableStreams.isEmpty() && data.playableStreams.isNotEmpty()) {
            // Hindari memutar iklan/trap jika refresh lokal gagal.
            data.copy(streams = emptyList())
        } else refreshed
    }

    suspend fun download(modelId: String, episodeId: String, id: String): SourceData =
        unwrap(call { api.download(apiModelId(modelId), episodeId, id) }, "Tautan unduhan tidak tersedia.")

    // ---------- Akun ----------

    suspend fun accountStatus(): AccountData =
        unwrap(call { api.accountStatus() }, "Gagal memuat status akun.")
}

data class CategoryBundle(val id: String, val name: String)
