package com.giraffe.media.utils

import com.giraffe.media.exception.ApiException
import com.giraffe.media.exception.ClientErrorDomainException
import com.giraffe.media.exception.ClientErrorException
import com.giraffe.media.exception.CorruptDatabaseException
import com.giraffe.media.exception.CorruptDbDomainException
import com.giraffe.media.exception.DiskAccessException
import com.giraffe.media.exception.DiskErrorDomainException
import com.giraffe.media.exception.ForbiddenAccessException
import com.giraffe.media.exception.ForbiddenDomainException
import com.giraffe.media.exception.InvalidApiKeyDomainException
import com.giraffe.media.exception.InvalidApiKeyException
import com.giraffe.media.exception.InvalidIdException
import com.giraffe.media.exception.MediaDomainException
import com.giraffe.media.exception.NetworkDomainException
import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.exception.NotFoundDomainException
import com.giraffe.media.exception.NotFoundException
import com.giraffe.media.exception.RateLimitExceededException
import com.giraffe.media.exception.RateLimitedDomainException
import com.giraffe.media.exception.RedirectedDomainException
import com.giraffe.media.exception.RedirectedException
import com.giraffe.media.exception.RequestTimeoutException
import com.giraffe.media.exception.SerializationException
import com.giraffe.media.exception.ServerErrorDomainException
import com.giraffe.media.exception.ServerException
import com.giraffe.media.exception.TimeoutDomainException
import com.giraffe.media.exception.TooManyRequestsException
import com.giraffe.media.exception.UnauthorizedAccessException
import com.giraffe.media.exception.UnauthorizedDomainException
import com.giraffe.media.exception.UnknownDomainException
import com.giraffe.media.exception.UnknownNetworkException
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

    fun mapToDomainException(e: Throwable): MediaDomainException = when (e) {
        // Remote error codes (ApiException)
        is ApiException -> when (e.code) {
            7 -> InvalidApiKeyDomainException()
            34 -> NotFoundDomainException()
            401 -> UnauthorizedDomainException()
            403 -> ForbiddenDomainException()
            429 -> RateLimitedDomainException()
            in 500..599 -> ServerErrorDomainException()
            else -> UnknownDomainException()
        }

        // Network-related
        is NoInternetException,
        is UnknownHostException -> NetworkDomainException()

        is RequestTimeoutException,
        is SocketTimeoutException -> TimeoutDomainException()

        is TooManyRequestsException -> RateLimitedDomainException()
        is RedirectedException -> RedirectedDomainException()
        is ClientErrorException -> ClientErrorDomainException()

        // Server
        is ServerException -> ServerErrorDomainException()

        // Validation / Serialization
        is SerializationException,
        is IllegalArgumentException,
        is InvalidIdException -> ValidationDomainException()

        // Local/database
        is NotFoundException -> NotFoundDomainException()
        is CorruptDatabaseException -> CorruptDbDomainException()
        is DiskAccessException -> DiskErrorDomainException()

        // Auth (alternative fallback)
        is InvalidApiKeyException -> InvalidApiKeyDomainException()
        is UnauthorizedAccessException -> UnauthorizedDomainException()
        is ForbiddenAccessException -> ForbiddenDomainException()
        is RateLimitExceededException -> RateLimitedDomainException()

        // Unknown
        is UnknownNetworkException -> UnknownDomainException()
        else -> UnknownDomainException()
    }
}


