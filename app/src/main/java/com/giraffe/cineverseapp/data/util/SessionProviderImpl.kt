package com.giraffe.cineverseapp.data.util

import com.giraffe.media.util.SessionProvider

class SessionProviderImpl: SessionProvider {

    @Volatile
    private var sessionId: String? = null

    override fun getSessionId(): String? = sessionId

    fun setSessionId(sessionId: String?) {
        this.sessionId = sessionId
    }
}