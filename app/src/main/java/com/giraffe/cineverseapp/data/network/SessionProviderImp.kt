package com.giraffe.cineverseapp.data.network

import com.giraffe.user.datastore.SessionProvider

class SessionProviderImp: SessionProvider {

    @Volatile
    private var sessionId: String? = null

    override fun getSessionId(): String? = sessionId

    override fun setSessionId(sessionId: String?) {
        this.sessionId = sessionId
    }
}