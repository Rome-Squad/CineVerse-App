package com.giraffe.presentation.explore.screen.discover

sealed class DiscoverEffect {
    data class ShowError(val error: Throwable) : DiscoverEffect()
    data class NavigateToMovieDetails(val movieId: Int) : DiscoverEffect()
    data class NavigateToSeriesDetails(val seriesId: Int) : DiscoverEffect()
    object NavigateToSearchScreen : DiscoverEffect()
}