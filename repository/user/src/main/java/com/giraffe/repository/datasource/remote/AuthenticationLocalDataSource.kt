package com.giraffe.repository.datasource.remote

import kotlinx.coroutines.flow.Flow


interface AuthenticationLocalDataSource {

    suspend fun saveSessionId(sessionId: String)

    suspend fun getSessionId(): Flow<String?>
}