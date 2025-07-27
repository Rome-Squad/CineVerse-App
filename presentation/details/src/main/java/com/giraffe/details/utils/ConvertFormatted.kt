package com.giraffe.details.utils

import java.text.DateFormatSymbols
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

    val monthSymbols = DateFormatSymbols(Locale.ENGLISH).apply {
        months = arrayOf(
            "Jan", "Feb", "Mar", "Apr",
            "May", "Jun", "Jul", "Aug",
            "Sep", "Oct", "Nov", "Dec"
        )
    }

    val outputFormat = SimpleDateFormat("yyyy, MMMM d", Locale.ENGLISH).apply {
        dateFormatSymbols = monthSymbols
    }
    return outputFormat.format(date)
}