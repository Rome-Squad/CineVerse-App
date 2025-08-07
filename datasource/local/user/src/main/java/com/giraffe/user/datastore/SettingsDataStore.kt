package com.giraffe.user.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsDataStore @Inject constructor(
    private val context: Context
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = DATA_STORE_NAME
    )

    private object PreferencesKeys {
        val IS_DARK_MODE = booleanPreferencesKey(IS_DARK_MODE_S)
        val APP_LANGUAGE = stringPreferencesKey(APP_LANGUAGE_S)
        val CONTENT_PREFERENCE = stringPreferencesKey("content_preference")
    }

    fun isDarkMode(): Flow<Boolean> =
        context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.IS_DARK_MODE] ?: false
        }

    suspend fun setDarkMode(isDark: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_DARK_MODE] = isDark
        }
    }

    fun getLanguage(): Flow<String> =
        context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.APP_LANGUAGE] ?: "en"
        }

    suspend fun setLanguage(languageCode: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.APP_LANGUAGE] = languageCode
        }
    }

    fun getContentPreference(): Flow<String> =
        context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.CONTENT_PREFERENCE] ?: "HIDE_EXPLICIT"
        }

    suspend fun setContentPreference(preferenceName: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.CONTENT_PREFERENCE] = preferenceName
        }
    }

    companion object {
        private const val DATA_STORE_NAME = "CineVerseSettingsDataStore"
        const val IS_DARK_MODE_S = "is_dark_mode"
        const val APP_LANGUAGE_S = "app_language"
    }
}