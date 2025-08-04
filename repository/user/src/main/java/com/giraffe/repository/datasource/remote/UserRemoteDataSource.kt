package com.giraffe.repository.datasource.remote

import com.giraffe.repository.dto.UserDto


interface UserRemoteDataSource {
    suspend fun getUser(sessionId: String): UserDto
}