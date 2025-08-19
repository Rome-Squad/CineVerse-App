package com.giraffe.presentation.home.utils

import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number
import kotlinx.datetime.toJavaLocalDate
import java.time.format.TextStyle
import java.util.Locale

fun LocalDate?.formatDate(): String {
    if (this == null) return ""

    val locale = AppCompatDelegate.getApplicationLocales().get(0) ?: Locale.getDefault()

    val dayStr = this.day.toString()
    val monthStr = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        this.toJavaLocalDate().month.getDisplayName(TextStyle.SHORT, locale)
    } else {
        this.month.number.toString()
    }
    val yearStr = this.year.toString()

    return if (locale.language == "ar") {
        "${dayStr.toArabicDigits()} $monthStr ${yearStr.toArabicDigits()}"
    } else {
        "$yearStr, $monthStr $dayStr"
    }
}

private fun String.toArabicDigits(): String {
    return this.map { char ->
        if (char.isDigit()) "٠١٢٣٤٥٦٧٨٩"[char.digitToInt()] else char
    }.joinToString("")
}
