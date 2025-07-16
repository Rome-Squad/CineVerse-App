package com.giraffe.media.util

import com.giraffe.media.exception.*
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend inline fun <reified T> SafeCall(
    block: suspend () -> T
): T {
    return try {
        block()
    } catch (e: Throwable) {
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
