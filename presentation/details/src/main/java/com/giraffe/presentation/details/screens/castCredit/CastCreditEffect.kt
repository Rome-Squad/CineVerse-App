package com.giraffe.presentation.details.screens.castCredit

sealed interface CastCreditEffect {
    data class Error(val error: Throwable) : CastCreditEffect
    data class NavigateToSeriesDetails(val seriesId: Int) : CastCreditEffect
    data class NavigateToMovieDetails(val movieId: Int) : CastCreditEffect
    object NavigateBack : CastCreditEffect
}