package com.giraffe.presentation.profile.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.exception.NotFoundException
import com.giraffe.media.exception.ValidationException
import com.giraffe.presentation.profile.R
import kotlinx.datetime.LocalDate
import java.text.SimpleDateFormat
import java.util.Locale

fun LocalDate?.toFormattedDate(): String {
    return this?.let {
        val dateString = it.toString()
        val inputFormat = SimpleDateFormat("yyyy-M-dd", Locale.ENGLISH)
        val date = inputFormat.parse(dateString) ?: return dateString

        val outputFormat = SimpleDateFormat("yyyy, MMM d", Locale.ENGLISH)
        return outputFormat.format(date)
    } ?: ""
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