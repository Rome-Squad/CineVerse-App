package com.giraffe.match.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.showToast(@StringRes stringRes: Int) {
    Toast.makeText(
        this,
        this.getString(stringRes),
        Toast.LENGTH_SHORT
    ).show()
}
