package com.giraffe.repository

import android.util.Log
import com.giraffe.repository.datasource.local.AuthenticationRemoteDataSource
import com.giraffe.repository.datasource.remote.AuthenticationLocalDataSource
import com.giraffe.repository.utils.SafeCall
import com.giraffe.user.repository.AuthRepository

class AuthenticationRepositoryImpl(
    private val remoteDataSource: AuthenticationRemoteDataSource,
    private val localDataSource: AuthenticationLocalDataSource
) : AuthRepository {
    override suspend fun login(username: String, password: String) = SafeCall {
        val requestToken = remoteDataSource.createRequestToken()

        val validatedToken =
            remoteDataSource.validateTokenWithLogin(requestToken, username, password)

        val sessionId = remoteDataSource.createSession(validatedToken)
        Log.d("TAG", "session id: $sessionId")

        localDataSource.saveSessionId(sessionId)
    }

    override suspend fun isLoggedIn(): Boolean {
        return localDataSource.isLoggedIn()
    }
}