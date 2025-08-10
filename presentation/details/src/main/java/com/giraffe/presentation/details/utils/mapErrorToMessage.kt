package com.giraffe.presentation.details.utils

import androidx.annotation.StringRes
import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.exception.NotFoundException
import com.giraffe.media.exception.ValidationException
import com.giraffe.presentation.details.R

@StringRes
fun mapErrorToMessageStringResource(error: Throwable) = when (error) {
    is NoInternetException -> R.string.error_network
    is AccessDeniedException -> R.string.access_denied_error
    is ValidationException -> if (error.message == "Collection name cannot be blank")
        R.string.collection_name_cannot_be_blank
    else
        R.string.validation_error

    is NotFoundException -> R.string.collection_not_found
    else -> R.string.error_unknown
}