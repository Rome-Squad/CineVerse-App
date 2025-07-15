package com.giraffe.explore.util

import androidx.annotation.StringRes

interface HasErrorMessage<T> {
    fun withErrorMessage(@StringRes id: Int): T
}