package com.giraffe.user

interface SessionManager {
    suspend fun saveSessionId(sessionId: String)
    suspend fun getSessionId(): String?
}