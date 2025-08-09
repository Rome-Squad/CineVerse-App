package com.giraffe.presentation.details.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun Int.toFormattedDuration(): String {
    val hours = this / 60
    val minutes = this % 60
    return buildString {
        if (hours > 0) append("${hours}h ")
        if (minutes > 0 || hours == 0) append("${minutes}m")
    }.trim()
}

fun String.toFormattedDate(): String {
    if (this.isBlank()) return this
    val inputFormat = SimpleDateFormat("yyyy-M-dd", Locale.ENGLISH)
    val date = inputFormat.parse(this) ?: return this


    val outputFormat = SimpleDateFormat("yyyy, MMM d", Locale.ENGLISH)
    return outputFormat.format(date)
}