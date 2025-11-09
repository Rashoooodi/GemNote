package com.rashoooodi.gemnotecloud.data.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.remove
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.geminiDataStore: DataStore<Preferences> by preferencesDataStore(name = "gemini_settings")

data class GeminiSettings(
    val apiKey: String = "",
    val model: String = GeminiSettingsStore.DEFAULT_MODEL
)

class GeminiSettingsStore(private val dataStore: DataStore<Preferences>) {

    val settings: Flow<GeminiSettings> = dataStore.data.map { prefs ->
        val savedModel = prefs[MODEL_KEY] ?: DEFAULT_MODEL
        GeminiSettings(
            apiKey = prefs[API_KEY].orEmpty(),
            model = if (savedModel in SUPPORTED_MODELS) savedModel else DEFAULT_MODEL
        )
    }

    suspend fun setApiKey(value: String) {
        dataStore.edit { prefs ->
            val sanitized = value.trim()
            if (sanitized.isEmpty()) {
                prefs.remove(API_KEY)
            } else {
                prefs[API_KEY] = sanitized
            }
        }
    }

    suspend fun setModel(value: String) {
        dataStore.edit { prefs ->
            val target = if (value in SUPPORTED_MODELS) value else DEFAULT_MODEL
            prefs[MODEL_KEY] = target
        }
    }

    companion object {
        const val DEFAULT_MODEL = "gemini-1.5-flash"
        val SUPPORTED_MODELS = listOf(
            "gemini-1.5-flash",
            "gemini-1.5-pro",
            "gemini-2.0-flash-exp"
        )
        private val API_KEY = stringPreferencesKey("gemini_api_key")
        private val MODEL_KEY = stringPreferencesKey("gemini_model")
    }
}
