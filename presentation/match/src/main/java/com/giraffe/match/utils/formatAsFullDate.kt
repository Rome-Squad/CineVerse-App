package com.giraffe.match.utils

import android.os.Build
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number
import kotlinx.datetime.toJavaLocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalDate?.formatAsFullDate(): String {
    if (this == null) return ""

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
        this.toJavaLocalDate().format(formatter)
    } else {
        "%04d-%02d-%02d".format(this.year, this.month.number, this.day)
    }
}


