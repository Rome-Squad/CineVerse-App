package com.giraffe.explore.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.giraffe.explore.nav.route.DISCOVER_ROUTE
import com.giraffe.explore.nav.route.discoverRoute
import com.giraffe.explore.nav.route.searchResultRoute
import com.giraffe.explore.nav.route.searchRoute

@Composable
fun ExploreNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = DISCOVER_ROUTE) {
        discoverRoute(navController)
        searchRoute(navController)
        searchResultRoute(navController)
    }
}