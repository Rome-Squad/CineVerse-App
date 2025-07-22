package com.giraffe.explore.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.giraffe.explore.screen.discover.DiscoverRoute
import com.giraffe.explore.screen.discover.discoverRoute
import com.giraffe.explore.screen.search.navigateToSearch
import com.giraffe.explore.screen.search.searchRoute
import com.giraffe.explore.screen.searchresult.navigateToSearchResult
import com.giraffe.explore.screen.searchresult.searchResultRoute

@Composable
fun ExploreNavGraph(
    navController: NavHostController,
    navigateToMovieDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit,
    navigateToCastDetails: (Int) -> Unit
) {
    val durationMillis = 200
    NavHost(
        navController = navController,
        startDestination = DiscoverRoute,
        enterTransition = { fadeIn(animationSpec = tween(durationMillis)) },
        exitTransition = { fadeOut(animationSpec = tween(durationMillis)) },
        popEnterTransition = { fadeIn(animationSpec = tween(durationMillis)) },
        popExitTransition = { fadeOut(animationSpec = tween(durationMillis)) }
    ) {
        discoverRoute(
            navigateToMovieDetails = navigateToMovieDetails,
            navigateToSeriesDetails = navigateToSeriesDetails,
            navigateToSearch = {
                navController.navigateToSearch()
            }
        )

        searchRoute(
            navigateToSearchResult = {
                navController.navigateToSearchResult(it)
            },
            onBackClick = {
                navController.popBackStack()
            }
        )

        searchResultRoute(
            navigateToMovieDetails = navigateToMovieDetails,
            navigateToSeriesDetails = navigateToSeriesDetails,
            navigateToCastDetails = navigateToCastDetails,
            onBackClick = {
                navController.popBackStack()
            }
        )
    }
}