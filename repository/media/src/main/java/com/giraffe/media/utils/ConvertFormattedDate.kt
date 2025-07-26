package com.giraffe.media.utils

import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Locale

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