package com.giraffe.home.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var currentRoute = navBackStackEntry?.destination
    val bottomBarRoutes = listOf(
        HomeRoute::class,
        DiscoverRoute::class
    )
    var isBottomBarVisible by remember { mutableStateOf(true) }
    var selectedTabBottomBar by remember { mutableStateOf(BottomTab.Home) }


    isBottomBarVisible = currentRoute?.hierarchy?.any { navDestination ->
        navDestination.route?.let { route ->
            bottomBarRoutes.any { klass ->
                route.contains(klass.simpleName ?: "")
            }
        } == true
    } == true




    Column {
        NavHost(
            navController = navController,
            startDestination = HomeRoute,
            modifier = Modifier.weight(1f)
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

            composable<DiscoverRoute> {
                exploreApi.ExploreContainer {
                    isBottomBarVisible = it
                }
            }
        }

        BottomNavigationBar(
            selectedTab = selectedTabBottomBar,
            isBottomBarVisible = isBottomBarVisible,
            onTabSelected = {
                when (it) {
                    BottomTab.Explore -> {
                        selectedTabBottomBar = BottomTab.Explore
                        navController.navigate(DiscoverRoute) {
                            popUpTo(navController.graph.startDestinationRoute ?: "") {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }

                    BottomTab.Home -> {
                        selectedTabBottomBar = BottomTab.Home
                        navController.navigate(HomeRoute) {
                            popUpTo(navController.graph.startDestinationRoute ?: "") {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }

                    else -> {
                    }
                }
            }
        )
    }
}