package com.mikdadstream.app.ui.nav

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikdadstream.app.data.local.PrefsStore
import com.mikdadstream.app.data.remote.ApiError
import com.mikdadstream.app.data.remote.SourceModel
import com.mikdadstream.app.data.repository.StreamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val repo: StreamRepository,
    private val prefs: PrefsStore,
) : ViewModel() {

    val profile = prefs.profile
    val themeMode = prefs.themeMode
    val activeSourceId = prefs.activeSource

    private val _models = MutableStateFlow<List<SourceModel>>(emptyList())
    val models: StateFlow<List<SourceModel>> = _models

    private val _modelsLoading = MutableStateFlow(false)
    val modelsLoading: StateFlow<Boolean> = _modelsLoading

    private val _modelsError = MutableStateFlow<String?>(null)
    val modelsError: StateFlow<String?> = _modelsError

    private val _sheetVisible = MutableStateFlow(false)
    val sheetVisible: StateFlow<Boolean> = _sheetVisible

    val activeSource: StateFlow<SourceModel?> = combine(_models, prefs.activeSource) { list, id ->
        list.firstOrNull { it.safeId.equals(id, true) }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, null)

    init {
        loadModels()
    }

    fun loadModels() {
        _modelsLoading.value = true
        _modelsError.value = null
        viewModelScope.launch {
            try {
                val result = repo.models(force = true)
                _models.value = result.models
                _modelsLoading.value = false
            } catch (e: ApiError) {
                _modelsError.value = e.message
                _modelsLoading.value = false
            } catch (e: Exception) {
                _modelsError.value = e.message ?: "Gagal memuat sumber."
                _modelsLoading.value = false
            }
        }
    }

    fun openSheet() { _sheetVisible.value = true }
    fun closeSheet() { _sheetVisible.value = false }

    fun selectSource(model: SourceModel) {
        if (!model.isActive) return
        prefs.setActiveSource(model.safeId)
        closeSheet()
    }
}
