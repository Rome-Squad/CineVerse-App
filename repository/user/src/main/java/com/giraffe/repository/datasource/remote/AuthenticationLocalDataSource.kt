package com.giraffe.repository.datasource.remote


interface AuthenticationLocalDataSource {

    suspend fun saveSessionId(sessionId: String)

    suspend fun getSessionId(): String?
}