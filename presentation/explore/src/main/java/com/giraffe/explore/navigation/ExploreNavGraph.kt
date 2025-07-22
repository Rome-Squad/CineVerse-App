package com.giraffe.explore.navigation

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.giraffe.details.DetailsApi
import com.giraffe.explore.screen.discover.DiscoverRoute
import com.giraffe.explore.screen.discover.discoverRoute
import com.giraffe.explore.screen.search.navigateToSearch
import com.giraffe.explore.screen.search.searchRoute
import com.giraffe.explore.screen.searchresult.CastDetailsRoute
import com.giraffe.explore.screen.searchresult.MovieDetailsRoute
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
    transitionSpecs: FiniteAnimationSpec<Float> = tween(200)
) {

    NavHost(
        navController = navController,
        startDestination = DiscoverRoute,
        enterTransition = { fadeIn(transitionSpecs) },
        exitTransition = { fadeOut(transitionSpecs) },
        popEnterTransition = { fadeIn(transitionSpecs) },
        popExitTransition = { fadeOut(transitionSpecs) }
    ) {
        discoverRoute(
            navigateToMovieDetails = navController::navigateToMovieDetails,
            navigateToSeriesDetails = navController::navigateToSeriesDetails,
            navigateToSearch = navController::navigateToSearch
        )

        searchRoute(
            navigateToSearchResult = navController::navigateToSearchResult,
            onBackClick = navController::popBackStack
        )

        searchResultRoute(
            navigateToMovieDetails = navController::navigateToMovieDetails,
            navigateToSeriesDetails = navController::navigateToSeriesDetails,
            navigateToCastDetails = navController::navigateToCastDetails,
            onBackClick = navController::popBackStack
        )

        composable<CastDetailsRoute> { backStackEntry ->
            val castId = backStackEntry.toRoute<CastDetailsRoute>().castId

            detailsApi.CastDetailsContainer(castId = castId)
        }

        composable<SeriesDetailsRoute> { backStackEntry ->
            val seriesId = backStackEntry.toRoute<SeriesDetailsRoute>().seriesId

            detailsApi.SeriesDetailsContainer(seriesId = seriesId)
        }

        composable<MovieDetailsRoute> { backStackEntry ->
            val movieId = backStackEntry.toRoute<MovieDetailsRoute>().movieId

            detailsApi.MovieDetailsContainer(movieId = movieId)
        }
    }
}