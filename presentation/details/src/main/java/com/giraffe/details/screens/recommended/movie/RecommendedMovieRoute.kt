package com.giraffe.details.screens.recommended.movie

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.giraffe.details.screens.recommended.movie.utils.RecommendedMovieArgs

fun NavController.navigateToRecommendedMoviesScreen(args: RecommendedMovieArgs) {
    navigate(args.toRoute())
}


fun NavGraphBuilder.recommendedMoviesRoute(
    onBackClick: () -> Unit,
    onMovieClick: (Int) -> Unit
) {
    composable(
        route = RecommendedMovieArgs.fullRoute,
        arguments = listOf(
            navArgument(RecommendedMovieArgs.MOVIE_ID) { type = NavType.IntType },
            navArgument(RecommendedMovieArgs.TITLE) { type = NavType.StringType }
        )
    ) {
        RecommendedMoviesScreen(
            onBackClick = onBackClick,
            onMovieClick = onMovieClick
        )
    }
}



