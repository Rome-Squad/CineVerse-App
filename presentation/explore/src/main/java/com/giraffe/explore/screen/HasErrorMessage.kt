package com.giraffe.explore.screen

import androidx.annotation.StringRes


interface HasErrorMessage<T> {
    fun withErrorMessage(@StringRes id: Int): T
}
