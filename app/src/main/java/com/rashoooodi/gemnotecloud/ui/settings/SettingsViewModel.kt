package com.rashoooodi.gemnotecloud.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rashoooodi.gemnotecloud.data.settings.GeminiSettings
import com.rashoooodi.gemnotecloud.data.settings.GeminiSettingsStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val store: GeminiSettingsStore
) : ViewModel() {

    val settings: StateFlow<GeminiSettings> = store.settings.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = GeminiSettings()
    )

    val availableModels: List<String> = GeminiSettingsStore.SUPPORTED_MODELS

    fun saveApiKey(value: String) {
        viewModelScope.launch { store.setApiKey(value) }
    }

    fun selectModel(value: String) {
        viewModelScope.launch { store.setModel(value) }
    }
}
