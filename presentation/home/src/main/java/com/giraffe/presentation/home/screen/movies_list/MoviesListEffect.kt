package com.giraffe.presentation.home.screen.movies_list

import androidx.annotation.StringRes

sealed class MoviesListEffect {
    data class NavigateToMovieDetails(val movieId: Int) : MoviesListEffect()
    data class NavigateToSeriesDetails(val seriesId: Int) : MoviesListEffect()
    data class ShowError(@StringRes val messageResId: Int) : MoviesListEffect()
}