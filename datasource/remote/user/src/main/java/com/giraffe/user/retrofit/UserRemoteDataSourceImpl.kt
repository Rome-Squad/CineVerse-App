package com.giraffe.user.retrofit

import com.giraffe.repository.datasource.remote.UserRemoteDataSource
import com.giraffe.repository.dto.UserDto
import com.giraffe.user.utils.safeCall
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val userApiService: UserApiService
) : UserRemoteDataSource {
    override suspend fun getUser(sessionId: String): UserDto =
        safeCall { userApiService.getUser(sessionId) }
}
