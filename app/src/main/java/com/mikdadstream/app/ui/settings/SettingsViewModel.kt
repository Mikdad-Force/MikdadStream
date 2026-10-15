package com.mikdadstream.app.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikdadstream.app.data.local.PrefsStore
import com.mikdadstream.app.data.remote.AccountData
import com.mikdadstream.app.data.remote.ApiError
import com.mikdadstream.app.data.repository.StreamRepository
import com.mikdadstream.app.data.remote.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repo: StreamRepository,
    prefs: PrefsStore,
    private val apiClient: ApiClient,
) : ViewModel() {

    val profile = prefs.profile
    val themeMode = prefs.themeMode
    val serverStatus = prefs.serverStatus

    private val prefsStore = prefs

    private val _account = MutableStateFlow<AccountData?>(null)
    val account: StateFlow<AccountData?> = _account

    private val _busy = MutableStateFlow(false)
    val busy: StateFlow<Boolean> = _busy

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        refreshAccount()
    }

    fun setThemeMode(mode: Int) = prefsStore.setThemeMode(mode)

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
}
