package com.giraffe.user.retrofit

import com.giraffe.repository.datasource.UserRemoteDataSource

class UserRemoteDataSourceImplRetrofit(
    private val UserApiServiceRetrofit: UserApiServiceRetrofit
) : UserRemoteDataSource {

    override suspend fun getGuestSessionId(): String? {
        val response = UserApiServiceRetrofit.getGuestSession()
        return if (response.isSuccessful) {
            val body = response.body()
            if (body?.success == true) body.guest_session_id else null
        } else {
            null
        }
    }

}
