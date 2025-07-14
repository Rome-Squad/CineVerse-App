package com.giraffe.cineverseapp.data.preference

import android.content.SharedPreferences
import com.giraffe.movie.datasource.remote.SessionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.core.content.edit

class SessionRepositoryImpl(private val prefs: SharedPreferences): SessionRepository {

    override suspend fun saveGuestSessionId(sessionId: String) {
        withContext(Dispatchers.IO) {
            prefs.edit { putString(GUEST_SESSION_ID, sessionId) }
        }
    }

    override suspend fun getGuestSessionId(): String? {
        return withContext(Dispatchers.IO) {
            prefs.getString(GUEST_SESSION_ID, null)
        }
    }

    companion object {
        private const val GUEST_SESSION_ID = "guest_session_id"
    }

}