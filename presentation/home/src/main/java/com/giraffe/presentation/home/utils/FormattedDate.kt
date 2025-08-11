package com.giraffe.presentation.home.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun String.toFormattedDate(): String {
    if (this.isBlank()) return this
    val inputFormat = SimpleDateFormat("yyyy-M-dd", Locale.ENGLISH)
    val date = inputFormat.parse(this) ?: return this


    val outputFormat = SimpleDateFormat("yyyy, MMM d", Locale.ENGLISH)
    return outputFormat.format(date)
}