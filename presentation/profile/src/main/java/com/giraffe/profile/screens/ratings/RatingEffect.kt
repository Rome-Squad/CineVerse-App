package com.giraffe.profile.screens.ratings

sealed class RatingEffect {
    data class NavigateToDetails(val id: Int) : RatingEffect()
    object NavigateBack : RatingEffect()
}