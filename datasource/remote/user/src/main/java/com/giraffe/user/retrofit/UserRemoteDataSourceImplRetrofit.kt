package com.giraffe.user.retrofit

import com.giraffe.repository.datasource.UserRemoteDataSource

class UserRemoteDataSourceImplRetrofit(
    private val userApiServiceRetrofit: UserApiServiceRetrofit
) : UserRemoteDataSource {

    override suspend fun getGuestSessionId(): String? {
        val response = userApiServiceRetrofit.getGuestSession()
        return response.body()?.guest_session_id
    }

}
