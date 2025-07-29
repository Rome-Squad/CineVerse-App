package com.giraffe.user

import android.util.Log
import com.giraffe.repository.datasource.remote.AuthenticationLocalDataSource
import com.giraffe.user.datastore.AuthenticationDatastore
import com.giraffe.user.datastore.SessionProvider
import kotlinx.coroutines.flow.Flow

class AuthenticationLocalDataSourceImp(
    private val dataStore: AuthenticationDatastore,
    private val sessionProvider: SessionProvider
) : AuthenticationLocalDataSource {
    override suspend fun saveSessionId(sessionId: String) {
        dataStore.saveSessionId(sessionId)
        sessionProvider.setSessionId(sessionId)
        val session = sessionProvider.getSessionId()
        Log.d("TAG", "savedSessionId from session provider: $session")
    }

    override suspend fun getSessionId(): String? {
        val sessionId = dataStore.getSessionId()
        return sessionId
    }
}