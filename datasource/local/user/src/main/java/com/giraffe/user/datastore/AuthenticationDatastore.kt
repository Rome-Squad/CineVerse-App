package com.giraffe.user.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
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
    suspend fun saveAccountId(id: Int) {
        dataStore.edit { it[ACCOUNT_ID] = id }
    }

    suspend fun saveUsername(username: String) {
        dataStore.edit { it[USER_USERNAME] = username }
    }

    suspend fun saveDisplayName(name: String) {
        dataStore.edit { it[USER_DISPLAY_NAME] = name }
    }

    suspend fun saveAvatarUrl(url: String?) {
        dataStore.edit { preferences ->
            if (url != null) {
                preferences[USER_AVATAR_URL] = url
            } else {
                preferences.remove(USER_AVATAR_URL)
            }
        }
    }

    suspend fun getSessionId(): String? {
        return dataStore.data.map { preferences ->
            preferences[SESSION_ID]
        }.first()
    }
    fun getAccountId(): Flow<Int?> = dataStore.data.map { it[ACCOUNT_ID] }
    fun getUsername(): Flow<String?> = dataStore.data.map { it[USER_USERNAME] }
    fun getDisplayName(): Flow<String?> = dataStore.data.map { it[USER_DISPLAY_NAME] }
    fun getAvatarUrl(): Flow<String?> = dataStore.data.map { it[USER_AVATAR_URL] }


    suspend fun clearAll() {
        dataStore.edit { preferences ->
            preferences.remove(SESSION_ID)
            preferences.remove(ACCOUNT_ID)
            preferences.remove(USER_USERNAME)
            preferences.remove(USER_DISPLAY_NAME)
            preferences.remove(USER_AVATAR_URL)
        }
    }

    suspend fun setUserAsGuest() {
        dataStore.edit { preferences ->
            preferences[IS_GUEST] = true
        }
    }

    suspend fun setUserAsNotGuest() {
        dataStore.edit { preferences ->
            preferences[IS_GUEST] = false
        }
    }

    suspend fun isUserGuest(): Boolean {
        return dataStore.data.map { preferences ->
            preferences[IS_GUEST]
        }.first() == true
    }


    private companion object {
        val SESSION_ID = stringPreferencesKey("session_id")
        val ACCOUNT_ID = intPreferencesKey("account_id")
        val USER_USERNAME = stringPreferencesKey("user_username")
        val USER_DISPLAY_NAME = stringPreferencesKey("user_display_name")
        val USER_AVATAR_URL = stringPreferencesKey("user_avatar_url")
        val IS_GUEST = booleanPreferencesKey("is_guest")
    }
}