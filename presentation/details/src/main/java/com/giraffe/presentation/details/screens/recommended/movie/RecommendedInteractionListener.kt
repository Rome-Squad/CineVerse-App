package com.giraffe.presentation.details.screens.recommended.movie

interface RecommendedInteractionListener {
    fun onMovieClick(movieId: Int)

    fun onBackClick()

    fun onRetryClick()

}
