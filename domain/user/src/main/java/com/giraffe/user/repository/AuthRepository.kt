package com.giraffe.user.repository

interface AuthRepository {
    suspend fun login(username: String, password: String)
}