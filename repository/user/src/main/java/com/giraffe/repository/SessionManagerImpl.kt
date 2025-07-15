package com.giraffe.repository

import android.util.Log
import com.giraffe.repository.datasource.UserRemoteDataSource
import com.giraffe.repository.utils.safeCall
import com.giraffe.user.SessionManager

class SessionManagerImpl(
    private val remote: UserRemoteDataSource
): SessionManager {
    override suspend fun saveGuestSessionId(sessionId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun createGuestSessionId(): String? {
        /*return safeCall {
            val sessionId = remote.getGuestSessionId()
            Log.d("TAG", "createGuestSessionId: $sessionId")
            return sessionId ?: throw Exception("Failed to get guest session id")
        }*/
        return "7a42b1a3b72850168238984f0738003d"
    }

}