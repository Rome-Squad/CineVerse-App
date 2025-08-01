package com.giraffe.details.screens.reviewScreen

sealed class ReviewEffect {
    data class ShowError(val message: String) : ReviewEffect()
}