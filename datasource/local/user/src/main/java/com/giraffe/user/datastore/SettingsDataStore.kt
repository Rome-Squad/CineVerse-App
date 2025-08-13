package com.giraffe.user.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale
import javax.inject.Inject

class SettingsDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    fun isDarkMode(): Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[IS_DARK_MODE] ?: false
        }

    suspend fun setDarkMode(isDark: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_DARK_MODE] = isDark
        }
    }

    fun getLanguage(): Flow<String> =
        dataStore.data.map { preferences ->
            preferences[APP_LANGUAGE] ?: Locale.getDefault().language
        }

    suspend fun setLanguage(languageCode: String) {
        dataStore.edit { preferences ->
            preferences[APP_LANGUAGE] = languageCode
        }
    }

    fun getContentPreference(): Flow<String> =
        dataStore.data.map { preferences ->
            preferences[CONTENT_PREFERENCE] ?: "HIDE_EXPLICIT"
        }

    suspend fun setContentPreference(preferenceName: String) {
        dataStore.edit { preferences ->
            preferences[CONTENT_PREFERENCE] = preferenceName
        }
    }

    companion object {
        val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
        val APP_LANGUAGE = stringPreferencesKey("app_language")
        val CONTENT_PREFERENCE = stringPreferencesKey("content_preference")
    }
}