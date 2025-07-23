package com.giraffe.cineverseapp.data.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import android.util.Log

class DataStorePreferences(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = DATA_STORE_NAME
    )

    suspend fun setFirstTimeStatus() {
        context.dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(
                IS_FIRST_TIME
            )] = false
        }
    }

    suspend fun isFirstTime() = context.dataStore.data.map { preferences ->
        preferences[booleanPreferencesKey(IS_FIRST_TIME)] != false
    }.first()

    suspend fun setDarkThemeStatus(isDark: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(
                IS_DARK_THEME
            )] = isDark
        }
    }

    fun isDarkTheme() = context.dataStore.data.map { preferences ->
        preferences[booleanPreferencesKey(IS_DARK_THEME)] != false
    }

    suspend fun saveSessionId(sessionId: String) {
        Log.d("DataStorePrefs", "Saving session ID to DataStore: $sessionId")
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.TMDB_SESSION_ID] = sessionId
        }
    }

    suspend fun getSessionId(): String? {
       val id= context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.TMDB_SESSION_ID]

        }.first()
        Log.d("DataStorePrefs1", "Retrieved session ID: $id")
return id
    }

    companion object {

        private const val DATA_STORE_NAME = "CineVerseDataStore"
        const val IS_FIRST_TIME = "IS_FIRST_TIME"
        const val IS_DARK_THEME = "IS_DARK_THEME"
        const val TMDB_SESSION_ID_KEY = "tmdb_session_id"
    }

    private object PreferencesKeys {
        val TMDB_SESSION_ID = stringPreferencesKey(TMDB_SESSION_ID_KEY)
    }

}