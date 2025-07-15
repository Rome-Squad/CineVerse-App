package com.giraffe.media.explore.utils

import com.giraffe.explore.exceptions.ExploreException
import com.giraffe.explore.exceptions.NoInternetException
import com.giraffe.explore.exceptions.NoInternetNetworkException
import com.giraffe.explore.exceptions.NotFoundException
import com.giraffe.explore.exceptions.RequestTimeoutException
import com.giraffe.explore.exceptions.RequestTimeoutNetworkException
import com.giraffe.explore.exceptions.SerializationNetworkException
import com.giraffe.explore.exceptions.ServerException
import com.giraffe.explore.exceptions.ServerNetworkException
import com.giraffe.explore.exceptions.TooManyRequestsException
import com.giraffe.explore.exceptions.TooManyRequestsNetworkException
import com.giraffe.explore.exceptions.UnknownException
import com.giraffe.explore.exceptions.UnrecognizableDataException
import com.giraffe.explore.exceptions.ValidationException
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException


suspend inline fun <reified T> safeCall(
    block: suspend () -> T
): T {
    return try {
        block()
    } catch (e: Throwable) {
        throw mapToLocalDatabaseException(e)
    }
}


fun mapToLocalDatabaseException(e: Throwable): ExploreException {
    return when (e) {
        //network exceptions
        is NoInternetNetworkException -> NoInternetException()
        is UnresolvedAddressException -> NoInternetException()
        is RequestTimeoutNetworkException -> RequestTimeoutException()
        is TooManyRequestsNetworkException -> TooManyRequestsException()
        is ServerNetworkException -> ServerException()

        //serialization
        is SerializationNetworkException -> UnrecognizableDataException()
        is SerializationException -> UnrecognizableDataException()

        //local
        is NoSuchElementException -> NotFoundException()
        is IllegalArgumentException -> ValidationException()

        //unknown
        else -> UnknownException()
    }
}