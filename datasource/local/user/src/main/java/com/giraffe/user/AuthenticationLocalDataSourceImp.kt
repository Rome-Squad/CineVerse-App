package com.giraffe.user

import com.giraffe.repository.datasource.remote.AuthenticationLocalDataSource
import com.giraffe.user.datastore.AuthenticationDatastore
import kotlinx.coroutines.flow.Flow

class AuthenticationLocalDataSourceImp(
    private val dataStore: AuthenticationDatastore,
) : AuthenticationLocalDataSource {
    override suspend fun saveSessionId(sessionId: String) {
        dataStore.saveSessionId(sessionId)
    }

    override suspend fun getSessionId(): Flow<String?> {
        val sessionIdFlow = dataStore.getSessionId()
        return sessionIdFlow
    }
}