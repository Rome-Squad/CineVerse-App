package com.giraffe.details.screens.recommended.movie

import androidx.navigation.NavController
import com.giraffe.details.navigation.MoviesRecommendedRoute

fun NavController.navigateToRecommendedMoviesScreen( movieId: Int,  title: String) {
    navigate(MoviesRecommendedRoute(movieId, title))
}