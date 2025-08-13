package com.giraffe.presentation.profile.screens.ratings

sealed class RatingEffect {
    data class ShowError(val error: Throwable) : RatingEffect()
    data class NavigateToMovieDetails(val movieId: Int) : RatingEffect()
    data class NavigateToSeriesDetails(val seriesId: Int) : RatingEffect()
    object NavigateToExplore : RatingEffect()
    object NavigateBack : RatingEffect()
}