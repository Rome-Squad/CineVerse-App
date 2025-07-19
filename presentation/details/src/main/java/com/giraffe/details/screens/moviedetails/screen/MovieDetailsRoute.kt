package com.giraffe.details.screens.moviedetails.screen

import androidx.navigation.NavController
import com.giraffe.details.navigation.MovieDetailsRoute

fun NavController.navigateToMovieDetails(movieId: Int) {
    navigate(MovieDetailsRoute(movieId = movieId))
}