package com.giraffe.presentation.details.screens.seriesdetails.screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.presentation.details.navigation.route.navigateLoginScreen
import com.giraffe.presentation.details.screens.castDetails.navigateToCastDetails
import com.giraffe.presentation.details.screens.recommended.series.navigateToRecommendedSeries
import com.giraffe.presentation.details.screens.reviewScreen.navigateToReviews
import com.giraffe.presentation.details.screens.seasons.screen.navigateToSeasons
import com.giraffe.presentation.details.screens.videoPlayer.navigateToYouTubePlayer
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
        SeriesDetailsScreen(
            navigateToRecommendedSeries = navController::navigateToRecommendedSeries,
            navigateToCastDetails = navController::navigateToCastDetails,
            navigateToSeason = navController::navigateToSeasons,
            navigateToSeriesDetails = navController::navigateToSeriesDetails,
            onBackButtonClick = onBackButtonClick,
            navigateToYouTubePlayer = navController::navigateToYouTubePlayer,
            navigateToLogIn = navController::navigateLoginScreen,{
                navController.navigateToReviews(seriesId = it)
            }
        )
    }
}