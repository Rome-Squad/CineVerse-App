package com.giraffe.user.retrofit

import com.giraffe.repository.dto.UserDto
import com.giraffe.user.dto.DeleteSessionRequest
import com.giraffe.user.dto.GuestSessionResponse
import com.giraffe.user.dto.RequestTokenResponse
import com.giraffe.user.dto.SessionRequestBody
import com.giraffe.user.dto.SessionResponse
import com.giraffe.user.dto.TokenValidationBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApiService {

    @GET(CREATE_REQUEST_TOKEN)
    suspend fun createRequestToken(): Response<RequestTokenResponse>

    @POST(VALIDATE_TOKEN_WITH_LOGIN_URL)
    suspend fun validateTokenWithLogin(@Body requestBody: TokenValidationBody): Response<RequestTokenResponse>

    @POST(CREATE_SESSION_URL)
    suspend fun createSession(@Body requestBody: SessionRequestBody): Response<SessionResponse>

    @GET(ACCOUNT)
    suspend fun getUser(@Query(SESSION_ID) sessionId: String): Response<UserDto>

    @HTTP(method = DELETE, path = DELETE_SESSION_URL, hasBody = true)
    suspend fun deleteSession(@Body requestBody: DeleteSessionRequest): Response<Unit>

    @GET(CREATE_GUEST_SESSION_URL)
    suspend fun createGuestSession(): Response<GuestSessionResponse>



    companion object {
        private const val CREATE_REQUEST_TOKEN = "authentication/token/new"
        private const val VALIDATE_TOKEN_WITH_LOGIN_URL = "authentication/token/validate_with_login"
        private const val CREATE_SESSION_URL = "authentication/session/new"
        private const val DELETE_SESSION_URL = "authentication/session"
        private const val CREATE_GUEST_SESSION_URL = "authentication/guest_session/new"
        private const val ACCOUNT = "account"
        private const val SESSION_ID = "session_id"
        private const val DELETE = "DELETE"
    }
}
