package com.giraffe.explore.screen.discover

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object DiscoverRoute


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