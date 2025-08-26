package com.giraffe.presentation.explore.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.giraffe.api.details.DetailsApi
import com.giraffe.presentation.explore.navigation.routes.DiscoverRoute
import com.giraffe.presentation.explore.navigation.routes.SearchResultRoute
import com.giraffe.presentation.explore.navigation.routes.SearchRoute
import com.giraffe.presentation.explore.navigation.routes.discoverRoute
import com.giraffe.presentation.explore.navigation.routes.navigateToSearch
import com.giraffe.presentation.explore.navigation.routes.navigateToSearchResult
import com.giraffe.presentation.explore.navigation.routes.searchResultRoute
import com.giraffe.presentation.explore.navigation.routes.searchRoute

@Composable
internal fun ExploreNavGraph(
    navController: NavHostController,
    detailsApi: DetailsApi,
    onShowBottomBarChange: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val startDestination = DiscoverRoute

    val currentRoute = navBackStackEntry?.destination?.route

    val isBottomBarVisible =
        currentRoute.orEmpty().endsWith(startDestination.toString()) ||
                currentRoute.orEmpty().endsWith(SearchRoute.toString()) ||
                currentRoute.orEmpty().startsWith(SearchResultRoute.toString().substringBefore('$'))

    LaunchedEffect(currentRoute) {
        onShowBottomBarChange(isBottomBarVisible)
    }

    NavHost(
        navController = navController,
        startDestination = DiscoverRoute
    ) {

        discoverRoute(
            navigateToMovieDetails = {detailsApi.launchMovieDetails(context, it)},
            navigateToSeriesDetails = {detailsApi.launchSeriesDetails(context, it)},
            navigateToSearch = navController::navigateToSearch
        )

        searchRoute(
            navigateToSearchResult = navController::navigateToSearchResult,
            onBackClick = navController::popBackStack,
            navigateToMovieDetail = {detailsApi.launchMovieDetails(context, it)},
            navigateToSeriesDetail = {detailsApi.launchSeriesDetails(context, it)},
            navigateToPersonDetail = {detailsApi.launchCastDetails(context, it)},
        )

        searchResultRoute(
            navigateToMovieDetails = {detailsApi.launchMovieDetails(context, it)},
            navigateToSeriesDetails = {detailsApi.launchSeriesDetails(context, it)},
            navigateToCastDetails = {detailsApi.launchCastDetails(context, it)},
            navigateToSearchScreen = navController::navigateToSearch,
            onBackClick = navController::popBackStack
        )

    }
}