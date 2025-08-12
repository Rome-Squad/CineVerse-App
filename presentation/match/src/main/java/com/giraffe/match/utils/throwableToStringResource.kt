package com.giraffe.match.utils

import com.giraffe.media.exception.NoInternetException
import com.giraffe.presentation.match.R


fun Throwable.toStringResource() = when (this) {
    is NoInternetException -> R.string.error_network
    is AccessDeniedException -> R.string.access_denied_error
    else -> R.string.error_unknown
}
