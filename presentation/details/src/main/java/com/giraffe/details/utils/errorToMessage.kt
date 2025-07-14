package com.giraffe.details.utils

import android.content.Context
import com.giraffe.details.R
import com.giraffe.movies.exception.InvalidApiKey
import com.giraffe.movies.exception.MoviesException
import com.giraffe.movies.exception.NetworkError
import com.giraffe.movies.exception.NotFoundError
import com.giraffe.movies.exception.ServerError
import com.giraffe.movies.exception.UnknownError

fun Context.errorToMessage(error: MoviesException): String = when (error) {
    is NetworkError -> getString(R.string.network_error)

    is InvalidApiKey -> getString(R.string.authentication_error)

    is NotFoundError -> getString(R.string.movie_not_found)

    is ServerError -> getString(R.string.server_error)

    is UnknownError -> getString(R.string.unknown_error)
    else -> getString(R.string.unknown_error)
}