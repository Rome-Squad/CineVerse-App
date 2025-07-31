package com.giraffe.details.utils

import androidx.annotation.StringRes
import com.giraffe.details.R

enum class TypeOfScreen(@StringRes val titleResId: Int) {
    MOVIE(R.string.type_movie),
    SERIES(R.string.type_series),
    CAST(R.string.type_cast)
}