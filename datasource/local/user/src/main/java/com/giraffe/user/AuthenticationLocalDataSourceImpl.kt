package com.giraffe.user

import com.giraffe.repository.datasource.local.AuthenticationLocalDataSource
import com.giraffe.user.datastore.AuthenticationDatastore
import com.giraffe.user.util.safeCall
import javax.inject.Inject

class AuthenticationLocalDataSourceImpl @Inject constructor(
    private val authenticationDatastore: AuthenticationDatastore
) : AuthenticationLocalDataSource {
    override suspend fun saveSessionId(sessionId: String) = safeCall {
        authenticationDatastore.saveSessionId(sessionId)
    }

    override suspend fun getSessionId(): String? = safeCall {
        authenticationDatastore.getSessionId()
    }

    override suspend fun saveAccountId(accountId: Int) = safeCall {
        authenticationDatastore.saveAccountId(accountId)
    }

    override suspend fun getAccountId(): Int? = safeCall {
        authenticationDatastore.getAccountId()
    }

    override suspend fun isLoggedIn(): Boolean = safeCall {
        authenticationDatastore.getSessionId() != null
    }
}