package com.giraffe.match.utils

import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.exception.NotFoundException
import com.giraffe.media.exception.ValidationException
import com.giraffe.presentation.match.R


fun Throwable.toStringResource() = when (this) {
    is NoInternetException -> R.string.error_network
    is AccessDeniedException -> R.string.access_denied_error
    is NotFoundException -> R.string.error_not_found
    is ValidationException -> R.string.error_validation
    else -> R.string.error_unknown
}
