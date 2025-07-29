package com.giraffe.cineverseapp.data.preference

import com.giraffe.user.SessionManager
import javax.inject.Inject

class SessionIdManagerImpl @Inject constructor(
    private val dataStorePreferences: DataStorePreferences
) : SessionManager {
    override suspend fun saveSessionId(sessionId: String) {
        dataStorePreferences.saveSessionId(sessionId)
    }

    override suspend fun getSessionId(): String? {
        val sessionId = dataStorePreferences.getSessionId()
        return sessionId
    }
}