package com.giraffe.user.retrofit

import com.giraffe.repository.datasource.remote.AuthenticationRemoteDataSource
import com.giraffe.user.dto.DeleteSessionRequest
import com.giraffe.user.dto.SessionRequestBody
import com.giraffe.user.dto.TokenValidationBody
import com.giraffe.user.utils.safeCall
import javax.inject.Inject

class AuthenticationRemoteDataSourceImpl @Inject constructor(
    private val userApiService: UserApiService
) : AuthenticationRemoteDataSource {

    override suspend fun createRequestToken(): String =
        safeCall { userApiService.createRequestToken() }.requestToken

    override suspend fun validateTokenWithLogin(token: String, user: String, pass: String): String {
        val request = TokenValidationBody(username = user, password = pass, requestToken = token)
        return safeCall { userApiService.validateTokenWithLogin(request) }.requestToken
    }

    override suspend fun createSession(token: String): String {
        val request = SessionRequestBody(requestToken = token)
        return safeCall { userApiService.createSession(request) }.sessionId
    }

    override suspend fun deleteSession(sessionId: String) {
        val requestBody = DeleteSessionRequest(sessionId = sessionId)
        return safeCall { userApiService.deleteSession(requestBody) }
    }

    override suspend fun createGuestSession() =
        safeCall { userApiService.createGuestSession() }.guestSessionId
}