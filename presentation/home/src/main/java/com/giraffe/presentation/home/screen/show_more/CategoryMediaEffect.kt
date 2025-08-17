package com.giraffe.presentation.home.screen.show_more

sealed class CategoryMediaEffect {
    data class NavigateToMovieDetails(val movieId: Int) : CategoryMediaEffect()
    data class NavigateToSeriesDetails(val seriesId: Int) : CategoryMediaEffect()
    data object NavigateBack : CategoryMediaEffect()
    data class ShowError(val error: Throwable) : CategoryMediaEffect()
}