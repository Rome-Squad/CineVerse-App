package com.giraffe.explore.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.giraffe.explore.navigation.route.DISCOVER_ROUTE
/*

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
//        ExploreApiImp().apply {
//            createDiscoverScreen(navController)
//            createSearchScreen(navController)
//            createSearchResultScreen(navController)
//        }
    }
}*/
