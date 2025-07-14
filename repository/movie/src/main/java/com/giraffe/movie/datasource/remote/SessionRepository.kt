package com.giraffe.movie.datasource.remote

interface SessionRepository {
    suspend fun saveGuestSessionId(sessionId: String)
    suspend fun getGuestSessionId(): String?
}