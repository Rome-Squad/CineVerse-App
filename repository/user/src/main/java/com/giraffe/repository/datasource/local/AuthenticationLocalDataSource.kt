package com.giraffe.repository.datasource.local

interface AuthenticationLocalDataSource {

    suspend fun saveSessionId(sessionId: String)

    suspend fun getSessionId(): String?

    suspend fun isLoggedInByAccount(): Boolean

    suspend fun isLoggedIn(): Boolean

    suspend fun clearSessionId()

    suspend fun setTheUserAsGuest()

}