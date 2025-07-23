package com.giraffe.repository

import com.giraffe.repository.datasource.UserRemoteDataSource
import com.giraffe.user.SessionManager
import com.giraffe.user.exception.GuestSessionException
import com.giraffe.user.repository.AuthRepository

class AuthRepositoryImpl(
    private val remoteDataSource: UserRemoteDataSource,
    private val sessionIdManager: SessionManager
) : AuthRepository {
    override suspend fun login(username: String, password: String) {

        val requestToken = remoteDataSource.createRequestToken()

        val validatedToken = remoteDataSource.validateTokenWithLogin(requestToken, username, password)

        val sessionId = remoteDataSource.createSession(validatedToken)

        sessionIdManager.saveSessionId(sessionId)

    }

    override suspend fun joinAsGuest(): String {
        val guestSessionId = remoteDataSource.getGuestSessionId()
            ?: throw GuestSessionException("Failed to get guest session ID")
        sessionIdManager.saveSessionId(guestSessionId)
        return guestSessionId
    }
}