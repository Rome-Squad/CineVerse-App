package com.giraffe.details.screens.moviedetails

import com.giraffe.details.models.ReviewUI


sealed interface MovieDetailsEffect {

    object NavigateToCollection : MovieDetailsEffect

    object NavigateToLogin : MovieDetailsEffect

    object NavigateToMovies : MovieDetailsEffect

    data class NavigateToReviews(val movieId: Int) : MovieDetailsEffect

    data class Error(val error: Throwable) : MovieDetailsEffect

    data class NavigateToCastDetails(val personId: Int) :
        MovieDetailsEffect

    data class NavigateToMoviesRecommended(val movieId: Int, val title: String) :
        MovieDetailsEffect

}