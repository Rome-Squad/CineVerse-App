package com.giraffe.presentation.profile.screens.history

sealed class HistoryEffect {
    data class ShowError(val error: Throwable) : HistoryEffect()
    object NavigateToExploreScreen : HistoryEffect()
    object NavigateToBack : HistoryEffect()
    data class NavigateToProfileScreen(val id: Int) : HistoryEffect()
    data class NavigateToMovieDetails(val movieId: Int) : HistoryEffect()
    data class NavigateToSeriesDetails(val seriesId: Int) : HistoryEffect()
}