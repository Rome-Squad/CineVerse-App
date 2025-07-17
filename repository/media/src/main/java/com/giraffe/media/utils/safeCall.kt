package com.giraffe.media.utils

import com.giraffe.media.exception.ApiException
import com.giraffe.media.exception.ForbiddenAccessException
import com.giraffe.media.exception.InvalidApiKeyException
import com.giraffe.media.exception.InvalidIdException
import com.giraffe.media.exception.MediaDomainException
import com.giraffe.media.exception.NetworkDomainException
import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.exception.NotFoundDomainException
import com.giraffe.media.exception.NotFoundException
import com.giraffe.media.exception.RateLimitExceededException
import com.giraffe.media.exception.RequestTimeoutException
import com.giraffe.media.exception.SerializationException
import com.giraffe.media.exception.ServerErrorDomainException
import com.giraffe.media.exception.ServerException
import com.giraffe.media.exception.TimeoutDomainException
import com.giraffe.media.exception.UnauthorizedAccessException
import com.giraffe.media.exception.*
import com.giraffe.media.exception.UnknownDomainException
import com.giraffe.media.exception.ValidationDomainException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object SafeCall {
    suspend operator fun <T> invoke(execute: suspend () -> T): T {
        return try {
            execute()
        } catch (e: Exception) {
            throw mapToDomainException(e)
        }
    }

    private fun mapToDomainException(e: Throwable): MediaDomainException = when (e) {

        is ApiException -> when (e.code) {
            3, 7, 10, 14, 16, 17, 30, 31, 32, 33, 35, 36 -> UnauthorizedDomainException()
            4, 42 -> InvalidRequestMethodDomainException()
            8, 38, 39, 45, 25 -> AccessDeniedDomainException()
            5, 18, 20, 22, 23, 26, 27, 28, 29, 41, 47 -> ValidationDomainException()
            6, 34, 37 -> NotFoundDomainException()
            2, 9, 43, 46 -> NetworkDomainException()
            11, 15, 44 -> ServerErrorDomainException()
            24 -> TimeoutDomainException()
            else -> UnknownDomainException()
        }


        is NoInternetException,
        is UnknownNetworkException,
        is UnknownHostException -> NetworkDomainException()

        is RequestTimeoutException,
        is SocketTimeoutException -> TimeoutDomainException()

        is ServerException -> ServerErrorDomainException()

        is UnauthorizedAccessException,
        is InvalidApiKeyException -> UnauthorizedDomainException()

        is ForbiddenAccessException,
        is TooManyRequestsException,
        is RateLimitExceededException -> AccessDeniedDomainException()

        is NotFoundException -> NotFoundDomainException()

        is InvalidIdException,
        is SerializationException,
        is IllegalArgumentException -> ValidationDomainException()

        is RedirectedException,
        is ClientErrorException,
        is CorruptDatabaseException,
        is DiskAccessException -> InfrastructureDomainException()


        else -> UnknownDomainException()
    }
}
