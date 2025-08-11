package com.giraffe.presentation.home.navigation.home.routes

import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
internal data class MovieDetailsRoute(val movieId: Int)

internal fun NavController.navigateToMovieDetails(movieId: Int) {
    navigate(MovieDetailsRoute(movieId))
}
