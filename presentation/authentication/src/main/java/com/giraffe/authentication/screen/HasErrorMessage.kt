package com.giraffe.authentication.screen

import androidx.annotation.StringRes

interface HasErrorMessage<T> {
    fun withErrorMessage(@StringRes id: Int): T
}