package com.giraffe.media.utils

import com.giraffe.media.exception.AccessDeniedException
import com.giraffe.media.exception.ApiDataException
import com.giraffe.media.exception.ClientErrorDataException
import com.giraffe.media.exception.CorruptDatabaseDataException
import com.giraffe.media.exception.DiskAccessDataException
import com.giraffe.media.exception.ForbiddenAccessDataException
import com.giraffe.media.exception.InfrastructureException
import com.giraffe.media.exception.InvalidApiKeyDataException
import com.giraffe.media.exception.InvalidIdDataException
import com.giraffe.media.exception.InvalidRequestMethodException
import com.giraffe.media.exception.MediaException
import com.giraffe.media.exception.NetworkException
import com.giraffe.media.exception.NoInternetDataException
import com.giraffe.media.exception.NotFoundDataException
import com.giraffe.media.exception.NotFoundException
import com.giraffe.media.exception.RateLimitExceededDataException
import com.giraffe.media.exception.RedirectedDataException
import com.giraffe.media.exception.RequestTimeoutDataException
import com.giraffe.media.exception.SerializationDataException
import com.giraffe.media.exception.ServerDataException
import com.giraffe.media.exception.ServerErrorException
import com.giraffe.media.exception.TimeoutException
import com.giraffe.media.exception.TooManyRequestsDataException
import com.giraffe.media.exception.UnauthorizedAccessDataException
import com.giraffe.media.exception.UnauthorizedException
import com.giraffe.media.exception.UnknownException
import com.giraffe.media.exception.UnknownNetworkDataException
import com.giraffe.media.exception.ValidationException
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

    fun mapToDomainException(e: Throwable): MediaException = when (e) {

        is ApiDataException -> when (e.code) {
            401  -> UnauthorizedException()
            405  -> InvalidRequestMethodException()
            403, 429 -> AccessDeniedException()
            400,406,422 -> ValidationException()
            404  -> NotFoundException()
            502, 503 -> NetworkException()
            500, 501 -> ServerErrorException()
            504  -> TimeoutException()
            else -> UnknownException()
        }


        is NoInternetDataException,
        is UnknownNetworkDataException,
        is UnknownHostException -> NetworkException()

        is RequestTimeoutDataException,
        is SocketTimeoutException -> TimeoutException()

        is ServerDataException -> ServerErrorException()

        is UnauthorizedAccessDataException,
        is InvalidApiKeyDataException -> UnauthorizedException()

        is ForbiddenAccessDataException,
        is TooManyRequestsDataException,
        is RateLimitExceededDataException -> AccessDeniedException()

        is NotFoundDataException -> NotFoundException()

        is InvalidIdDataException,
        is SerializationDataException,
        is IllegalArgumentException -> ValidationException()

        is RedirectedDataException,
        is ClientErrorDataException,
        is CorruptDatabaseDataException,
        is DiskAccessDataException -> InfrastructureException()


        else -> UnknownException()
    }
}
