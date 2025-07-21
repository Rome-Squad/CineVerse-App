package com.giraffe.explore.screen.discover

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object DiscoverRoute

fun NavController.navigateToDiscover() {
    navigate(DiscoverRoute)
}

fun NavGraphBuilder.discoverRoute(
    navigateToMovieDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit,
    navigateToSearch: () -> Unit,
) {
    composable<DiscoverRoute> { backStackEntry ->
        DiscoverScreen(
            navigateToMovieDetails = navigateToMovieDetails,
            navigateToSeriesDetails = navigateToSeriesDetails,
            navigateToSearch = navigateToSearch
        )
    }
}