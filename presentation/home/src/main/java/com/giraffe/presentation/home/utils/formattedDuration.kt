package com.giraffe.presentation.home.utils

import java.util.Locale

fun Int?.formatDuration(locale: Locale = Locale.getDefault()): String {
    if (this == null || this <= 0) return ""
    val hours = this / 60
    val minutes = this % 60

    val formatted = buildString {
        if (hours > 0) append("$hours ${if (locale.language == "ar") "ساعة" else "h"} ")
        if (minutes > 0 || hours == 0) append("$minutes ${if (locale.language == "ar") "دقيقة" else "m"}")
    }.trim()

    return if (locale.language == "ar") formatted.map { c ->
        if (c.isDigit()) "٠١٢٣٤٥٦٧٨٩"[c - '0'] else c
    }.joinToString("") else formatted
}
