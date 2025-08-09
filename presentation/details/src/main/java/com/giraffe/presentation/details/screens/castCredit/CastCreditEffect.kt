package com.giraffe.presentation.details.screens.castCredit

interface CastCreditEffect {
    data class Error(val error: Throwable) : CastCreditEffect
    data class NavigateToSeriesDetails(val seriesId: Int) : CastCreditEffect
    data class NavigateToMovieDetails(val movieId: Int) : CastCreditEffect
}