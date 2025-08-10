package com.giraffe.presentation.details.utils

import kotlinx.datetime.LocalDate
import java.text.SimpleDateFormat
import java.util.Locale

fun Int?.toFormattedDuration(): String {
    if (this == null || this < 0) return ""
    val hours = this / 60
    val minutes = this % 60
    return buildString {
        if (hours > 0) append("${hours}h ")
        if (minutes > 0 || hours == 0) append("${minutes}m")
    }.trim()
}

fun LocalDate?.toFormattedDate(): String {
    if (this == null) return ""

    val dateString = this.toString()
    val inputFormat = SimpleDateFormat("yyyy-M-dd", Locale.ENGLISH)
    val date = inputFormat.parse(dateString) ?: return dateString

    val outputFormat = SimpleDateFormat("yyyy, MMM d", Locale.ENGLISH)
    return outputFormat.format(date)
}