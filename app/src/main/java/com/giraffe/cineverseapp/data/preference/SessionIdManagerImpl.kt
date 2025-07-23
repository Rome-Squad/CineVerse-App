package com.giraffe.cineverseapp.data.preference

import com.giraffe.user.SessionManager
import android.util.Log

class SessionIdManagerImpl(
    private val dataStorePreferences: DataStorePreferences
) : SessionManager {
    override suspend fun saveSessionId(sessionId: String) {
        Log.d("SessionIdManager", "Saving sessionId: $sessionId")
        dataStorePreferences.saveSessionId(sessionId)
    }

    override suspend fun getSessionId(): String? {
        val sessionId = dataStorePreferences.getSessionId()
        Log.d("SessionIdManager", "Fetched sessionId: $sessionId")
        return sessionId
    }
}