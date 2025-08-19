package com.giraffe.user.retrofit

import com.giraffe.repository.dto.UserDto
import com.giraffe.user.dto.DeleteSessionRequest
import com.giraffe.user.dto.RequestTokenResponse
import com.giraffe.user.dto.SessionRequestBody
import com.giraffe.user.dto.SessionResponse
import com.giraffe.user.dto.TokenValidationBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApiService {

    @GET("authentication/token/new")
    suspend fun createRequestToken(): Response<RequestTokenResponse>

    @POST("authentication/token/validate_with_login")
    suspend fun validateTokenWithLogin(@Body requestBody: TokenValidationBody): Response<RequestTokenResponse>

    @POST("authentication/session/new")
    suspend fun createSession(@Body requestBody: SessionRequestBody): Response<SessionResponse>

    @GET("account")
    suspend fun getUser(@Query("session_id") sessionId: String): Response<UserDto>

    @HTTP(method = "DELETE", path = "authentication/session", hasBody = true)
    suspend fun deleteSession(@Body requestBody: DeleteSessionRequest): Response<Unit>
}