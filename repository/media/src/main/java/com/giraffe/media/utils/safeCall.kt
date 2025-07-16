package com.giraffe.media.utils


import com.giraffe.media.series.exception.ClientErrorException
import com.giraffe.media.series.exception.CorruptDatabaseException
import com.giraffe.media.series.exception.DiskAccessException
import com.giraffe.media.series.exception.NoInternetException
import com.giraffe.media.series.exception.NotFoundElementException
import com.giraffe.media.series.exception.RedirectedException
import com.giraffe.media.series.exception.SerializationException
import com.giraffe.media.series.exception.SeriesException
import com.giraffe.media.series.exception.ServerException
import com.giraffe.media.series.exception.TimeoutException
import com.giraffe.media.series.exception.UnknownException
import com.giraffe.media.series.exception.ValidationExceptions
import com.giraffe.media.series.exceptions.ClientException
import com.giraffe.media.series.exceptions.InvalidRequestException
import com.giraffe.media.series.exceptions.InvalidRequestMethodException
import com.giraffe.media.series.exceptions.NoInternetNetworkException
import com.giraffe.media.series.exceptions.RedirectException
import com.giraffe.media.series.exceptions.RequestTimeoutNetworkException
import com.giraffe.media.series.exceptions.SerializationNetworkException
import com.giraffe.media.series.exceptions.ServerNetworkException
import com.giraffe.media.series.exceptions.TooManyRequestsNetworkException
import com.giraffe.media.series.exceptions.UnknownNetworkException

suspend inline fun <reified T> safeCall(
    block: suspend () -> T
): T {
    return try {
        block()
    } catch (e: Throwable) {
        throw mapToDomainException(e)
    }
}

fun mapToDomainException(e: Throwable): SeriesException {
    return when (e) {
        is NoInternetNetworkException -> NoInternetException()
        is RequestTimeoutNetworkException -> TimeoutException()
        is TooManyRequestsNetworkException -> SeriesException()
        is ServerNetworkException -> ServerException()
        is RedirectException -> RedirectedException()
        is ClientException -> ClientErrorException()
        is SerializationNetworkException -> SerializationException()
        is InvalidRequestMethodException -> ValidationExceptions()
        is InvalidRequestException -> ValidationExceptions()
        is UnknownNetworkException -> UnknownException()
        is NoSuchElementException -> NotFoundElementException()
        is IllegalArgumentException -> ValidationExceptions()
        is CorruptDatabaseException -> SeriesException()
        is DiskAccessException -> SeriesException()
        else -> UnknownException()
    }


}