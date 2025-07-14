package com.giraffe.user

interface SessionManager {


    suspend fun saveGuestSessionId(sessionId: String)
    suspend fun createGuestSessionId(): String?

}