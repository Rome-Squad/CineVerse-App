package com.giraffe.presentation.home.utils

import androidx.appcompat.app.AppCompatDelegate
import java.text.NumberFormat
import java.util.Locale

private fun String.toArabicDigits(): String {
    val arabicDigits = listOf('٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩')
    return this.map { char ->
        if (char.isDigit()) arabicDigits[char.digitToInt()]
        else if (char == '٫') '.'
        else char
    }.joinToString("")
}

fun Float.toLocalizedRating(): String {
    val locale = AppCompatDelegate.getApplicationLocales().get(0) ?: Locale.getDefault()
    val numberFormat = NumberFormat.getNumberInstance(locale).apply {
        maximumFractionDigits = 1
        minimumFractionDigits = 1
    }
    val formatted = numberFormat.format(this)
    return if (locale.language == "ar") formatted.toArabicDigits() else formatted
}