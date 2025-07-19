package com.giraffe.explore

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.giraffe.explore.navigation.route.DISCOVER_ROUTE
import com.giraffe.explore.navigation.route.discoverRoute
import com.giraffe.explore.navigation.route.searchResultRoute
import com.giraffe.explore.navigation.route.searchRoute

class ExploreApiImp : ExploreApi {
    @Composable
    override fun ExploreContainer(
        modifier: Modifier,
        navigateToMovieDetails: (Int) -> Unit,
        navigateToSeriesDetails: (Int) -> Unit,
        navigateToCastDetails: (Int) -> Unit
    ) {
        val navController: NavHostController = rememberNavController()


        NavHost(
            navController = navController,
            startDestination = DISCOVER_ROUTE,
            enterTransition = { fadeIn(animationSpec = tween(SCREEN_TRANSITION_MILLIS)) },
            exitTransition = { fadeOut(animationSpec = tween(SCREEN_TRANSITION_MILLIS)) },
            popEnterTransition = { fadeIn(animationSpec = tween(SCREEN_TRANSITION_MILLIS)) },
            popExitTransition = { fadeOut(animationSpec = tween(SCREEN_TRANSITION_MILLIS)) }
        ) {
            discoverRoute(
                navController = navController,
                navigateToMovieDetails = navigateToMovieDetails,
                navigateToSeriesDetails = navigateToSeriesDetails
            )

            searchRoute(
                navController = navController
            )

            searchResultRoute(
                navController = navController,
                navigateToMovieDetails = navigateToMovieDetails,
                navigateToSeriesDetails = navigateToSeriesDetails,
                navigateToCastDetails = navigateToCastDetails
            )
        }
    }

    companion object {
        private const val SCREEN_TRANSITION_MILLIS = 200
    }
}