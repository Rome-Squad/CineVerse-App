package com.giraffe.user

import com.giraffe.repository.datasource.remote.AuthenticationLocalDataSource
import com.giraffe.user.datastore.AuthenticationDatastore
import javax.inject.Inject

class AuthenticationLocalDataSourceImp @Inject constructor(
    private val dataStore: AuthenticationDatastore
) : AuthenticationLocalDataSource {
    override suspend fun saveSessionId(sessionId: String) {
        dataStore.saveSessionId(sessionId)
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