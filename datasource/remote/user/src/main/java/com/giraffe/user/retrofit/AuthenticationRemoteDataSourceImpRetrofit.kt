package com.giraffe.user.retrofit

import com.giraffe.repository.datasource.remote.AuthenticationRemoteDataSource
import com.giraffe.repository.dto.AccountDetailsDto
import com.giraffe.user.dto.SessionRequestBody
import com.giraffe.user.dto.TokenValidationBody
import com.giraffe.user.util.RetrofitUserRequestBuilder
import javax.inject.Inject

class AuthenticationRemoteDataSourceImpRetrofit @Inject constructor(
    private val retrofitRequestBuilder: RetrofitUserRequestBuilder<UserApiServiceRetrofit>
) : AuthenticationRemoteDataSource {

    override suspend fun createRequestToken(): String =
        retrofitRequestBuilder.get { createRequestToken() }.requestToken

    override suspend fun validateTokenWithLogin(token: String, user: String, pass: String): String {
        val request = TokenValidationBody(username = user, password = pass, requestToken = token)
        return retrofitRequestBuilder.post { validateTokenWithLogin(request) }.requestToken
    }

    override suspend fun createSession(token: String): String {
        val request = SessionRequestBody(requestToken = token)
        return retrofitRequestBuilder.post { createSession(request) }.sessionId
    }

    override suspend fun getAccountDetails(sessionId: String): AccountDetailsDto {
        return retrofitRequestBuilder.get { getAccountDetails(sessionId) }
    }

}