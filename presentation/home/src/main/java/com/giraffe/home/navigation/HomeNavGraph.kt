package com.giraffe.home.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.giraffe.home.screen.home.HomeRoute
import com.giraffe.home.screen.home.homeRoute
import com.giraffe.home.screen.movies_list.moviesListRoute
import com.giraffe.home.screen.movies_list.navigateToMoviesList

@Composable
fun HomeNavGraph(
    navController: NavHostController,
    navigateToMovieDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute
    ) {
        homeRoute(
            navigateToMoviesScreen = { navController.navigateToMoviesList() },
            navigateToMoviesDetailsScreen = navigateToMovieDetails,
            navigateToSeriesDetailsScreen = navigateToSeriesDetails
        )

        moviesListRoute(
            onBackClick = { navController.popBackStack() }
        )
    }
}