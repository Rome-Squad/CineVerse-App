package com.giraffe.presentation.details.utils

import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number
import kotlinx.datetime.toJavaLocalDate
import java.time.format.TextStyle
import java.util.Locale

fun Int?.toFormattedDuration(locale: Locale = Locale.getDefault()): String {
    if (this == null || this <= 0) return ""
    val hours = this / 60
    val minutes = this % 60

    val formatted = buildString {
        if (hours > 0) append("${hours} ${if (locale.language == "ar") "ساعة" else "h"} ")
        if (minutes > 0 || hours == 0) append("${minutes} ${if (locale.language == "ar") "دقيقة" else "m"}")
    }.trim()

    return if (locale.language == "ar") formatted.map { c ->
        if (c.isDigit()) "٠١٢٣٤٥٦٧٨٩"[c - '0'] else c
    }.joinToString("") else formatted
}

fun LocalDate?.toFormattedDate(): String {
    if (this == null) return ""

    val locale = AppCompatDelegate.getApplicationLocales().get(0) ?: Locale.getDefault()

    val dayStr = this.day.toString()
    val monthStr = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        this.toJavaLocalDate().month.getDisplayName(TextStyle.FULL, locale)
    } else {
        this.month.number.toString()
    }
    val yearStr = this.year.toString()

    return if (locale.language == "ar") {
        "${yearStr.toArabicDigits()}, $monthStr ${dayStr.toArabicDigits()}"
    } else {
        "$yearStr, $monthStr $dayStr"
    }
}

private fun String.toArabicDigits(): String {
    return this.map { char ->
        if (char.isDigit()) "٠١٢٣٤٥٦٧٨٩"[char.digitToInt()] else char
    }.joinToString("")
}