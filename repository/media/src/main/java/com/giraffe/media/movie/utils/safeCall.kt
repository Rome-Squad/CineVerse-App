package com.giraffe.media.movie.utils

import com.giraffe.media.movie.exceptions.NetworkException
import com.giraffe.media.movie.exceptions.NoInternetNetworkException
import com.giraffe.media.movie.exceptions.RequestTimeoutNetworkException
import com.giraffe.media.movie.exceptions.ServerNetworkException
import com.giraffe.media.movie.exceptions.TooManyRequestsNetworkException
import com.giraffe.media.movies.exception.MoviesException
import com.giraffe.media.movies.exception.NetworkError
import com.giraffe.media.movies.exception.NotFoundError
import com.giraffe.media.movies.exception.ServerError
import com.giraffe.media.movies.exception.UnknownError


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
        //is UnresolvedAddressException -> InvalidApiKey()
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