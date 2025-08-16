package com.giraffe.media.utils

import kotlinx.datetime.LocalDate

fun Float?.orZero() = this ?: 0f

fun LocalDate?.orEmptyString(): String = this?.toString() ?: ""
