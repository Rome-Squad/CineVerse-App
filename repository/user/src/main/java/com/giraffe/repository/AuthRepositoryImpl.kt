package com.giraffe.repository

import com.giraffe.repository.datasource.UserRemoteDataSource
import com.giraffe.repository.utils.SafeCall
import com.giraffe.user.SessionManager
import com.giraffe.user.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource,
    private val sessionIdManager: SessionManager
) : AuthRepository {
    override suspend fun login(username: String, password: String) = SafeCall {
            val requestToken = remoteDataSource.createRequestToken()

            val validatedToken = remoteDataSource.validateTokenWithLogin(requestToken, username, password)

            val sessionId = remoteDataSource.createSession(validatedToken)

            sessionIdManager.saveSessionId(sessionId)
        }
}