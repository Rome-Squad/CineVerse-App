package com.giraffe.authentication.validation

import android.content.Context
import com.giraffe.authentication.R

interface ValidationError

object InvalidEmail : ValidationError
object InvalidPassword : ValidationError


fun ValidationError.getMessage(context: Context): String {
    return when (this) {
        InvalidEmail -> context.getString(R.string.invalid_email_address)
        InvalidPassword -> context.getString(R.string.invalid_password_minimum_8_characters)
        else -> "Unknown Error"
    }
}
