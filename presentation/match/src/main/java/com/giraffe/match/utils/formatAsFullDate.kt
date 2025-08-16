package com.giraffe.match.utils

import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number
import kotlinx.datetime.toJavaLocalDate
import java.time.format.TextStyle
import java.util.Locale

fun LocalDate?.formatAsFullDate(): String {
    if (this == null) return ""

    val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        AppCompatDelegate.getApplicationLocales()[0] ?: Locale.getDefault()
    } else {
        Locale.getDefault()
    }

    val isArabic = locale.language == "ar"

    val dayStr = if (isArabic) this.day.toString().map { c ->
        if (c.isDigit()) "٠١٢٣٤٥٦٧٨٩"[c - '0'] else c
    }.joinToString("") else this.day.toString()

    val monthStr = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        this.toJavaLocalDate().month.getDisplayName(TextStyle.FULL, locale)
    } else {
        this.month.number.toString()
    }

    val yearStr = if (isArabic) this.year.toString().map { c ->
        if (c.isDigit()) "٠١٢٣٤٥٦٧٨٩"[c - '0'] else c
    }.joinToString("") else this.year.toString()

    return "$dayStr $monthStr $yearStr"
}
