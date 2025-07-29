package com.giraffe.details.screens.moviedetails.screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.giraffe.details.models.ReviewUI
import kotlinx.serialization.Serializable

@Serializable
internal data class MovieDetailsRoute(val id: Int)


fun NavController.navigateToMovieDetails(id: Int) {
    navigate(MovieDetailsRoute(id))
}

fun NavGraphBuilder.movieDetailsRoute(
    navController: NavController,
    onBackButtonClick: () -> Unit,
    navigateToReviews:(Int?) -> Unit
) {
    composable<MovieDetailsRoute> { backStackEntry ->
        val movieID = backStackEntry.toRoute<MovieDetailsRoute>().id

        MovieDetailsScreen(
            navController = navController,
            movieID = movieID,
            navigateToReviews ={ navigateToReviews(movieID) },
            onBackButtonClick = onBackButtonClick
        )
    }
}