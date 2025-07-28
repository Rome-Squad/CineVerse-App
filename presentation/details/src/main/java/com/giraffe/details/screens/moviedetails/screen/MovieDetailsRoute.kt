package com.giraffe.details.screens.moviedetails.screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
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
    navigateToReviews: (reviews: List<ReviewUI>) -> Unit,
) {
    composable<MovieDetailsRoute> { backStackEntry ->

        MovieDetailsScreen(
            navController = navController,
            navigateToReviews = navigateToReviews,
            onBackButtonClick = onBackButtonClick
        )
    }
}