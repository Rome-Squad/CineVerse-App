package com.giraffe.details.utils

import android.content.Context
import com.giraffe.details.R
import com.giraffe.media.exception.*

fun Context.errorToMessage(error: MediaDomainException): String {
    return when (error) {
        is NetworkDomainException -> getString(R.string.network_error)
        is InvalidRequestMethodDomainException -> getString(R.string.authentication_error)
        is NotFoundDomainException -> getString(R.string.movie_not_found)
        is ServerErrorDomainException -> getString(R.string.server_error)
        is UnknownDomainException -> getString(R.string.unknown_error)
        else -> getString(R.string.unknown_error)
    }
}
