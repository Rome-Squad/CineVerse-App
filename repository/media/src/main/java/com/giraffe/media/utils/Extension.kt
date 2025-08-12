package com.giraffe.media.utils

fun Float?.orEmpty() = this ?: 0f

fun Any?.orEmpty(): String = this?.toString() ?: ""
