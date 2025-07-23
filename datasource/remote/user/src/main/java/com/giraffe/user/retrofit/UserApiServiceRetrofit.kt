package com.giraffe.user.retrofit

import com.giraffe.repository.dto.GuestSessionResponse
import com.giraffe.user.dto.CreateSessionRequest
import com.giraffe.user.dto.RequestTokenDto
import com.giraffe.user.dto.SessionDto
import com.giraffe.user.dto.ValidateTokenRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApiServiceRetrofit {

    @GET("authentication/guest_session/new")
    suspend fun getGuestSession(): Response<GuestSessionResponse>

    @GET("authentication/token/new")
    suspend fun createRequestToken(): RequestTokenDto

    @POST("authentication/token/validate_with_login")
    suspend fun validateTokenWithLogin(@Body requestBody: ValidateTokenRequest): RequestTokenDto

    @POST("authentication/session/new")
    suspend fun createSession(@Body requestBody: CreateSessionRequest): SessionDto
}
