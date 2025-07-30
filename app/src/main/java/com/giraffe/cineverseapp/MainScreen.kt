package com.giraffe.cineverseapp

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
import androidx.navigation.compose.rememberNavController
import com.giraffe.designsystem.composable.BottomNavigationBar
import com.giraffe.designsystem.composable.BottomTab
import com.giraffe.explore.ExploreApi
import com.giraffe.explore.screen.discover.DiscoverRoute
import com.giraffe.home.HomeApi
import com.giraffe.home.screen.home.HomeRoute

@Composable
fun MainScreen(
    exploreApi: ExploreApi,
    homeApi: HomeApi
) {
    val navController: NavHostController = rememberNavController()

    var isBottomBarVisible by remember { mutableStateOf(true) }

    var selectedTabBottomBar by remember { mutableStateOf(BottomTab.Home) }

    Column {
        NavHost(
            navController = navController,
            startDestination = HomeRoute,
            modifier = Modifier.weight(1f)
        ) {

            composable<HomeRoute> {
                homeApi.HomeContainer {
                    isBottomBarVisible = it
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