package com.giraffe.presentation.profile.utils

fun Any?.orEmpty(): String = this?.toString() ?: ""

fun Float?.orEmpty() = this ?: 0f
