package com.giraffe.presentation.home.screen.show_more

sealed class ShowMoreEffect {
    data class NavigateToMovieDetails(val movieId: Int) : ShowMoreEffect()
    data class NavigateToSeriesDetails(val seriesId: Int) : ShowMoreEffect()
    data object NavigateBack : ShowMoreEffect()
    data class ShowError(val error: Throwable) : ShowMoreEffect()
}