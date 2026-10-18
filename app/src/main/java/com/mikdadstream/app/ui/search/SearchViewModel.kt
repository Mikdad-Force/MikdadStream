package com.mikdadstream.app.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikdadstream.app.data.local.SearchHistoryEntity
import com.mikdadstream.app.data.remote.ApiError
import com.mikdadstream.app.data.remote.MediaItem
import com.mikdadstream.app.AppGraph
import com.mikdadstream.app.data.repository.StreamRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class SearchUiState(
    val query: String = "",
    val results: List<MediaItem> = emptyList(),
    val page: Int = 1,
    val hasMore: Boolean = false,
    val loading: Boolean = false,
    val loadingMore: Boolean = false,
    val error: String? = null,
    val searched: Boolean = false,
)

class SearchViewModel(
    private val repo: StreamRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(SearchUiState())
    val state: StateFlow<SearchUiState> = _state

    val history: StateFlow<List<SearchHistoryEntity>> = repo.searchHistoryDao.observeAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private var searchJob: Job? = null
    private var token = 0

    fun onQueryChange(query: String) {
        _state.value = _state.value.copy(query = query, error = null)
        searchJob?.cancel()
        if (query.isBlank()) {
            _state.value = _state.value.copy(
                results = emptyList(),
                loading = false,
                searched = false,
                page = 1,
                hasMore = false,
            )
            return
        }
        searchJob = viewModelScope.launch {
            delay(450)
            runSearch(query.trim(), page = 1, append = false, saveHistory = true)
        }
    }

    fun submit(query: String = _state.value.query) {
        val trimmed = query.trim()
        if (trimmed.isBlank()) return
        searchJob?.cancel()
        viewModelScope.launch { runSearch(trimmed, page = 1, append = false, saveHistory = true) }
    }

    fun searchFrom(query: String) {
        _state.value = _state.value.copy(query = query)
        submit(query)
    }

    private suspend fun runSearch(query: String, page: Int, append: Boolean, saveHistory: Boolean) {
        val myToken = ++token
        _state.value = _state.value.copy(
            loading = !append,
            loadingMore = append,
            error = null,
            searched = true,
        )
        try {
            val results = repo.search(AppGraph.prefs.activeSource.value, query, page)
            if (myToken != token) return
            val existing = if (append) _state.value.results.map { it.safeId + it.source }.toSet() else emptySet()
            val fresh = results.filter { (it.safeId + it.source) !in existing }
            _state.value = _state.value.copy(
                results = if (append) _state.value.results + fresh else fresh,
                page = page,
                hasMore = fresh.isNotEmpty(),
                loading = false,
                loadingMore = false,
            )
            if (saveHistory && page == 1) {
                repo.searchHistoryDao.upsert(SearchHistoryEntity(query = query, at = System.currentTimeMillis()))
            }
        } catch (e: ApiError) {
            if (myToken != token) return
            _state.value = _state.value.copy(
                loading = false,
                loadingMore = false,
                error = if (!append) e.message else _state.value.error ?: e.message,
            )
        }
    }

    fun loadMore() {
        val current = _state.value
        if (current.loading || current.loadingMore || !current.hasMore || current.query.isBlank()) return
        viewModelScope.launch { runSearch(current.query.trim(), current.page + 1, append = true, saveHistory = false) }
    }

    fun clearHistory() {
        viewModelScope.launch { repo.searchHistoryDao.clear() }
    }

    fun removeHistory(query: String) {
        viewModelScope.launch { repo.searchHistoryDao.delete(query) }
    }

    fun retry() = submit()
}
