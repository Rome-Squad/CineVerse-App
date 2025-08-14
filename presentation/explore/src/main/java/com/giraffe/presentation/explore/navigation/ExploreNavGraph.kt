package com.giraffe.presentation.explore.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import com.giraffe.api.details.DetailsApi
import com.giraffe.presentation.explore.navigation.routes.CastDetailsRoute
import com.giraffe.presentation.explore.navigation.routes.DiscoverRoute
import com.giraffe.presentation.explore.navigation.routes.MovieDetailsRoute
import com.giraffe.presentation.explore.navigation.routes.SearchResultRoute
import com.giraffe.presentation.explore.navigation.routes.SearchRoute
import com.giraffe.presentation.explore.navigation.routes.SeriesDetailsRoute
import com.giraffe.presentation.explore.navigation.routes.discoverRoute
import com.giraffe.presentation.explore.navigation.routes.navigateToCastDetails
import com.giraffe.presentation.explore.navigation.routes.navigateToMovieDetails
import com.giraffe.presentation.explore.navigation.routes.navigateToSearch
import com.giraffe.presentation.explore.navigation.routes.navigateToSearchResult
import com.giraffe.presentation.explore.navigation.routes.navigateToSeriesDetails
import com.giraffe.presentation.explore.navigation.routes.searchResultRoute
import com.giraffe.presentation.explore.navigation.routes.searchRoute

@Composable
internal fun ExploreNavGraph(
    navController: NavHostController,
    detailsApi: DetailsApi,
    onShowBottomBarChange: (Boolean) -> Unit
) {
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
            navigateToMovieDetails = navController::navigateToMovieDetails,
            navigateToSeriesDetails = navController::navigateToSeriesDetails,
            navigateToSearch = navController::navigateToSearch
        )

        searchRoute(
            navigateToSearchResult = navController::navigateToSearchResult,
            onBackClick = navController::popBackStack,
            navigateToMovieDetail = navController::navigateToMovieDetails,
            navigateToSeriesDetail = navController::navigateToSeriesDetails,
            navigateToPersonDetail = navController::navigateToCastDetails,
        )

        searchResultRoute(
            navigateToMovieDetails = navController::navigateToMovieDetails,
            navigateToSeriesDetails = navController::navigateToSeriesDetails,
            navigateToCastDetails = navController::navigateToCastDetails,
            navigateToSearchScreen = navController::navigateToSearch,
            onBackClick = navController::popBackStack
        )

        composable<CastDetailsRoute> { backStackEntry ->
            val castId = backStackEntry.toRoute<CastDetailsRoute>().castId
            detailsApi.CastDetailsContainer(
                castId = castId,
                onBackClick = navController::popBackStack
            )
        }

        composable<SeriesDetailsRoute> { backStackEntry ->
            val seriesId = backStackEntry.toRoute<SeriesDetailsRoute>().seriesId
            detailsApi.SeriesDetailsContainer(
                seriesId = seriesId,
                onBackClick = navController::popBackStack
            )
        }

        composable<MovieDetailsRoute> { backStackEntry ->
            val movieId = backStackEntry.toRoute<MovieDetailsRoute>().movieId
            detailsApi.MovieDetailsContainer(
                movieId = movieId,
                onBackClick = navController::popBackStack
            )
        }
    }
}