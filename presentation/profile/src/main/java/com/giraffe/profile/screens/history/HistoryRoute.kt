package com.giraffe.profile.screens.history

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
internal object HistoryRoute

internal fun NavController.navigateToHistory() {
    navigate(HistoryRoute)
}

fun NavGraphBuilder.historyRoute(
    onBackClicked: () -> Unit,
    navigateToMoviesDetailsScreen: (Int) -> Unit,
    navigateToSeriesDetailsScreen: (Int) -> Unit,
    navigateToExploreScreen: () -> Unit
) {
    composable<HistoryRoute> {
        HistoryScreen(
            onBackClicked = onBackClicked,
            navigateToMoviesDetailsScreen = navigateToMoviesDetailsScreen,
            navigateToSeriesDetailsScreen = navigateToSeriesDetailsScreen,
            navigateToExploreScreen = navigateToExploreScreen
        )
    }
}