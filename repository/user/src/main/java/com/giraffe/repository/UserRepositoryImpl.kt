package com.giraffe.repository

import com.giraffe.repository.datasource.local.AuthenticationLocalDataSource
import com.giraffe.repository.datasource.mapper.toEntity
import com.giraffe.repository.datasource.remote.UserRemoteDataSource
import com.giraffe.repository.exceptions.InvalidIdDataException
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
        if (!localDataSource.isLoggedIn()) {
            throw InvalidIdDataException()
        }
        val userResponse = userRemoteDataSource.getUser(sessionId.toString())
        localDataSource.saveAccountId(userResponse.id)
        userResponse.toEntity()
    }
}