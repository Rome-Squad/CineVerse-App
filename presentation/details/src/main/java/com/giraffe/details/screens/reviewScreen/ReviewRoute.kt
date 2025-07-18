package com.giraffe.details.screens.reviewScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.details.models.ReviewUI

const val Review_ROUTE = "reviews"
const val REVIEW_LIST_ARG = "reviewList"


fun NavGraphBuilder.reviewRoute(navController: NavController) {
    composable(Review_ROUTE) {
        val reviews = navController.previousBackStackEntry?.savedStateHandle?.get<List<ReviewUI>>(
            REVIEW_LIST_ARG
        )
        ReviewsScreen(
            reviewsList = reviews,
            navController = navController
        )
    }
}
