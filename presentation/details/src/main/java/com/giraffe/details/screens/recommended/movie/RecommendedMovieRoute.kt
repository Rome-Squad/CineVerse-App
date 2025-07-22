package com.giraffe.details.screens.recommended.movie

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

fun NavController.navigateToRecommendedMoviesScreen(movieId: Int, title: String) {
    navigate("recommended_movies/$movieId/$title")
}


fun NavGraphBuilder.recommendedMoviesRoute(
    onBackClick: () -> Unit,
    onMovieClick: (Int) -> Unit
) {
    composable(
        route = "recommended_movies/{movieId}/{title}",
        arguments = listOf(
            navArgument("movieId") { type = NavType.IntType },
            navArgument("title") { type = NavType.StringType }
        )
    ) {
        RecommendedMoviesScreen(
            onBackClick = onBackClick,
            onMovieClick = onMovieClick
        )
    }
}


