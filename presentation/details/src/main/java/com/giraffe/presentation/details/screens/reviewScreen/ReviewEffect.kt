package com.giraffe.presentation.details.screens.reviewScreen

import androidx.annotation.StringRes

sealed class ReviewEffect {
    data class ShowError(val error: Throwable) : ReviewEffect()
}