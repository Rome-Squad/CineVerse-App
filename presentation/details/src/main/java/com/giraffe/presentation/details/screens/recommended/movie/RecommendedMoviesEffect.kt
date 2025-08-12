package com.giraffe.presentation.details.screens.recommended.movie

sealed interface RecommendedMoviesEffect {
    data class NavigateToMovieDetails(val movieId: Int) : RecommendedMoviesEffect
    data class ShowError(val error: Throwable) : RecommendedMoviesEffect
    object NavigateBack : RecommendedMoviesEffect
}
