package com.giraffe.presentation.details.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable


@Serializable
internal data class SeriesDetailsRoute(val seriesID: Int)


internal fun NavController.navigateToSeriesDetails(seriesId: Int) {
    navigate(SeriesDetailsRoute(seriesId))
}

fun NavGraphBuilder.seriesDetailsRoute(
    navController: NavController,
    onBackButtonClick: () -> Unit,
) {
    composable<SeriesDetailsRoute> { backStackEntry ->
        _root_ide_package_.com.giraffe.presentation.details.screens.seriesdetails.SeriesDetailsScreen(
            navigateToRecommendedSeries = navController::navigateToRecommendedSeries,
            navigateToCastDetails = navController::navigateToCastDetails,
            navigateToSeason = navController::navigateToSeasons,
            navigateToSeriesDetails = navController::navigateToSeriesDetails,
            onBackButtonClick = onBackButtonClick,
            navigateToYouTubePlayer = navController::navigateToYouTubePlayer,
            navigateToLogIn = navController::navigateLoginScreen, {
                navController.navigateToReviews(seriesId = it)
            }
        )
    }
}