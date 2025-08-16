package com.giraffe.match.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.number
import java.util.Locale


private val arabicDigits = listOf('٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩')
private val arabicMonths = listOf(
    "يناير", "فبراير", "مارس", "أبريل", "مايو", "يونيو",
    "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمبر"
)
private val englishMonths = listOf(
    "Jan", "Feb", "Mar", "Apr", "May", "Jun",
    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
)

fun LocalDate?.formatAsFullDate(): String {
    if (this == null) return ""

    val locale = Locale.getDefault()
    val isArabic = locale.language == "ar"

    val dayStr = if (isArabic) this.day.toString().map { c ->
        if (c.isDigit()) arabicDigits[c - '0'] else c
    }.joinToString("") else this.day.toString()

    val monthStr =
        if (isArabic) arabicMonths[this.month.number - 1] else englishMonths[this.month.number - 1]

    val yearStr = if (isArabic) this.year.toString().map { c ->
        if (c.isDigit()) arabicDigits[c - '0'] else c
    }.joinToString("") else this.year.toString()

    return "$dayStr $monthStr $yearStr"
}


