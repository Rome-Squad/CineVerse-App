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
import com.giraffe.media.exception.NotFoundException
import com.giraffe.media.exception.NotFoundDataException
import com.giraffe.media.exception.RateLimitExceededDataException
import com.giraffe.media.exception.RedirectedDataException
import com.giraffe.media.exception.RequestTimeoutDataException
import com.giraffe.media.exception.SerializationDataException
import com.giraffe.media.exception.ServerErrorException
import com.giraffe.media.exception.ServerDataException
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
            3, 7, 10, 14, 16, 17, 30, 31, 32, 33, 35, 36 -> UnauthorizedException()
            4, 42 -> InvalidRequestMethodException()
            8, 38, 39, 45, 25 -> AccessDeniedException()
            5, 18, 20, 22, 23, 26, 27, 28, 29, 41, 47 -> ValidationException()
            6, 34, 37 -> NotFoundException()
            2, 9, 43, 46 -> NetworkException()
            11, 15, 44 -> ServerErrorException()
            24 -> TimeoutException()
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
