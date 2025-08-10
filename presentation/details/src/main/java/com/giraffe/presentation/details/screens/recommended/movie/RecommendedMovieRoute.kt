package com.giraffe.presentation.details.screens.recommended.movie

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
        RecommendedMoviesScreen(
            onBackClick = onBackClick,
            navigateToMovieDetails = navigateToMovieDetails,
        )
    }
}

