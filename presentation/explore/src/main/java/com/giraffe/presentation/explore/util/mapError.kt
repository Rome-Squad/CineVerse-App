package com.giraffe.presentation.explore.util

import androidx.annotation.StringRes
import com.giraffe.media.exception.AccessDeniedException
import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.exception.NotFoundException
import com.giraffe.media.exception.UnknownException
import com.giraffe.media.exception.ValidationException
import com.giraffe.presentation.explore.R

@StringRes
fun mapExceptionToStringRes(throwable: Throwable) = when (throwable) {
    is NoInternetException -> R.string.error_network
    is AccessDeniedException -> R.string.error_access_denied
    is ValidationException -> R.string.error_validation
    is NotFoundException -> R.string.error_not_found
    is UnknownException -> R.string.error_unknown
    else -> R.string.error_unknown
}