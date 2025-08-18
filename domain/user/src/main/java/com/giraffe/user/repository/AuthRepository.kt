package com.giraffe.user.repository

interface AuthRepository {
    suspend fun login(username: String, password: String)
    suspend fun isLoggedIn(): Boolean
    suspend fun isLoggedInByAccount(): Boolean
    suspend fun logout()
    suspend fun loginAsGuest()
}