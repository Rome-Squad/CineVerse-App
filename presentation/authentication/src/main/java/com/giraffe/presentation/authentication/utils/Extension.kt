package com.giraffe.presentation.authentication.utils

import android.content.Context
import android.widget.Toast
import com.giraffe.presentation.authentication.R
import com.giraffe.user.exception.EmptyUsernameException
import com.giraffe.user.exception.InvalidPasswordException
import com.giraffe.user.exception.InvalidUsernameMatchException
import com.giraffe.user.exception.InvalidUsernameOrPasswordException

fun Throwable.toStringResource() = when (this) {
    is InvalidPasswordException -> R.string.invalid_password
    is EmptyUsernameException -> R.string.Empty_username
    is InvalidUsernameOrPasswordException -> R.string.invalid_username_or_password
    is InvalidUsernameMatchException -> R.string.invalid_username_format
    else -> R.string.unknown_error
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}