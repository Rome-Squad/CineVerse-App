package com.giraffe.user.retrofit

import com.giraffe.repository.dto.GuestSessionResponse
import retrofit2.Response
import retrofit2.http.GET

interface UserApiServiceRetrofit {

    @GET(ENDPOINT_GUEST_SESSION)
    suspend fun getGuestSession(): Response<GuestSessionResponse>

    companion object {
        private const val ENDPOINT_GUEST_SESSION = "authentication/guest_session/new"
    }
}