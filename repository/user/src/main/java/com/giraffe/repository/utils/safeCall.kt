package com.giraffe.repository.utils

import com.giraffe.repository.exceptions.GuestSessionCreationException
import com.giraffe.repository.exceptions.InvalidCredentialsException
import com.giraffe.repository.exceptions.SessionCreationException
import com.giraffe.repository.exceptions.TokenCreationException
import com.giraffe.repository.exceptions.TokenValidationException
import com.giraffe.user.exception.AuthDomainException
import com.giraffe.user.exception.GuestSessionCreationDomainException
import com.giraffe.user.exception.InvalidCredentialsDomainException
import com.giraffe.user.exception.SessionCreationDomainException
import com.giraffe.user.exception.TokenCreationDomainException
import com.giraffe.user.exception.TokenValidationDomainException
import com.giraffe.user.exception.UnknownException

suspend inline fun <reified T> safeCall(
    block: suspend () -> T
): T {
    return try {
        block()
    } catch (e: Throwable) {
        throw mapToMoviesException(e)
    }
}

fun mapToMoviesException(e: Throwable):AuthDomainException = when(e){
    is InvalidCredentialsException->InvalidCredentialsDomainException()
    is TokenCreationException -> TokenCreationDomainException()
    is TokenValidationException -> TokenValidationDomainException()
    is SessionCreationException -> SessionCreationDomainException()
    is GuestSessionCreationException -> GuestSessionCreationDomainException()
    else -> UnknownException()
}