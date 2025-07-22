package com.giraffe.user.retrofit

import com.giraffe.repository.dto.GuestSessionResponse
import retrofit2.Response
import retrofit2.http.GET

interface UserApiServiceRetrofit {

    @GET("authentication/guest_session/new")
    suspend fun getGuestSession(): Response<GuestSessionResponse>
}
