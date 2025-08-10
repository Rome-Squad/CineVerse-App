package com.giraffe.presentation.profile.utils

import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.exception.NotFoundException
import com.giraffe.media.exception.ValidationException
import com.giraffe.presentation.profile.R

fun Throwable.toStringResource() = when (this) {
    is NoInternetException -> R.string.error_network
    is AccessDeniedException -> R.string.access_denied_error
    is ValidationException -> if (this.message == "Collection name cannot be blank")
        R.string.collection_name_cannot_be_blank
    else
        R.string.validation_error

    is NotFoundException -> R.string.collection_not_found
    else -> R.string.error_unknown
}