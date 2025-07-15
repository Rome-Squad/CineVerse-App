package com.giraffe.media.person.util

import com.giraffe.media.person.exception.ForbiddenAccessException
import com.giraffe.media.person.exception.InvalidApiKeyException
import com.giraffe.media.person.exception.InvalidPersonIdException
import com.giraffe.media.person.exception.NetworkException
import com.giraffe.media.person.exception.PersonException
import com.giraffe.media.person.exception.PersonNotFoundException
import com.giraffe.media.person.exception.RateLimitExceededException
import com.giraffe.media.person.exception.ServerErrorException
import com.giraffe.media.person.exception.UnauthorizedAccessException
import com.giraffe.media.person.exception.UnknownException

object SafeCall {
    suspend operator fun <T> invoke(execute: suspend () -> T): T {
        return try {
            execute()
        } catch (e: Exception) {
            throw e.toDomainException()
        }
    }

    private fun Exception.toDomainException(): PersonException = when (this) {
        is ApiException -> {
            when (this.code) {
                7 -> InvalidApiKeyException()
                34 -> PersonNotFoundException()
                401 -> UnauthorizedAccessException()
                403 -> ForbiddenAccessException()
                429 -> RateLimitExceededException()
                in 500..599 -> ServerErrorException()
                else -> UnknownException()
            }
        }

        is java.net.UnknownHostException,
        is java.net.SocketTimeoutException -> NetworkException()

        is kotlinx.serialization.SerializationException,
        is java.lang.IllegalArgumentException -> InvalidPersonIdException()

        else -> UnknownException()
    }
}
