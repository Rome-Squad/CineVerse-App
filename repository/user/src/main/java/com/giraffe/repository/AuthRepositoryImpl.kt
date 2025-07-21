package com.giraffe.repository

import com.giraffe.repository.datasource.AuthRemoteDataSource
import com.giraffe.user.repository.AuthRepository

class AuthRepositoryImpl(
    private val remoteDataSource: AuthRemoteDataSource,
    private val sessionIdManager: SessionIdManager
) : AuthRepository {
    override suspend fun login(username: String, password: String) {

        val requestToken = remoteDataSource.createRequestToken()

        val validatedToken = remoteDataSource.validateTokenWithLogin(requestToken, username, password)

        val sessionId = remoteDataSource.createSession(validatedToken)

        sessionIdManager.saveSessionId(sessionId)
    }
}