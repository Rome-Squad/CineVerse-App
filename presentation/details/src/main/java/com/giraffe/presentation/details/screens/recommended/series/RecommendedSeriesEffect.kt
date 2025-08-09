package com.giraffe.presentation.details.screens.recommended.series


interface RecommendedSeriesEffect {
    data class Error(val error: Throwable) : RecommendedSeriesEffect
    data class NavigateToSeriesDetails(val seriesId: Int) : RecommendedSeriesEffect
}