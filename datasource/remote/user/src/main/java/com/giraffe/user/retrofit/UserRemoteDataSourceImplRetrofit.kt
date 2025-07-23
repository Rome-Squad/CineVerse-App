package com.giraffe.user.retrofit

import com.giraffe.repository.datasource.UserRemoteDataSource
import com.giraffe.user.dto.CreateSessionRequest
import com.giraffe.user.dto.ValidateTokenRequest
import android.util.Log

class UserRemoteDataSourceImplRetrofit(
    private val UserApiServiceRetrofit: UserApiServiceRetrofit
) : UserRemoteDataSource {

    override suspend fun getGuestSessionId(): String? {
        val response = UserApiServiceRetrofit.getGuestSession()
        Log.d("UserRemoteDS", "Guest session response: $response")

        return if (response.isSuccessful) {
            val body = response.body()
            Log.d("UserRemoteDS", "Guest session success: ${body?.guest_session_id}")

            if (body?.success == true) body.guest_session_id else null
        } else {
            Log.d("UserRemoteDS", "Failed to get guest session")
            null

        }
    }


    //override suspend fun createRequestToken(): String = UserApiServiceRetrofit.createRequestToken().requestToken
    override suspend fun createRequestToken(): String {
        val tokenDto = UserApiServiceRetrofit.createRequestToken()
        Log.d("UserRemoteDS", "Created request token: ${tokenDto.requestToken}")
        return tokenDto.requestToken
    }


    override suspend fun validateTokenWithLogin(token: String, user: String, pass: String): String {
        val request = ValidateTokenRequest(username = user, password = pass, requestToken = token)
        val response = UserApiServiceRetrofit.validateTokenWithLogin(request)
        Log.d("UserRemoteDS1", "Token validated: ${response.requestToken}")
        return response.requestToken
    }

    override suspend fun createSession(token: String): String {
        val request = CreateSessionRequest(requestToken = token)
        val response = UserApiServiceRetrofit.createSession(request)
        Log.d("UserRemoteDS2", "Session created: ${response.sessionId}")
        return response.sessionId
    }

    /*
    override suspend fun validateTokenWithLogin(token: String, user: String, pass: String): String {
        val request = ValidateTokenRequest(username = user, password = pass, requestToken = token)
        return UserApiServiceRetrofit.validateTokenWithLogin(request).requestToken
    }

    override suspend fun createSession(token: String): String {
        val request = CreateSessionRequest(requestToken = token)
        return UserApiServiceRetrofit.createSession(request).sessionId
    }
*/
}
