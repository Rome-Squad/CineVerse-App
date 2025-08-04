package com.giraffe.repository.datasource.local

interface AuthenticationLocalDataSource {

    suspend fun saveSessionId(sessionId: String)

    suspend fun getSessionId(): String?
    suspend fun isLoggedIn(): Boolean
}