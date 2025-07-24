package com.giraffe.user.retrofit

import com.giraffe.repository.datasource.UserRemoteDataSource
import com.giraffe.user.dto.CreateSessionRequest
import com.giraffe.user.dto.ValidateTokenRequest
import com.giraffe.user.util.RetrofitUserRequestBuilder

class UserRemoteDataSourceImplRetrofit(
    private val retrofitRequestBuilder: RetrofitUserRequestBuilder<UserApiServiceRetrofit>
) : UserRemoteDataSource {

    override suspend fun createRequestToken(): String =
        retrofitRequestBuilder.get { createRequestToken() }.requestToken

    override suspend fun validateTokenWithLogin(token: String, user: String, pass: String): String {
        val request = ValidateTokenRequest(username = user, password = pass, requestToken = token)
        return retrofitRequestBuilder.post { validateTokenWithLogin(request) }.requestToken
    }

    override suspend fun createSession(token: String): String {
        val request = CreateSessionRequest(requestToken = token)
        return retrofitRequestBuilder.post { createSession(request) }.sessionId
    }

}
