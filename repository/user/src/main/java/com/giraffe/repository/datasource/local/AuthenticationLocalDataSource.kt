package com.giraffe.repository.datasource.local

import kotlinx.coroutines.flow.Flow

interface AuthenticationLocalDataSource {

    suspend fun saveSessionId(sessionId: String)

    suspend fun getSessionId(): String?

    suspend fun isLoggedInByAccount(): Boolean

    suspend fun isLoggedIn(): Boolean

    fun getAccountId(): Flow<Int?>

    fun getUsername(): Flow<String?>

    fun getDisplayName(): Flow<String?>

    fun getAvatarUrl(): Flow<String?>

    suspend fun saveAccountId(id: Int)

    suspend fun saveUsername(username: String)

    suspend fun saveDisplayName(name: String)

    suspend fun saveAvatarUrl(url: String?)

    suspend fun clearAllData()
    suspend fun clearSessionId()

    suspend fun setUserAsGuest()

}