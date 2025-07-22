package com.giraffe.details.screens.moviedetails.screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.giraffe.details.models.ReviewUI
import com.giraffe.details.nav.MovieDetailsRoute


fun NavController.navigateToMovieDetails(id: Int) {
    navigate(MovieDetailsRoute(id))
}

fun NavGraphBuilder.movieDetailsRoute(
    navController: NavController,
    onBackButtonClick: () -> Unit,
    navigateToReviews: (reviews: List<ReviewUI>) -> Unit,
) {
    composable<MovieDetailsRoute> { backStackEntry ->
        val movieID = backStackEntry.toRoute<MovieDetailsRoute>().id

        MovieDetailsScreen(
            navController = navController,
            movieID = movieID,
            navigateToReviews = navigateToReviews,
            onBackButtonClick = onBackButtonClick
        )
    }
}