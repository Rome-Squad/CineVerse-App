package com.giraffe.presentation.profile.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.presentation.profile.screens.history.HistoryScreen
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