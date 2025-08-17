package com.giraffe.match.utils

import java.util.Locale

private val arabicDigits = listOf('٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩')

fun Int?.toDurationString(locale: Locale = Locale.getDefault()): String {
    if (this == null || this <= 0) return ""
    val hours = this / 60
    val minutes = this % 60

    val formatted = buildString {
        if (hours > 0) append("${hours} ${if (locale.language == "ar") "ساعة" else "h"} ")
        if (minutes > 0 || hours == 0) append("${minutes} ${if (locale.language == "ar") "دقيقة" else "m"}")
    }.trim()

    return if (locale.language == "ar") formatted.map { c ->
        if (c.isDigit()) arabicDigits[c - '0'] else c
    }.joinToString("") else formatted
}


fun Int?.toSeasonsString(locale: Locale = Locale.getDefault()): String {
    if (this == null || this <= 0) return ""
    val numberStr = if (locale.language == "ar") this.toString().map { c ->
        if (c.isDigit()) arabicDigits[c - '0'] else c
    }.joinToString("") else this.toString()

    return if (locale.language == "ar") "$numberStr  مواسم"
    else "$numberStr Season${if (this > 1) "s" else ""}"
}
