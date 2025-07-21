package com.giraffe.repository.datasource

interface AuthRemoteDataSource {
    suspend fun createRequestToken(): String
    suspend fun validateTokenWithLogin(token: String, user: String, pass: String): String
    suspend fun createSession(token: String): String
}