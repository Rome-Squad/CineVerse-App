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

    val locale = AppCompatDelegate.getApplicationLocales().get(0) ?: Locale.getDefault()

    val dayStr = String.format(Locale.getDefault(), "%d", this.day)
    val monthStr = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        this.toJavaLocalDate().month.getDisplayName(TextStyle.FULL, locale)
    } else {
        this.month.number.toString()
    }
    val yearStr = String.format(Locale.getDefault(), "%d", this.year)


    return "$dayStr $monthStr $yearStr"
}


