package com.mikdadstream.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikdadstream.app.data.local.HistoryEntity
import com.mikdadstream.app.data.local.PrefsStore
import com.mikdadstream.app.data.remote.ApiError
import com.mikdadstream.app.data.remote.MediaItem
import com.mikdadstream.app.data.remote.SourceModel
import com.mikdadstream.app.data.repository.CategoryBundle
import com.mikdadstream.app.data.repository.StreamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class HomeUiState(
    val categories: List<CategoryBundle> = emptyList(),
    val activeCategory: CategoryBundle? = null,
    val items: List<MediaItem> = emptyList(),
    val page: Int = 1,
    val hasMore: Boolean = false,
    val loading: Boolean = true,
    val refreshing: Boolean = false,
    val loadingMore: Boolean = false,
    val error: String? = null,
)

class HomeViewModel(
    private val repo: StreamRepository,
    private val prefs: PrefsStore,
    private val initialCategoryId: String? = null,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state

    val continueWatching: StateFlow<List<HistoryEntity>> = repo.historyDao.observeAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private var requestToken = 0

    init {
        viewModelScope.launch {
            prefs.activeSource.collect { sourceId -> loadSource(sourceId) }
        }
    }

    fun activeSourceId(): String = prefs.activeSource.value

    fun sourceModelsCache(): List<SourceModel> = emptyList()

    private suspend fun loadSource(sourceId: String) {
        val token = ++requestToken
        _state.value = HomeUiState(loading = true)
        try {
            val categories = repo.categories(sourceId)
            if (token != requestToken) return
            _state.value = _state.value.copy(categories = categories, loading = true)
            val first = categories.firstOrNull { it.id == initialCategoryId } ?: categories.firstOrNull()
            if (first == null) {
                _state.value = _state.value.copy(loading = false, error = "Sumber ini belum punya kategori.")
                return
            }
            loadCategory(sourceId, first, token)
        } catch (e: ApiError) {
            if (token != requestToken) return
            _state.value = _state.value.copy(loading = false, error = e.message)
        }
    }

    fun selectCategory(category: CategoryBundle) {
        if (category.id == _state.value.activeCategory?.id) return
        val token = ++requestToken
        // Optimistis: chip langsung aktif & daftar lama tetap tampil (dengan
        // indikator loading tipis) supaya perpindahan kategori tidak "patah"
        // menjadi layar skeleton/kosong.
        _state.value = _state.value.copy(
            loading = true,
            activeCategory = category,
            error = null,
        )
        viewModelScope.launch { loadCategory(prefs.activeSource.value, category, token) }
    }

    /** "Semua Film": tampilkan semua konten dari kategori pertama tanpa mengubah activeCategory di UI. */
    fun showAllFilms() {
        if (_state.value.categories.isEmpty()) return
        val first = _state.value.categories.first()
        // Hindari load ulang kalau sudah ada items dari kategori ini
        if (_state.value.activeCategory == first) return
        val token = ++requestToken
        _state.value = _state.value.copy(loading = true, items = emptyList(), error = null)
        viewModelScope.launch { loadCategory(prefs.activeSource.value, first, token) }
    }

    private suspend fun loadCategory(sourceId: String, category: CategoryBundle, token: Int) {
        try {
            val data = repo.videos(sourceId, category.id, page = 1)
            if (token != requestToken) return
            _state.value = HomeUiState(
                categories = _state.value.categories,
                activeCategory = category,
                items = data.items.orEmpty(),
                page = 1,
                hasMore = data.hasMore == true,
                loading = false,
            )
        } catch (e: ApiError) {
            if (token != requestToken) return
            _state.value = _state.value.copy(
                loading = false,
                refreshing = false,
                error = e.message,
                activeCategory = category,
            )
        }
    }

    fun refresh() {
        if (_state.value.refreshing) return
        val category = _state.value.activeCategory ?: return
        _state.value = _state.value.copy(refreshing = true, error = null)
        val token = ++requestToken
        viewModelScope.launch {
            try {
                val data = repo.videos(prefs.activeSource.value, category.id, page = 1)
                if (token != requestToken) return@launch
                _state.value = HomeUiState(
                    categories = _state.value.categories,
                    activeCategory = category,
                    items = data.items.orEmpty(),
                    page = 1,
                    hasMore = data.hasMore == true,
                    loading = false,
                )
            } catch (e: ApiError) {
                if (token != requestToken) return@launch
                _state.value = _state.value.copy(refreshing = false, loading = false, error = e.message)
            }
        }
    }

    fun loadMore() {
        val current = _state.value
        if (current.loading || current.loadingMore || !current.hasMore) return
        val category = current.activeCategory ?: return
        _state.value = current.copy(loadingMore = true, error = null)
        val token = ++requestToken
        viewModelScope.launch {
            try {
                val next = current.page + 1
                val data = repo.videos(prefs.activeSource.value, category.id, page = next)
                if (token != requestToken) return@launch
                val existing = _state.value.items.map { it.safeId }.toSet()
                val fresh = data.items.orEmpty().filter { it.safeId !in existing }
                _state.value = _state.value.copy(
                    items = _state.value.items + fresh,
                    page = next,
                    hasMore = data.hasMore == true && fresh.isNotEmpty(),
                    loadingMore = false,
                    refreshing = false,
                )
            } catch (e: ApiError) {
                if (token != requestToken) return@launch
                _state.value = _state.value.copy(loadingMore = false, refreshing = false, error = e.message)
            }
        }
    }

    fun retry() {
        viewModelScope.launch { loadSource(prefs.activeSource.value) }
    }
}
