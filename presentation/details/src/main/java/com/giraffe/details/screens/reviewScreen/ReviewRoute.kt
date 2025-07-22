package com.giraffe.details.screens.reviewScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.giraffe.details.models.ReviewUI
import kotlinx.serialization.Serializable


@Serializable
internal data class ReviewRoute(val reviews: List<ReviewUI>?)

fun NavController.navigateToReviews(reviews: List<ReviewUI>) {
    navigate(ReviewRoute(reviews))
}

fun NavGraphBuilder.reviewRoute(navController: NavController) {
    composable<ReviewRoute> {
        val reviews = it.toRoute<ReviewRoute>().reviews

        ReviewsScreen(
            reviewsList = reviews,
            navController = navController
        )
    }
}
