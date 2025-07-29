package com.giraffe.user.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class AuthenticationDatastore(
    private val context: Context
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = DATA_STORE_NAME
    )

    suspend fun saveSessionId(sessionId: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.SESSION_ID] = sessionId
        }
    }

    suspend fun getSessionId(): String? {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.SESSION_ID]
        }.first()
    }

    companion object {
        private const val DATA_STORE_NAME = "CineVerseAuthenticationDatastore"
        const val SESSION_ID_KEY = "session_id"
    }

    private object PreferencesKeys {
        val SESSION_ID = stringPreferencesKey(SESSION_ID_KEY)
    }

}