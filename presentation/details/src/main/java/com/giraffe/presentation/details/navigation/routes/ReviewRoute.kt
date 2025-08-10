package com.giraffe.presentation.details.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.presentation.details.screens.reviewScreen.ReviewsScreen
import kotlinx.serialization.Serializable


@Serializable
internal data class ReviewRoute(val movieId: Int?, val seriesId: Int?)

fun NavController.navigateToReviews(movieId: Int? = null, seriesId: Int? = null) {
    navigate(ReviewRoute(movieId, seriesId))
}
fun NavGraphBuilder.reviewRoute(navController: NavController) {
    composable<ReviewRoute> { backStackEntry ->
        ReviewsScreen(
            navController = navController
        )
    }
}
