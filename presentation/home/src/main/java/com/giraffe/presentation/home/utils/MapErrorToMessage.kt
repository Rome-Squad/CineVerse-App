package com.giraffe.presentation.home.utils

import androidx.annotation.StringRes
import com.giraffe.media.exception.AccessDeniedException
import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.exception.NotFoundException
import com.giraffe.media.exception.UnknownException
import com.giraffe.media.exception.ValidationException
import com.giraffe.presentation.home.R
import com.giraffe.user.exception.FailedToGetUserName

@StringRes
fun Throwable.toStringRes() = when (this) {
    is AccessDeniedException -> R.string.error_access_denied
    is ValidationException -> R.string.error_validation
    is NotFoundException -> R.string.error_not_found
    is UnknownException -> R.string.error_unknown
    is NoInternetException -> R.string.error_network
    is FailedToGetUserName -> R.string.username_error
    else -> R.string.error_unknown
}