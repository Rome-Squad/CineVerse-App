package com.giraffe.presentation.profile.utils

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.exception.NotFoundException
import com.giraffe.media.exception.ValidationException
import com.giraffe.presentation.profile.R
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number
import kotlinx.datetime.toJavaLocalDate
import java.text.NumberFormat
import java.time.format.TextStyle
import java.util.Locale

fun LocalDate?.toFormattedDate(): String {
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
        "$monthStr $dayStr, $yearStr"
    }
}


private fun String.toArabicDigits(): String {
    return this.map { char ->
        if (char.isDigit()) "٠١٢٣٤٥٦٧٨٩"[char.digitToInt()] else char
    }.joinToString("")
}

fun Int.toLocalized(): String {
    val locale = AppCompatDelegate.getApplicationLocales().get(0) ?: Locale.getDefault()
    val numberFormat = NumberFormat.getNumberInstance(locale)
    val formatted = numberFormat.format(this)
    return if (locale.language == "ar") formatted.toArabicDigits() else formatted
}

fun Float?.orZero() = this ?: 0f

fun Throwable.toStringResource() = when (this) {
    is NoInternetException -> R.string.error_network
    is AccessDeniedException -> R.string.access_denied_error
    is ValidationException -> if (this.message == "Collection name cannot be blank")
        R.string.collection_name_cannot_be_blank
    else
        R.string.validation_error

    is NotFoundException -> R.string.collection_not_found
    else -> R.string.error_unknown
}

fun Context.showToast(@StringRes stringRes: Int) {
    Toast.makeText(
        this,
        this.getString(stringRes),
        Toast.LENGTH_SHORT
    ).show()
}