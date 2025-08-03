package com.giraffe.repository

import android.util.Log
import com.giraffe.repository.datasource.local.AuthenticationLocalDataSource
import com.giraffe.repository.datasource.mapper.toEntity
import com.giraffe.repository.datasource.remote.UserRemoteDataSource
import com.giraffe.repository.utils.SafeCall
import com.giraffe.user.entity.User
import com.giraffe.user.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val localDataSource: AuthenticationLocalDataSource,
) : UserRepository {
    override suspend fun getUser(): User = SafeCall {
        val sessionId = localDataSource.getSessionId()
        val userDto = userRemoteDataSource.getUser(sessionId.toString())
        Log.d("AccountDetails", "Account Details DTO received: $userDto")
        userDto.toEntity()
    }
}