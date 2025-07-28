package com.giraffe.home.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
    var isBottomBarVisible by remember { mutableStateOf(true) }
    var selectedTabBottomBar by remember { mutableStateOf(BottomTab.Home) }
    Column {

        NavHost(
            navController = navController,
            startDestination = HomeRoute,
            modifier = Modifier.weight(1f)
        ) {
            homeRoute(
                navigateToMoviesScreen = { sectionType, sectionTitle ->
                    isBottomBarVisible = false
                    navController.navigateToMoviesList(
                        sectionType = sectionType,
                        sectionTitle = sectionTitle
                    )
                },
                navigateToMoviesDetailsScreen = {
                    isBottomBarVisible = false
                    navController.navigateToMovieDetails(it)
                },
                navigateToSeriesDetailsScreen = {
                    isBottomBarVisible = false
                    navController.navigateToSeriesDetails(it)
                }
            )

            moviesListRoute(
                onBackClick = {
                    isBottomBarVisible = true
                    navController.popBackStack()
                },
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

            composable<DiscoverRoute> { backStackEntry ->
                exploreApi.ExploreContainer()
            }
        }

        BottomNavigationBar(
            selectedTab = selectedTabBottomBar,
            isBottomBarVisible = isBottomBarVisible,
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
                        //navController.popBackStack()
                    }
                }
            }
        )
    }
}