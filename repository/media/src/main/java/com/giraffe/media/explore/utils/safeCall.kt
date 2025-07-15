package com.giraffe.media.explore.utils

import com.giraffe.media.explore.exceptions.ExploreException
import com.giraffe.media.explore.exceptions.NoInternetException
import com.giraffe.media.explore.exceptions.NoInternetNetworkException
import com.giraffe.media.explore.exceptions.NotFoundException
import com.giraffe.media.explore.exceptions.RequestTimeoutException
import com.giraffe.media.explore.exceptions.RequestTimeoutNetworkException
import com.giraffe.media.explore.exceptions.SerializationNetworkException
import com.giraffe.media.explore.exceptions.ServerException
import com.giraffe.media.explore.exceptions.ServerNetworkException
import com.giraffe.media.explore.exceptions.TooManyRequestsException
import com.giraffe.media.explore.exceptions.TooManyRequestsNetworkException
import com.giraffe.media.explore.exceptions.UnknownException
import com.giraffe.media.explore.exceptions.UnrecognizableDataException
import com.giraffe.media.explore.exceptions.ValidationException
import kotlinx.serialization.SerializationException
import java.nio.channels.UnresolvedAddressException


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