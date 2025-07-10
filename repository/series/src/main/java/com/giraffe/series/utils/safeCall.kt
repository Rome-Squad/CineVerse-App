package com.giraffe.series.utils


import com.giraffe.series.exception.ClientErrorException
import com.giraffe.series.exception.CorruptDatabaseException
import com.giraffe.series.exception.DiskAccessException
import com.giraffe.series.exception.NoInternetException
import com.giraffe.series.exception.NotFoundElementException
import com.giraffe.series.exception.RedirectedException
import com.giraffe.series.exception.SeriesException
import com.giraffe.series.exception.ServerException
import com.giraffe.series.exception.TimeoutException
import com.giraffe.series.exception.UnknownException
import com.giraffe.series.exception.ValidationExceptions
import com.giraffe.series.exceptions.ClientException
import com.giraffe.series.exceptions.NoInternetNetworkException
import com.giraffe.series.exceptions.RedirectException
import com.giraffe.series.exceptions.RequestTimeoutNetworkException
import com.giraffe.series.exceptions.ServerNetworkException
import com.giraffe.series.exceptions.TooManyRequestsNetworkException
import kotlin.NoSuchElementException

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
        is ClientException ->ClientErrorException()



        is NoSuchElementException -> NotFoundElementException()
        is IllegalArgumentException -> ValidationExceptions()
        is CorruptDatabaseException -> SeriesException()
        is DiskAccessException ->SeriesException()
        else -> UnknownException()




    }



}