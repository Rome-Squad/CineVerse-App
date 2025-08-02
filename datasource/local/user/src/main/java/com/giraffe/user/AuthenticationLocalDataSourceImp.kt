package com.giraffe.user

import com.giraffe.repository.datasource.remote.AuthenticationLocalDataSource
import com.giraffe.user.datastore.AuthenticationDatastore
import com.giraffe.user.util.safeCall
import javax.inject.Inject

class AuthenticationLocalDataSourceImp @Inject constructor(
    private val dataStore: AuthenticationDatastore
) : AuthenticationLocalDataSource {
    override suspend fun saveSessionId(sessionId: String) = safeCall {
        dataStore.saveSessionId(sessionId)
    }

    override suspend fun getSessionId(): String? = safeCall {
        dataStore.getSessionId()
    }

    override suspend fun isLoggedIn(): Boolean = safeCall {
        dataStore.getSessionId() != null
    }
}