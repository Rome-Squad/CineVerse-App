package com.giraffe.repository.datasource.local

interface AuthenticationLocalDataSource {

    suspend fun saveSessionId(sessionId: String)

    suspend fun getSessionId(): String?

    suspend fun saveAccountId(accountId: Int)

    suspend fun getAccountId(): Int?

    suspend fun isLoggedIn(): Boolean
}