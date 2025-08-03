package com.giraffe.user.retrofit

import com.giraffe.repository.dto.AccountDetailsDto
import com.giraffe.user.dto.RequestTokenResponse
import com.giraffe.user.dto.SessionRequestBody
import com.giraffe.user.dto.SessionResponse
import com.giraffe.user.dto.TokenValidationBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApiServiceRetrofit {

    @GET(TOKEN_NEW)
    suspend fun createRequestToken(): Response<RequestTokenResponse>

    @POST(VALIDATE_WITH_LOGIN)
    suspend fun validateTokenWithLogin(@Body requestBody: TokenValidationBody): Response<RequestTokenResponse>

    @POST(SESSION_NEW)
    suspend fun createSession(@Body requestBody: SessionRequestBody): Response<SessionResponse>

    @GET("account")
    suspend fun getAccountDetails(@Query("session_id") sessionId: String): Response<AccountDetailsDto>

    companion object {
        private const val AUTHENTICATION = "authentication"
        const val TOKEN_NEW = "$AUTHENTICATION/token/new"
        const val VALIDATE_WITH_LOGIN = "$AUTHENTICATION/token/validate_with_login"
        const val SESSION_NEW = "$AUTHENTICATION/session/new"
    }
}
