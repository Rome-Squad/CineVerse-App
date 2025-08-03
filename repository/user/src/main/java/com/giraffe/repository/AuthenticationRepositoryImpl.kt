package com.giraffe.repository

import com.giraffe.repository.datasource.local.AuthenticationRemoteDataSource
import com.giraffe.repository.datasource.remote.AuthenticationLocalDataSource
import com.giraffe.repository.utils.SafeCall
import com.giraffe.user.repository.AuthRepository
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthenticationRemoteDataSource,
    private val localDataSource: AuthenticationLocalDataSource
) : AuthRepository {
    override suspend fun login(username: String, password: String) = SafeCall {
        val requestToken = remoteDataSource.createRequestToken()
        val validatedToken =
            remoteDataSource.validateTokenWithLogin(requestToken, username, password)
        val sessionId = remoteDataSource.createSession(validatedToken)
        localDataSource.saveSessionId(sessionId)
    }

    override suspend fun isLoggedIn() = SafeCall { localDataSource.isLoggedIn() }
}