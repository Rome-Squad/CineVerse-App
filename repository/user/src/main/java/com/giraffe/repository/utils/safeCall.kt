package com.giraffe.repository.utils

import com.giraffe.repository.exceptions.GuestSessionCreationException
import com.giraffe.repository.exceptions.InvalidCredentialsException
import com.giraffe.repository.exceptions.SessionCreationException
import com.giraffe.repository.exceptions.TokenCreationException
import com.giraffe.repository.exceptions.TokenValidationException
import com.giraffe.user.exception.AuthException
import com.giraffe.user.exception.UnknownException

suspend inline fun <reified T> safeCall(
    block: suspend () -> T
): T {
    return try {
        block()
    } catch (e: Throwable) {
        throw mapToAuthException(e)
    }
}

fun mapToAuthException(e: Throwable):AuthException = when(e){
    is InvalidCredentialsException-> com.giraffe.user.exception.InvalidCredentialsException()
    is TokenCreationException -> com.giraffe.user.exception.TokenCreationException()
    is TokenValidationException -> com.giraffe.user.exception.TokenValidationException()
    is SessionCreationException -> com.giraffe.user.exception.SessionCreationException()
    is GuestSessionCreationException -> com.giraffe.user.exception.GuestSessionCreationException()
    else -> UnknownException()
}