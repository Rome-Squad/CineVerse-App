package com.giraffe.user.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthenticationDatastore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun saveSessionId(sessionId: String) {
        dataStore.edit { preferences ->
            preferences[SESSION_ID] = sessionId
        }
    }

    suspend fun getSessionId(): String? {
        return dataStore.data.map { preferences ->
            preferences[SESSION_ID]
        }.first()
    }

    suspend fun clearSessionId() {
        dataStore.edit { preferences ->
            preferences.remove(SESSION_ID)
        }
    }


    companion object {
        val SESSION_ID = stringPreferencesKey("session_id")
    }
}