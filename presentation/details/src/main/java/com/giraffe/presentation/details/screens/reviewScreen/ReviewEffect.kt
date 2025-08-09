package com.giraffe.presentation.details.screens.reviewScreen

import androidx.annotation.StringRes

sealed class ReviewEffect {
    data class ShowError(@param:StringRes val messageResId: Int) : ReviewEffect()
}