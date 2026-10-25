package com.mikdadstream.app.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikdadstream.app.data.local.HistoryEntity
import com.mikdadstream.app.data.local.WatchlistEntity
import com.mikdadstream.app.data.remote.ApiError
import com.mikdadstream.app.data.remote.DetailData
import com.mikdadstream.app.data.remote.EpisodeData
import com.mikdadstream.app.data.remote.MediaStream
import com.mikdadstream.app.data.remote.SourceData
import com.mikdadstream.app.data.remote.SubtitleTrack
import com.mikdadstream.app.data.repository.StreamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit

data class DetailUiState(
    val loading: Boolean = true,
    val detail: DetailData? = null,
    val modelId: String? = null,
    val error: String? = null,
    val inWatchlist: Boolean = false,
    val history: HistoryEntity? = null,
    val downloadEpisode: EpisodeData? = null,
    val downloads: List<MediaStream> = emptyList(),
    val loadingDownloads: Boolean = false,
    val downloadError: String? = null,
    val downloadLocked: Boolean = false,
    /** Sumber episode yang sudah dipanen prefetch: dipakai pemutar seketika. */
    val sourceCache: Map<String, SourceData> = emptyMap(),
) {
    /** Sumber episode dari hasil prefetch (null bila belum ada). */
    fun cachedSourceFor(episodeId: String): SourceData? = sourceCache[episodeId]
}

/** Permintaan pemutaran yang diteruskan ke PlayerActivity. */
data class PlayRequest(
    val modelId: String,
    val contentId: String,
    val episodeId: String,
    val episodeNumber: Int,
    val title: String,
    val cover: String?,
    val episodes: List<EpisodeData>,
    val startPositionMs: Long = 0L,
    val localUrl: String? = null,
    val streams: List<MediaStream> = emptyList(),
    val subtitles: List<SubtitleTrack> = emptyList(),
)

class DetailViewModel(
    private val repo: StreamRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(DetailUiState())
    val state: StateFlow<DetailUiState> = _state

    private var contentId: String = ""
    private val prefetchAttempted = mutableSetOf<String>()
    private val prefetchSemaphoreLocal = Semaphore(3)
    private val prefetchSemaphoreApi = Semaphore(1)

    /** Simpan hasil prefetch ke cache UI agar pemutar memakainya seketika. */
    private fun rememberSource(episodeId: String, data: SourceData) {
        if (episodeId.isBlank() || data.playableStreams.isEmpty()) return
        _state.value = _state.value.copy(
            sourceCache = (_state.value.sourceCache + (episodeId to data)).let { map ->
                if (map.size <= 48) map else map.entries.sortedByDescending {
                    it.value.hashCode()
                }.take(48).associate { it.key to it.value }
            },
        )
    }

    /** Panaskan cache sumber episode yang terlihat agar klik langsung memutar. */
    fun prefetch(episode: EpisodeData) {
        val current = _state.value
        val detailId = current.detail?.safeId ?: return
        val modelId = current.modelId ?: return
        if (episode.safeId.isBlank() || episode.isLocked) return
        val key = "$detailId|${episode.safeId}"
        if (!prefetchAttempted.add(key)) return
        // Sudah ada di cache UI: tidak perlu diambil ulang.
        if (_state.value.sourceCache.containsKey(episode.safeId)) return
        val semaphore = if (modelId.equals("moviebox-local", true)) {
            prefetchSemaphoreLocal
        } else {
            prefetchSemaphoreApi
        }
        viewModelScope.launch {
            semaphore.withPermit {
                runCatching { repo.source(modelId, episode.safeId, detailId) }
                    .onSuccess { rememberSource(episode.safeId, it) }
            }
        }
    }

    /** Panaskan sumber beberapa episode di sekitar yang dipilih (window). */
    private fun prefetchWindow(around: EpisodeData) {
        val episodes = _state.value.detail?.episodeList.orEmpty()
        if (episodes.isEmpty()) return
        val idx = episodes.indexOfFirst { it.safeId == around.safeId }
        if (idx < 0) return
        listOf(idx + 1, idx + 2, idx - 1)
            .mapNotNull { episodes.getOrNull(it) }
            .forEach { prefetch(it) }
    }

    fun load(id: String, modelId: String?, sourceName: String?) {
        if (id.isBlank()) {
            _state.value = DetailUiState(loading = false, error = "Konten tidak valid.")
            return
        }
        if (contentId == id && _state.value.detail != null) return
        contentId = id
        _state.value = DetailUiState(loading = true)
        viewModelScope.launch {
            try {
                val detail = repo.detailResolve(id = id, modelId = modelId, sourceName = sourceName)
                val resolvedModel = detail.safeModelId.ifBlank { modelId.orEmpty() }
                val history = repo.historyDao.find(id)
                val key = watchlistKey(resolvedModel, id)
                val inList = repo.watchlistDao.find(key) != null
                _state.value = DetailUiState(
                    loading = false,
                    detail = detail,
                    modelId = resolvedModel.ifBlank { null },
                    inWatchlist = inList,
                    history = history,
                )
                val resume = history?.episodeId?.let { episodeId ->
                    detail.episodeList.firstOrNull { it.safeId == episodeId }
                } ?: detail.episodeList.firstOrNull()
                if (resume != null) {
                    prefetch(resume)
                    prefetchWindow(resume)
                }
            } catch (e: ApiError) {
                _state.value = DetailUiState(loading = false, error = e.message)
            }
        }
    }

    private fun watchlistKey(modelId: String, id: String): String = "$modelId|$id"

    fun toggleWatchlist() {
        val current = _state.value
        val detail = current.detail ?: return
        val modelId = current.modelId.orEmpty()
        val key = watchlistKey(modelId, detail.safeId)
        viewModelScope.launch {
            if (current.inWatchlist) {
                repo.watchlistDao.delete(key)
                _state.value = current.copy(inWatchlist = false)
            } else {
                repo.watchlistDao.upsert(
                    WatchlistEntity(
                        key = key,
                        contentId = detail.safeId,
                        modelId = modelId,
                        title = detail.safeTitle,
                        cover = detail.cover,
                        tag = detail.category,
                        addedAt = System.currentTimeMillis(),
                    )
                )
                _state.value = current.copy(inWatchlist = true)
            }
        }
    }

    /** Susun permintaan pemutaran untuk episode tertentu (atau lanjutan). */
    fun playRequest(episode: EpisodeData? = null): PlayRequest? {
        val current = _state.value
        val detail = current.detail ?: return null
        val modelId = current.modelId ?: return null
        val episodes = detail.episodeList
        val target = episode
            ?: episodes.firstOrNull { it.safeId == current.history?.episodeId }
            ?: episodes.firstOrNull()
            ?: return null
        val resume = if (episode == null && current.history?.episodeId == target.safeId) {
            current.history.positionMs
        } else 0L
        // Sumber prefetch ikut dikirim: pemutar langsung berputar tanpa fetch.
        val cached = current.sourceCache[target.safeId]
        // Episode terpilih dipanen lebih dulu agar klik berikutnya instan.
        prefetch(target)
        return PlayRequest(
            modelId = modelId,
            contentId = detail.safeId,
            episodeId = target.safeId,
            episodeNumber = target.safeNumber,
            title = detail.safeTitle,
            cover = detail.cover,
            episodes = episodes,
            startPositionMs = resume,
            streams = cached?.playableStreams.orEmpty(),
            subtitles = cached?.subtitles.orEmpty(),
        )
    }

    fun resumeHistory(): PlayRequest? {
        val current = _state.value
        val history = current.history ?: return null
        val detail = current.detail ?: return null
        val modelId = current.modelId ?: return null
        val episode = detail.episodeList.firstOrNull { it.safeId == history.episodeId }
            ?: detail.episodeList.firstOrNull() ?: return null
        val cached = current.sourceCache[episode.safeId]
        return PlayRequest(
            modelId = modelId,
            contentId = detail.safeId,
            episodeId = episode.safeId,
            episodeNumber = episode.safeNumber,
            title = detail.safeTitle,
            cover = detail.cover,
            episodes = detail.episodeList,
            startPositionMs = history.positionMs,
            streams = cached?.playableStreams.orEmpty(),
            subtitles = cached?.subtitles.orEmpty(),
        )
    }

    fun openDownloads(episode: EpisodeData) {
        val modelId = _state.value.modelId ?: return
        val id = _state.value.detail?.safeId ?: return
        _state.value = _state.value.copy(downloadEpisode = episode, downloads = emptyList(), downloadError = null, loadingDownloads = true)
        viewModelScope.launch {
            try {
                val data = repo.download(modelId, episode.safeId, id)
                val files = data.files
                _state.value = _state.value.copy(
                    downloads = files,
                    loadingDownloads = false,
                    downloadError = if (files.isEmpty()) "Tidak ada tautan unduhan untuk episode ini." else null,
                )
            } catch (e: ApiError) {
                _state.value = _state.value.copy(loadingDownloads = false, downloadError = e.message)
            }
        }
    }

    fun closeDownloads() {
        _state.value = _state.value.copy(downloadEpisode = null, downloads = emptyList(), downloadError = null)
    }

    fun dismissDownloadLock() {
        _state.value = _state.value.copy(downloadLocked = false)
    }

    private fun isPremium(): Boolean =
        com.mikdadstream.app.AppGraph.prefs.premiumUnlocked.value ||
            com.mikdadstream.app.AppGraph.users.account.value?.premium == true

    fun startDownload(file: MediaStream) {
        val s = _state.value
        val detail = s.detail ?: return
        val episode = s.downloadEpisode ?: return
        if (!isPremium()) {
            _state.value = s.copy(downloadLocked = true)
            return
        }
        val url = file.safeUrl ?: return
        val quality = file.quality
        val headersJson = file.headers
            ?.takeIf { it.isNotEmpty() }
            ?.let { com.google.gson.Gson().toJson(it) }
        val key = "dl:${contentId}:${episode.safeId}:${quality ?: "auto"}"
        com.mikdadstream.app.AppGraph.downloads.enqueue(
            com.mikdadstream.app.data.local.DownloadEntity(
                key = key,
                contentId = contentId,
                modelId = s.modelId.orEmpty(),
                title = detail.safeTitle,
                cover = detail.cover,
                episodeId = episode.safeId,
                episodeNumber = episode.episodeNumber ?: episode.safeNumber,
                quality = quality,
                format = file.format,
                url = url,
                headersJson = headersJson,
                filePath = null,
                bytesTotal = 0L,
                bytesDone = 0L,
                status = com.mikdadstream.app.data.download.DownloadManager.STATUS_QUEUED,
                progress = 0f,
                createdAt = System.currentTimeMillis(),
            )
        )
        closeDownloads()
    }

    fun refreshHistory() {
        viewModelScope.launch {
            val history = repo.historyDao.find(contentId)
            _state.value = _state.value.copy(history = history)
        }
    }
}
