package com.giraffe.user.retrofit

import com.giraffe.repository.datasource.UserRemoteDataSource
import com.giraffe.repository.dto.GuestSessionResponse

class UserRemoteDataSourceImplRetrofit(
    private val api: UserApiServiceRetrofit
) : UserRemoteDataSource {

    override suspend fun getGuestSessionId(): String? {
        val response = api.getGuestSession()
        return if (response.isSuccessful) {
            val body = response.body()
            if (body?.success == true) body.guest_session_id else null
        } else {
            null
        }
    }

}
