package com.giraffe.user

import com.giraffe.user.dto.CreateSessionRequest
import com.giraffe.user.dto.RequestTokenDto
import com.giraffe.user.dto.SessionDto
import com.giraffe.user.dto.ValidateTokenRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiService {
    @GET("authentication/token/new")
    suspend fun createRequestToken(): RequestTokenDto

    @POST("authentication/token/validate_with_login")
    suspend fun validateTokenWithLogin(@Body requestBody: ValidateTokenRequest): RequestTokenDto

    @POST("authentication/session/new")
    suspend fun createSession(@Body requestBody: CreateSessionRequest): SessionDto
}