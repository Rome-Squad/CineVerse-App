package com.giraffe.media.util

interface SessionProvider {
    fun getSessionId(): String?
}