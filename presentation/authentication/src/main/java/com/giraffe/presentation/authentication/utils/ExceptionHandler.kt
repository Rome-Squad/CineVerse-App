package com.giraffe.presentation.authentication.utils


import androidx.annotation.StringRes
import com.giraffe.presentation.authentication.R
import com.giraffe.user.exception.*
@StringRes
fun mapExceptionToStringRes(throwable: Throwable): Int {
    return when (throwable) {
        is InvalidPasswordException -> R.string.invalid_password
        is EmptyUsernameException -> R.string.Empty_username
        is InvalidUsernameOrPasswordException -> R.string.invalid_username_or_password
        is InvalidUsernameMatchException -> R.string.invalid_username_format
        else -> R.string.unknown_error
    }
}
