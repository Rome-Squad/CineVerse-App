package com.giraffe.presentation.details.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.exception.NotFoundException
import com.giraffe.media.exception.ValidationException
import com.giraffe.presentation.details.R


fun Context.showToast(@StringRes stringRes: Int) {
    Toast.makeText(
        this,
        this.getString(stringRes),
        Toast.LENGTH_SHORT
    ).show()
}