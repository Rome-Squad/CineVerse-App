package com.giraffe.repository

interface SessionIdManager {
    suspend fun saveSessionId(sessionId: String)
    suspend fun getSessionId(): String?
}