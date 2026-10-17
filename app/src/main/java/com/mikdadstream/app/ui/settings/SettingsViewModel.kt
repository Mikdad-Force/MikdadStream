package com.mikdadstream.app.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikdadstream.app.data.local.PrefsStore
import com.mikdadstream.app.data.remote.AccountData
import com.mikdadstream.app.data.remote.ApiError
import com.mikdadstream.app.data.repository.StreamRepository
import com.mikdadstream.app.data.remote.ApiClient
import com.mikdadstream.app.data.remote.SourceModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class SourceHealth(
    val model: SourceModel,
    val status: HealthStatus = HealthStatus.Running,
    val message: String = "",
    val stages: List<String> = emptyList(),
    val latencyMs: Long? = null,
    val playable: Boolean = false,
)

enum class HealthStatus { Running, Ok, Warning, Failed }

class SettingsViewModel(
    private val repo: StreamRepository,
    prefs: PrefsStore,
    private val apiClient: ApiClient,
) : ViewModel() {

    val profile = prefs.profile
    val themeMode = prefs.themeMode
    val accent = prefs.accent
    val serverStatus = prefs.serverStatus
    val hiddenSources = prefs.hiddenSources

    val allSources: StateFlow<List<SourceModel>> = repo.modelsState
        .map { result -> result?.models.orEmpty() }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private val prefsStore = prefs

    private val _account = MutableStateFlow<AccountData?>(null)
    val account: StateFlow<AccountData?> = _account

    private val _busy = MutableStateFlow(false)
    val busy: StateFlow<Boolean> = _busy

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _sourcesLoading = MutableStateFlow(false)
    val sourcesLoading: StateFlow<Boolean> = _sourcesLoading

    private val _healthRunning = MutableStateFlow(false)
    val healthRunning: StateFlow<Boolean> = _healthRunning

    private val _healthResults = MutableStateFlow<List<SourceHealth>>(emptyList())
    val healthResults: StateFlow<List<SourceHealth>> = _healthResults

    private val _healthProgress = MutableStateFlow("")
    val healthProgress: StateFlow<String> = _healthProgress

    init {
        refreshAccount()
        loadSources()
    }

    fun setThemeMode(mode: Int) = prefsStore.setThemeMode(mode)

    fun setAccent(value: Int) = prefsStore.setAccent(value)

    fun refreshAccount() {
        viewModelScope.launch {
            _busy.value = true
            _error.value = null
            try {
                val status = repo.accountStatus()
                _account.value = status
                status.user?.let { user ->
                    prefsStore.saveProfile(
                        displayName = user.displayName,
                        avatarUrl = user.avatarUrl,
                        userNumber = user.userNumber,
                        premium = user.premium ?: status.premium,
                    )
                }
                prefsStore.setServerStatus("Terhubung • ${status.cacheTtlSeconds ?: 600}s cache")
            } catch (e: ApiError) {
                _error.value = e.message
            } finally {
                _busy.value = false
            }
        }
    }

    fun renameDisplay(name: String) {
        val trimmed = name.trim()
        if (trimmed.isBlank()) return
        viewModelScope.launch {
            _busy.value = true
            _error.value = null
            try {
                val ok = apiClient.auth.forceLogin(trimmed)
                if (ok) {
                    prefsStore.saveProfile(displayName = trimmed, avatarUrl = null, userNumber = null, premium = null)
                    refreshAccount()
                } else {
                    _error.value = "Gagal memperbarui nama. Coba lagi."
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _busy.value = false
            }
        }
    }

    fun clearHistory() = viewModelScope.launch { repo.historyDao.clear() }
    fun clearSearchHistory() = viewModelScope.launch { repo.searchHistoryDao.clear() }
    fun clearWatchlist() = viewModelScope.launch { repo.watchlistDao.clear() }
    fun dismissError() { _error.value = null }

    fun loadSources() {
        _sourcesLoading.value = true
        viewModelScope.launch {
            try {
                repo.models(force = true)
            } catch (_: Exception) {
            } finally {
                _sourcesLoading.value = false
            }
        }
    }

    fun hideSource(id: String) = prefsStore.hideSource(id)
    fun unhideSource(id: String) = prefsStore.unhideSource(id)
    fun toggleHiddenSource(id: String) {
        if (prefsStore.isSourceHidden(id)) prefsStore.unhideSource(id) else prefsStore.hideSource(id)
    }

    fun cancelHealthCheck() {
        _healthRunning.value = false
        _healthProgress.value = ""
    }

    fun clearHealthResults() {
        _healthResults.value = emptyList()
        _healthProgress.value = ""
    }

    /**
     * Uji tiap sumber video sampai benar-benar bisa diputar:
     * categories -> videos -> detail -> source -> cek ada stream playable (direct atau embed).
     */
    fun checkSources() {
        if (_healthRunning.value) return
        viewModelScope.launch {
            _healthRunning.value = true
            _healthProgress.value = "Memuat daftar sumber…"
            val results = mutableListOf<SourceHealth>()
            try {
                val models = try {
                    repo.models(force = true).models
                } catch (e: Exception) {
                    _error.value = e.message ?: "Gagal memuat daftar sumber."
                    return@launch
                }
                val total = models.size
                models.forEachIndexed { index, model ->
                    _healthProgress.value = "Menguji ${model.safeName} (${index + 1}/$total)…"
                    val health = withContext(Dispatchers.IO) { probeSource(model) }
                    results.add(health)
                    _healthResults.value = results.toList()
                }
                _healthProgress.value =
                    "Selesai: ${results.count { it.playable }} siap putar dari ${results.size} sumber."
            } finally {
                _healthRunning.value = false
            }
        }
    }

    private suspend fun probeSource(model: SourceModel): SourceHealth {
        val stages = mutableListOf<String>()
        val start = System.currentTimeMillis()
        if (!model.isActive) {
            return SourceHealth(
                model = model,
                status = HealthStatus.Failed,
                message = model.status ?: "Sumber tidak aktif",
                stages = stages,
            )
        }
        try {
            stages.add("Kategori")
            val categories = repo.categories(model.safeId)
            if (categories.isEmpty()) {
                return SourceHealth(
                    model = model, status = HealthStatus.Warning,
                    message = "Kategori kosong", stages = stages, latencyMs = System.currentTimeMillis() - start,
                )
            }

            stages.add("Daftar video")
            val videos = repo.videos(model.safeId, categories.first().id, 1).items.orEmpty()
            val video = videos.firstOrNull()
            if (video == null) {
                return SourceHealth(
                    model = model, status = HealthStatus.Warning,
                    message = "Daftar video kosong", stages = stages, latencyMs = System.currentTimeMillis() - start,
                )
            }

            stages.add("Detail")
            val detail = repo.detail(model.safeId, video.safeId)

            stages.add("Episode")
            val episode = detail.episodes.orEmpty().firstOrNull()
            if (episode == null) {
                return SourceHealth(
                    model = model, status = HealthStatus.Warning,
                    message = "Tidak ada episode", stages = stages, latencyMs = System.currentTimeMillis() - start,
                )
            }

            stages.add("Sumber stream")
            val source = repo.source(model.safeId, episode.safeId, episode.safeId)
            val streams = source.streams.orEmpty()
            val playable = streams.any {
                !it.isEmbed && !it.safeUrl.isNullOrBlank() && it.format != "unknown"
            }
            val anyStream = streams.isNotEmpty()
            val elapsed = System.currentTimeMillis() - start
            return when {
                playable -> SourceHealth(
                    model = model, status = HealthStatus.Ok,
                    message = "Siap putar • ${streams.size} stream",
                    stages = stages, latencyMs = elapsed, playable = true,
                )
                anyStream -> SourceHealth(
                    model = model, status = HealthStatus.Warning,
                    message = "Hanya hoster web (WebView)",
                    stages = stages, latencyMs = elapsed, playable = true,
                )
                else -> SourceHealth(
                    model = model, status = HealthStatus.Failed,
                    message = "Tidak ada stream",
                    stages = stages, latencyMs = elapsed,
                )
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            return SourceHealth(
                model = model,
                status = HealthStatus.Failed,
                message = e.message?.take(120) ?: "Gagal",
                stages = stages,
                latencyMs = System.currentTimeMillis() - start,
            )
        }
    }
}
