package com.giraffe.presentation.profile.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.api.details.DetailsApi
import com.giraffe.api.home.HomeApi
import com.giraffe.presentation.profile.screens.history.HistoryScreen
import kotlinx.serialization.Serializable

@Serializable
internal object HistoryRoute

internal fun NavController.navigateToHistory() {
    navigate(HistoryRoute)
}

fun NavGraphBuilder.historyRoute(
    navController: NavController,
    navigateToMovieDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit,
    homeApi: HomeApi,
) {
    composable<HistoryRoute> {
        HistoryScreen(
            onBackClicked = navController::navigateUp,
            navigateToMoviesDetailsScreen = navigateToMovieDetails,
            navigateToSeriesDetailsScreen = navigateToSeriesDetails,
            navigateToExploreScreen = homeApi::navigateToExploreScreen
        )
    }
}