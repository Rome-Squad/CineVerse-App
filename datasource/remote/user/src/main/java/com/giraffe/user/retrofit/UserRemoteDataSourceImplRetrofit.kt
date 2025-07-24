package com.giraffe.user.retrofit

import com.giraffe.repository.datasource.UserRemoteDataSource
import com.giraffe.user.dto.CreateSessionRequest
import com.giraffe.user.dto.ValidateTokenRequest

class UserRemoteDataSourceImplRetrofit(
    private val UserApiServiceRetrofit: UserApiServiceRetrofit
) : UserRemoteDataSource {

    override suspend fun createRequestToken(): String = UserApiServiceRetrofit.createRequestToken().requestToken

    override suspend fun validateTokenWithLogin(token: String, user: String, pass: String): String {
        val request = ValidateTokenRequest(username = user, password = pass, requestToken = token)
        return UserApiServiceRetrofit.validateTokenWithLogin(request).requestToken
    }

    override suspend fun createSession(token: String): String {
        val request = CreateSessionRequest(requestToken = token)
        return UserApiServiceRetrofit.createSession(request).sessionId
    }

}
