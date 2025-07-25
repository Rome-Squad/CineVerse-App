package com.giraffe.user.retrofit

import com.giraffe.user.dto.CreateSessionRequest
import com.giraffe.user.dto.RequestTokenDto
import com.giraffe.user.dto.SessionDto
import com.giraffe.user.dto.ValidateTokenRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApiServiceRetrofit {

    @GET(TOKEN_NEW)
    suspend fun createRequestToken(): Response<RequestTokenDto>

    @POST(VALIDATE_WITH_LOGIN)
    suspend fun validateTokenWithLogin(@Body requestBody: ValidateTokenRequest): Response<RequestTokenDto>

    @POST(SESSION_NEW)
    suspend fun createSession(@Body requestBody: CreateSessionRequest): Response<SessionDto>

    companion object {
        private const val AUTHENTICATION = "authentication"
        const val TOKEN_NEW = "$AUTHENTICATION/token/new"
        const val VALIDATE_WITH_LOGIN = "$AUTHENTICATION/token/validate_with_login"
        const val SESSION_NEW = "$AUTHENTICATION/session/new"
    }
}
