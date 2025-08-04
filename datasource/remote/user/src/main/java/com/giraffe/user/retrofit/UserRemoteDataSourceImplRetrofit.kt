package com.giraffe.user.retrofit

import com.giraffe.repository.datasource.remote.UserRemoteDataSource
import com.giraffe.repository.dto.UserDto
import com.giraffe.user.util.RetrofitUserRequestBuilder
import javax.inject.Inject

class UserRemoteDataSourceImplRetrofit @Inject constructor(
    private val retrofitRequestBuilder: RetrofitUserRequestBuilder<UserApiServiceRetrofit>
) : UserRemoteDataSource {
    override suspend fun getUser(sessionId: String): UserDto {
        return retrofitRequestBuilder.get { getUser(sessionId) }
    }
}
