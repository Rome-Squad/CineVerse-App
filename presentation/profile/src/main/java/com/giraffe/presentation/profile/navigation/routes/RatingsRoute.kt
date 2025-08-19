package com.giraffe.presentation.profile.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.api.home.HomeApi
import com.giraffe.presentation.profile.screens.ratings.RatingScreen
import kotlinx.serialization.Serializable

@Serializable
internal object RatingsRoute

internal fun NavController.navigateToRatings() {
    navigate(RatingsRoute)
}

fun NavGraphBuilder.ratingsRoute(
    navController: NavController,
    homeApi: HomeApi
) {
    composable<RatingsRoute> { backStackEntry ->
        RatingScreen(
            navigateToMovieDetails = navController::navigateToMovieDetails,
            navigateToSeriesDetails = navController::navigateToSeriesDetails,
            navigateBack = navController::navigateUp,
            navigateToExploreScreen = homeApi::navigateToExploreScreen
        )
    }
}