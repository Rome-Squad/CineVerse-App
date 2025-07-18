package com.giraffe.explore.nav

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.giraffe.explore.nav.route.DISCOVER_ROUTE
import com.giraffe.explore.nav.route.discoverRoute
import com.giraffe.explore.nav.route.searchResultRoute
import com.giraffe.explore.nav.route.searchRoute

private const val SCREEN_TRANSITION_MILLIS = 200

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ExploreNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DISCOVER_ROUTE,
        enterTransition = { fadeIn(animationSpec = tween(SCREEN_TRANSITION_MILLIS)) },
        exitTransition = { fadeOut(animationSpec = tween(SCREEN_TRANSITION_MILLIS)) },
        popEnterTransition = { fadeIn(animationSpec = tween(SCREEN_TRANSITION_MILLIS)) },
        popExitTransition = { fadeOut(animationSpec = tween(SCREEN_TRANSITION_MILLIS)) }
    ) {
        discoverRoute(navController)
        searchRoute(navController)
        searchResultRoute(navController)
    }
}