package com.giraffe.details.screens.moviedetails

sealed interface MovieDetailsEffect {

    object NavigateToCollection : MovieDetailsEffect

    object NavigateToLogin : MovieDetailsEffect

    object NavigateUp : MovieDetailsEffect

    data class NavigateToYouTubePlayer(val url: String) : MovieDetailsEffect

    data class NavigateToMovieDetails(val id: Int) : MovieDetailsEffect

    data class NavigateToReviews(val movieId: Int) : MovieDetailsEffect

    data class Error(val error: Throwable) : MovieDetailsEffect

    data class NavigateToCastDetails(val personId: Int) :
        MovieDetailsEffect

    data class NavigateToMoviesRecommended(val movieId: Int, val title: String) :
        MovieDetailsEffect

}