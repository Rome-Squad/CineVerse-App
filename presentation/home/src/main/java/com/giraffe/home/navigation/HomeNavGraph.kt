package com.giraffe.home.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.giraffe.designsystem.composable.BottomNavigationBar
import com.giraffe.designsystem.composable.BottomTab
import com.giraffe.details.DetailsApi
import com.giraffe.explore.ExploreApi
import com.giraffe.home.screen.home.HomeRoute
import com.giraffe.home.screen.home.homeRoute
import com.giraffe.home.screen.movies_list.moviesListRoute
import com.giraffe.home.screen.movies_list.navigateToMoviesList


@Composable
fun HomeNavGraph(
    navController: NavHostController,
    detailsApi: DetailsApi,
    exploreApi: ExploreApi
) {
    val navBackStackEntry = navController.currentDestination?.route.toString()

    val bottomBarRoutes = listOf(
        HomeRoute,
        DiscoverRoute
    )

    val isBottomBarVisible = navBackStackEntry in bottomBarRoutes
    var selectedTabBottomBar by remember { mutableStateOf(BottomTab.Home) }
    Column {

        NavHost(
            navController = navController,
            startDestination = HomeRoute
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

            composable<DiscoverRoute> { backStackEntry ->
                exploreApi.ExploreContainer()
            }
        }

        BottomNavigationBar(
            selectedTab = selectedTabBottomBar,
            isBottomBarVisible = !isBottomBarVisible,
            onTabSelected = {
                when (it) {
                    BottomTab.Explore -> {
                        selectedTabBottomBar = BottomTab.Explore
                        navController.navigateToDiscover()
                    }

                    BottomTab.Home -> {
                        selectedTabBottomBar = BottomTab.Home
                        navController.popBackStack()
                    }

                    else -> {
                    }
                }
            }
        )
    }
}