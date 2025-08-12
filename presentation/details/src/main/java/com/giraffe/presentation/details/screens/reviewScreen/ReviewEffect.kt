package com.giraffe.presentation.details.screens.reviewScreen

sealed class ReviewEffect {
    data class ShowError(val error: Throwable) : ReviewEffect()
    object NavigateBack : ReviewEffect()
}