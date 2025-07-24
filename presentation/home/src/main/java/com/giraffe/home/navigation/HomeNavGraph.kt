package com.giraffe.home.navigation

import androidx.compose.runtime.Composable
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
    detailsApi: DetailsApi,
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute
    ) {
        homeRoute(
            navigateToMoviesScreen = {sectionType , sectionTitle->
                navController.navigateToMoviesList(
                    sectionType =sectionType,
                    sectionTitle =sectionTitle
                )
            },
            navigateToMoviesDetailsScreen = { navController.navigateToMovieDetails(it) },
            navigateToSeriesDetailsScreen = { navController.navigateToSeriesDetails(it) }
        )

        moviesListRoute(
            onBackClick = { navController.popBackStack() },
            navigateToMoviesDetailsScreen = { navController.navigateToMovieDetails(it) },
            navigateToSeriesDetailsScreen = { navController.navigateToSeriesDetails(it) }
        )


        composable<SeriesDetailsRoute> { backStackEntry ->
            detailsApi.SeriesDetailsContainer(seriesId = backStackEntry.toRoute<SeriesDetailsRoute>().seriesId) {
                navController.popBackStack()
            }
        }

        composable<MovieDetailsRoute> { backStackEntry ->
            detailsApi.MovieDetailsContainer(movieId = backStackEntry.toRoute<MovieDetailsRoute>().movieId) {
                navController.popBackStack()
            }
        }
    }
}