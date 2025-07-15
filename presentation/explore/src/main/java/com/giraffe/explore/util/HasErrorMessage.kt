package com.giraffe.media.explore.util

import androidx.annotation.StringRes

interface HasErrorMessage<T> {
    fun withErrorMessage(@StringRes id: Int): T
}