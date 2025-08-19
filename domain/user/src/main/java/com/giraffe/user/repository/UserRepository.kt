package com.giraffe.user.repository

import com.giraffe.user.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUser(): Flow<User?>
    suspend fun refreshUser(): User
}