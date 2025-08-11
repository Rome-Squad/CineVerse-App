package com.giraffe.presentation.details.screens.recommended.series


sealed interface RecommendedSeriesEffect {
    data class Error(val error: Throwable) : RecommendedSeriesEffect
    data class NavigateToSeriesDetails(val seriesId: Int) : RecommendedSeriesEffect
    object NavigateBack : RecommendedSeriesEffect
}