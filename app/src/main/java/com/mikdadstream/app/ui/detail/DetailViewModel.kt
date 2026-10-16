package com.mikdadstream.app.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikdadstream.app.data.local.HistoryEntity
import com.mikdadstream.app.data.local.WatchlistEntity
import com.mikdadstream.app.data.remote.ApiError
import com.mikdadstream.app.data.remote.DetailData
import com.mikdadstream.app.data.remote.EpisodeData
import com.mikdadstream.app.data.remote.MediaStream
import com.mikdadstream.app.data.repository.StreamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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
)

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
)

class DetailViewModel(
    private val repo: StreamRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(DetailUiState())
    val state: StateFlow<DetailUiState> = _state

    private var contentId: String = ""

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
        return PlayRequest(
            modelId = modelId,
            contentId = detail.safeId,
            episodeId = target.safeId,
            episodeNumber = target.safeNumber,
            title = detail.safeTitle,
            cover = detail.cover,
            episodes = episodes,
            startPositionMs = resume,
        )
    }

    fun resumeHistory(): PlayRequest? {
        val current = _state.value
        val history = current.history ?: return null
        val detail = current.detail ?: return null
        val modelId = current.modelId ?: return null
        val episode = detail.episodeList.firstOrNull { it.safeId == history.episodeId }
            ?: detail.episodeList.firstOrNull() ?: return null
        return PlayRequest(
            modelId = modelId,
            contentId = detail.safeId,
            episodeId = episode.safeId,
            episodeNumber = episode.safeNumber,
            title = detail.safeTitle,
            cover = detail.cover,
            episodes = detail.episodeList,
            startPositionMs = history.positionMs,
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
