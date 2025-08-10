package com.giraffe.presentation.profile.screens.ratings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
internal object RatingsRoute

internal fun NavController.navigateToRatings() {
    navigate(RatingsRoute)
}

fun NavGraphBuilder.ratingsRoute(
    navigateToMovieDetails: (Int) -> Unit = {},
    navigateToSeriesDetails: (Int) -> Unit = {},
    navigateBack: () -> Unit = {},
) {
    composable<RatingsRoute> { backStackEntry ->
        RatingScreen(
            navigateToMovieDetails = navigateToMovieDetails,
            navigateToSeriesDetails = navigateToSeriesDetails,
            navigateBack = navigateBack
        )
    }
}