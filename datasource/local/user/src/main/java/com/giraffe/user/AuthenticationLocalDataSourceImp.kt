package com.giraffe.user

import com.giraffe.repository.datasource.remote.AuthenticationLocalDataSource
import com.giraffe.repository.utils.SafeCall
import com.giraffe.user.datastore.AuthenticationDatastore
import javax.inject.Inject

class AuthenticationLocalDataSourceImp @Inject constructor(
    private val dataStore: AuthenticationDatastore
) : AuthenticationLocalDataSource {
    override suspend fun saveSessionId(sessionId: String) = SafeCall {
        dataStore.saveSessionId(sessionId)
    }

    override suspend fun getSessionId(): String? = SafeCall {
        dataStore.getSessionId()
    }

    override suspend fun isLoggedIn(): Boolean = SafeCall {
        dataStore.getSessionId() != null
    }
}