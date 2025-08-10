package com.giraffe.presentation.explore.screen.searchresult


sealed class SearchResultEffect {
    data class Error(val error: Throwable) : SearchResultEffect()
    object OnBackClick : SearchResultEffect()
    data class NavigateToMovieDetail(val movieId: Int) : SearchResultEffect()
    data class NavigateToSeriesDetail(val seriesId: Int) : SearchResultEffect()
    data class NavigateToCastDetails(val personId: Int) : SearchResultEffect()
}