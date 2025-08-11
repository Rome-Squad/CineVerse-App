package com.giraffe.presentation.explore.screen.search

sealed class SearchEffect {
    data class ShowError(val error: Throwable) : SearchEffect()
    object NavigateBack : SearchEffect()
    data class NavigateToMovieDetail(val movieId: Int) : SearchEffect()
    data class NavigateToSeriesDetail(val seriesId: Int) : SearchEffect()
    data class NavigateToPersonDetails(val personId: Int) : SearchEffect()
    data class NavigateToSearchResult(val result: String) : SearchEffect()
}