package com.giraffe.movie.utils

import com.giraffe.movie.exceptions.NetworkException
import com.giraffe.movie.exceptions.NoInternetNetworkException
import com.giraffe.movie.exceptions.RequestTimeoutNetworkException
import com.giraffe.movie.exceptions.ServerNetworkException
import com.giraffe.movie.exceptions.TooManyRequestsNetworkException
import com.giraffe.movies.exception.InvalidApiKey
import com.giraffe.movies.exception.MoviesException
import com.giraffe.movies.exception.NetworkError
import com.giraffe.movies.exception.NotFoundError
import com.giraffe.movies.exception.ServerError
import com.giraffe.movies.exception.UnknownError
import io.ktor.util.network.UnresolvedAddressException


suspend inline fun <reified T> safeCall(
    block: suspend () -> T
): T {
    return try {
        block()
    } catch (e: Throwable) {
        throw mapToMoviesException(e)
    }
}


fun mapToMoviesException(e: Throwable): MoviesException {
    return when (e) {
        //network exceptions
        is NoInternetNetworkException -> ServerError()
        is UnresolvedAddressException -> InvalidApiKey()
        is RequestTimeoutNetworkException -> ServerError()
        is TooManyRequestsNetworkException -> ServerError()
        is ServerNetworkException -> ServerError()
        is NetworkException -> NetworkError()

        //local
        is NoSuchElementException -> NotFoundError()

        //unknown
        else -> UnknownError()
    }
}