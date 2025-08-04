package com.giraffe.user.repository

import com.giraffe.user.entity.User

interface UserRepository {
    suspend fun getUser(): User
}