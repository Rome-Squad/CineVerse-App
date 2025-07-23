package com.giraffe.repository

import com.giraffe.repository.datasource.UserRemoteDataSource
import com.giraffe.user.SessionManager
import com.giraffe.user.repository.AuthRepository
import android.util.Log

class AuthRepositoryImpl(
    private val remoteDataSource: UserRemoteDataSource,
    private val sessionIdManager: SessionManager
) : AuthRepository {
    override suspend fun login(username: String, password: String) {
        Log.d("AuthRepositoryImpl", "Starting login")

        val requestToken = remoteDataSource.createRequestToken()
        Log.d("AuthRepositoryImpl1", "Request token: $requestToken")

        val validatedToken = remoteDataSource.validateTokenWithLogin(requestToken, username, password)
        Log.d("AuthRepositoryImpl2", "Validated token: $validatedToken")

        val sessionId = remoteDataSource.createSession(validatedToken)
        Log.d("AuthRepositoryImpl3", "Session ID: $sessionId")

        sessionIdManager.saveSessionId(sessionId)
        Log.d("AuthRepositoryImpl4", "Session ID saved")

    }
}