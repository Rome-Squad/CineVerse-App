package com.giraffe.profile.screens.ratings

import androidx.annotation.StringRes

sealed class RatingEffect {
    data class ShowError(@param:StringRes val message: Int) : RatingEffect()
    data class NavigateToMovieDetails(val movieId: Int) : RatingEffect()
    data class NavigateToSeriesDetails(val seriesId: Int) : RatingEffect()
    object NavigateBack : RatingEffect()


}