package com.giraffe.user

import com.giraffe.repository.datasource.AuthRemoteDataSource
import com.giraffe.user.dto.CreateSessionRequest
import com.giraffe.user.dto.ValidateTokenRequest

class AuthRemoteDataSourceImpl(private val apiService: AuthApiService) : AuthRemoteDataSource {
    override suspend fun createRequestToken(): String = apiService.createRequestToken().requestToken

    override suspend fun validateTokenWithLogin(token: String, user: String, pass: String): String {
        val request = ValidateTokenRequest(username = user, password = pass, requestToken = token)
        return apiService.validateTokenWithLogin(request).requestToken
    }

    override suspend fun createSession(token: String): String {
        val request = CreateSessionRequest(requestToken = token)
        return apiService.createSession(request).sessionId
    }
}