package com.giraffe.user.datastore

interface SessionProvider {
    fun getSessionId(): String?
    fun setSessionId(sessionId: String?)
}