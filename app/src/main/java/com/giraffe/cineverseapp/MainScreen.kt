package com.giraffe.cineverseapp

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.giraffe.designsystem.composable.BottomNavigationBar
import com.giraffe.designsystem.composable.BottomTab
import com.giraffe.designsystem.composable.Scaffold
import com.giraffe.details.screens.moviedetails.screen.navigateToMovieDetails
import com.giraffe.explore.screen.discover.DiscoverRoute
import com.giraffe.explore.screen.discover.discoverRoute
import com.giraffe.explore.screen.search.navigateToSearch
import com.giraffe.home.navigation.navigateToSeriesDetails
import com.giraffe.home.screen.home.HomeRoute
import com.giraffe.home.screen.home.homeRoute
import com.giraffe.home.screen.movies_list.navigateToMoviesList

@Composable
fun MainScreen() {
    val navController: NavHostController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var currentRoute = navBackStackEntry?.destination
    val bottomBarRoutes = listOf(
        HomeRoute::class,
        DiscoverRoute::class
    )

    val isBottomBarVisible = currentRoute?.hierarchy?.any { navDestination ->
        navDestination.route?.let { route ->
            bottomBarRoutes.any { klass ->
                route.contains(klass.simpleName ?: "")
            }
        } == true
    } == true

    var selectedTabBottomBar by remember { mutableStateOf(BottomTab.Home) }
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedTab = selectedTabBottomBar,
                isBottomBarVisible = isBottomBarVisible,
                onTabSelected = {
                    Log.d("CurrentRoutesss", "MainScreen: $currentRoute")
                    when (it) {
                        BottomTab.Explore -> {
                            selectedTabBottomBar = BottomTab.Explore
                            navController.navigate(DiscoverRoute)
                        }

                        BottomTab.Home -> {
                            selectedTabBottomBar = BottomTab.Home
                            navController.navigate(HomeRoute)
                        }

                        else -> {
                        }
                    }
                }
            )
        }
    ) {

        NavHost(
            navController = navController,
            startDestination = HomeRoute,
        ) {
            homeRoute(
                navigateToMoviesScreen = navController::navigateToMoviesList,
                navigateToMoviesDetailsScreen = navController::navigateToMovieDetails,
                navigateToSeriesDetailsScreen = navController::navigateToSeriesDetails
            )
            discoverRoute(
                navigateToMovieDetails = navController::navigateToMovieDetails,
                navigateToSeriesDetails = navController::navigateToSeriesDetails,
                navigateToSearch = navController::navigateToSearch
            )
        }
    }
}