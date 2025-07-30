package com.giraffe.home.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.giraffe.details.DetailsApi
import com.giraffe.home.screen.home.HomeRoute
import com.giraffe.home.screen.home.homeRoute
import com.giraffe.home.screen.movies_list.moviesListRoute
import com.giraffe.home.screen.movies_list.navigateToMoviesList


@Composable
fun HomeNavGraph(
    navController: NavHostController,
    detailsApi: DetailsApi
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = Modifier.fillMaxSize()
    ) {
        homeRoute(
            navigateToMoviesScreen = navController::navigateToMoviesList,
            navigateToMoviesDetailsScreen = navController::navigateToMovieDetails,
            navigateToSeriesDetailsScreen = navController::navigateToSeriesDetails
        )

        moviesListRoute(
            onBackClick = navController::popBackStack,
            navigateToMoviesDetailsScreen = navController::navigateToMovieDetails,
            navigateToSeriesDetailsScreen = navController::navigateToSeriesDetails
        )


        composable<SeriesDetailsRoute> { backStackEntry ->
            val seriesId = backStackEntry.toRoute<SeriesDetailsRoute>().seriesId
            detailsApi.SeriesDetailsContainer(seriesId) {
                navController.popBackStack()
            }
        }

        composable<MovieDetailsRoute> { backStackEntry ->
            val movieId = backStackEntry.toRoute<MovieDetailsRoute>().movieId
            detailsApi.MovieDetailsContainer(movieId) {
                navController.popBackStack()
            }
        }
    }
}

