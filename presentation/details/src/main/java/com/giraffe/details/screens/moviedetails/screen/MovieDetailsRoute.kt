package com.giraffe.details.screens.moviedetails.screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.giraffe.details.models.ReviewUI

const val MOVIES_ROUTE = "movieDetails"
private const val MOVIE_ID_ARG = "movieID"

fun NavController.navigateToMovieDetails(movieID: Int) {
    navigate("$MOVIES_ROUTE/$movieID")
}

fun NavGraphBuilder.movieDetailsRoute(
    navController: NavController,
    onBackButtonClick: () -> Unit,
    navigateToReviews: (reviews: List<ReviewUI>) -> Unit,
) {
    composable(
        route = "$MOVIES_ROUTE/{$MOVIE_ID_ARG}",
        arguments = listOf(
            navArgument(MOVIE_ID_ARG) {
                type = NavType.IntType
            })
    ) { backStackEntry ->
        val movieID = backStackEntry.arguments?.getInt(MOVIE_ID_ARG) ?: 268
        MovieDetailsScreen(
            navController = navController,
            movieID = movieID,
            navigateToReviews = navigateToReviews,
            onBackButtonClick = onBackButtonClick
        )
    }
}
