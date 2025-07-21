package com.giraffe.cineverseapp.data.preference

import com.giraffe.repository.SessionIdManager

class SessionIdManagerImpl(
    private val dataStorePreferences: DataStorePreferences
) : SessionIdManager {
    override suspend fun saveSessionId(sessionId: String) {
        dataStorePreferences.saveSessionId(sessionId)
    }

    override suspend fun getSessionId(): String? {
        return dataStorePreferences.getSessionId()
    }
}