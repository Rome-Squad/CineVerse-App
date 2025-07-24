package com.giraffe.repository.utils

import com.giraffe.repository.exceptions.ApiDataException
import com.giraffe.repository.exceptions.InvalidCredentialsException
import com.giraffe.repository.exceptions.SessionCreationException
import com.giraffe.repository.exceptions.TokenCreationException
import com.giraffe.repository.exceptions.TokenValidationException
import com.giraffe.user.exception.AuthException
import com.giraffe.user.exception.InvalidUsernameOrPasswordException
import com.giraffe.user.exception.UnknownException

object SafeCall {
    suspend operator fun <T> invoke(execute: suspend () -> T): T {
        return try {
            execute()
        } catch (e: Exception) {
            throw mapToDomainException(e)
        }
    }


    fun mapToDomainException(e: Throwable): AuthException = when (e) {
        is ApiDataException -> when (e.code) {
            // 401 Unauthorized (Auth or Token issues)
            30 -> InvalidUsernameOrPasswordException()
            else -> UnknownException()

        }

        is InvalidCredentialsException -> com.giraffe.user.exception.InvalidCredentialsException()
        is TokenCreationException -> com.giraffe.user.exception.TokenCreationException()
        is TokenValidationException -> com.giraffe.user.exception.TokenValidationException()
        is SessionCreationException -> com.giraffe.user.exception.SessionCreationException()

        else -> UnknownException()

    }
}
