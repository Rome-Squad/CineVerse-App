package com.giraffe.explore.nav.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.explore.screen.discover.DiscoverScreen

const val DISCOVER_ROUTE = "DISCOVER_ROUTE"

fun NavController.navigateToDiscover() {
    navigate(DISCOVER_ROUTE)
}
fun NavGraphBuilder.discoverRoute(
    navController: NavController,
    navigateToMovieDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit
) {
    composable(DISCOVER_ROUTE) { backStackEntry ->
        DiscoverScreen(
            navController = navController,
            navigateToMovieDetails = navigateToMovieDetails,
            navigateToSeriesDetails = navigateToSeriesDetails
        )
    }
}