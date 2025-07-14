package com.giraffe.details.screens.moviedetails

sealed interface MovieDetailsEffect {

    object NavigateToCollection : MovieDetailsEffect

    object NavigateToLogin : MovieDetailsEffect

    object NavigateToMovies : MovieDetailsEffect

    data class Error(val error: Throwable) : MovieDetailsEffect

}