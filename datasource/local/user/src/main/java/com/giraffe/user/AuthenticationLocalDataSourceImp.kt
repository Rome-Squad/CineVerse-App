package com.giraffe.user

import com.giraffe.repository.datasource.remote.AuthenticationLocalDataSource
import com.giraffe.user.datastore.AuthenticationDatastore
import com.giraffe.user.datastore.SessionProvider

class AuthenticationLocalDataSourceImp(
    private val dataStore: AuthenticationDatastore,
    private val sessionProvider: SessionProvider
) : AuthenticationLocalDataSource {
    override suspend fun saveSessionId(sessionId: String) {
        dataStore.saveSessionId(sessionId)
        sessionProvider.setSessionId(sessionId)
    }

    override suspend fun getSessionId(): String? {
        val sessionId = dataStore.getSessionId()
        return sessionId
    }

    override suspend fun isLoggedIn(): Boolean {
        val sessionId = dataStore.getSessionId()
        return sessionId != null
    }
}