package com.giraffe.explore.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import com.giraffe.details.DetailsApi
import com.giraffe.explore.screen.discover.DiscoverRoute
import com.giraffe.explore.screen.discover.discoverRoute
import com.giraffe.explore.screen.search.SearchRoute
import com.giraffe.explore.screen.search.navigateToSearch
import com.giraffe.explore.screen.search.searchRoute
import com.giraffe.explore.screen.searchresult.CastDetailsRoute
import com.giraffe.explore.screen.searchresult.MovieDetailsRoute
import com.giraffe.explore.screen.searchresult.SearchResultRoute
import com.giraffe.explore.screen.searchresult.SeriesDetailsRoute
import com.giraffe.explore.screen.searchresult.navigateToCastDetails
import com.giraffe.explore.screen.searchresult.navigateToMovieDetails
import com.giraffe.explore.screen.searchresult.navigateToSearchResult
import com.giraffe.explore.screen.searchresult.navigateToSeriesDetails
import com.giraffe.explore.screen.searchresult.searchResultRoute

@Composable
internal fun ExploreNavGraph(
    navController: NavHostController,
    detailsApi: DetailsApi,
    onShowBottomBarChange: (Boolean) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var currentRoute = navBackStackEntry?.destination
    val bottomBarRoutes = listOf(
        DiscoverRoute::class,
        SearchRoute::class,
        SearchResultRoute::class
    )
    val isBottomBarVisible = currentRoute?.hierarchy?.any { navDestination ->
        navDestination.route?.let { route ->
            bottomBarRoutes.any { klass ->
                route.contains(klass.simpleName .orEmpty())
            }
        } == true
    } == true
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