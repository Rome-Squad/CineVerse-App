package com.giraffe.presentation.details.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
internal data class RecommendedMovieRoute(val movieId: Int, val title: String)

internal fun NavController.navigateToRecommendedMoviesScreen(movieId: Int, title: String) {
    navigate(RecommendedMovieRoute(movieId, title))
}

internal fun NavGraphBuilder.recommendedMoviesRoute(
    onBackClick: () -> Unit,
    navigateToMovieDetails: (Int) -> Unit
) {
    composable<RecommendedMovieRoute> {
        _root_ide_package_.com.giraffe.presentation.details.screens.recommended.movie.RecommendedMoviesScreen(
            onBackClick = onBackClick,
            navigateToMovieDetails = navigateToMovieDetails,
        )
    }
}

