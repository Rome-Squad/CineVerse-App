package com.giraffe.presentation.explore.screen.searchresult


sealed class SearchResultEffect {
    data class ShowError(val error: Throwable) : SearchResultEffect()
    object NavigateBack : SearchResultEffect()
    data class NavigateToMovieDetail(val movieId: Int) : SearchResultEffect()
    data class NavigateToSeriesDetail(val seriesId: Int) : SearchResultEffect()
    data class NavigateToCastDetails(val personId: Int) : SearchResultEffect()
    object NavigateToSearchScreen : SearchResultEffect()
}