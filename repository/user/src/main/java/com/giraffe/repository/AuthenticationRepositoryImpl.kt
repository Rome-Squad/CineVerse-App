package com.giraffe.repository

import com.giraffe.repository.datasource.local.AuthenticationLocalDataSource
import com.giraffe.repository.datasource.remote.AuthenticationRemoteDataSource
import com.giraffe.repository.utils.SafeCall
import com.giraffe.user.repository.AuthRepository
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthenticationRemoteDataSource,
    private val localDataSource: AuthenticationLocalDataSource,
) : AuthRepository {
    override suspend fun login(username: String, password: String) = SafeCall {
        val requestToken = authRemoteDataSource.createRequestToken()
        val validatedToken =
            authRemoteDataSource.validateTokenWithLogin(requestToken, username, password)
        val sessionId = authRemoteDataSource.createSession(validatedToken)
        localDataSource.saveSessionId(sessionId)
    }

    override suspend fun isLoggedIn() = SafeCall { localDataSource.isLoggedIn() }
    override suspend fun logout() = SafeCall {
        val sessionId = localDataSource.getSessionId()

        if (sessionId != null) {
                authRemoteDataSource.deleteSession(sessionId)
        }
        localDataSource.clearSessionId()
    }

}