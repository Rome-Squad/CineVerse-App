package com.giraffe.user.repository

import com.giraffe.user.entity.AccountDetails

interface AuthRepository {
    suspend fun login(username: String, password: String)
    suspend fun isLoggedIn(): Boolean
    suspend fun getAccountDetails(): AccountDetails
}