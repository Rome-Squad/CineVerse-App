package com.giraffe.details.screens.reviewScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.giraffe.details.models.ReviewUI
import kotlinx.serialization.Serializable


@Serializable
internal data class ReviewRoute(val movieId: Int?, val seriesId: Int?)

fun NavController.navigateToReviews(movieId: Int? = null, seriesId: Int? = null) {
    navigate(ReviewRoute(movieId, seriesId))
}

fun NavGraphBuilder.reviewRoute(navController: NavController) {
    composable<ReviewRoute> { backStackEntry ->
        val route = backStackEntry.toRoute<ReviewRoute>()
        val movieId = route.movieId
        val seriesId = route.seriesId

        ReviewsScreen(
            movieId = movieId,
            seriesId = seriesId,
            navController = navController
        )
    }
}
