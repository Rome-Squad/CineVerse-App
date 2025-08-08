package com.giraffe.details.screens.recommended.movie

sealed interface RecommendedEffectMovie {
    data class NavigateToMovieDetails(val movieId: Int) : RecommendedEffectMovie
}
