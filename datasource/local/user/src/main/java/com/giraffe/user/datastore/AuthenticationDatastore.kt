package com.giraffe.user.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.giraffe.repository.exceptions.SessionNotFoundException
import kotlinx.coroutines.flow.Flow
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

    fun getSessionId(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.SESSION_ID]
        }
    }

    companion object {
        private const val DATA_STORE_NAME = "CineVerseAuthenticationDatastore"
        const val SESSION_ID_KEY = "session_id"
    }

    private object PreferencesKeys {
        val SESSION_ID = stringPreferencesKey(SESSION_ID_KEY)
    }

}