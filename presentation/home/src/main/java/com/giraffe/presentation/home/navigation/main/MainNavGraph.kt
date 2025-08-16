package com.giraffe.presentation.home.navigation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.giraffe.api.details.DetailsApi
import com.giraffe.api.explore.ExploreApi
import com.giraffe.api.profile.ProfileApi
import com.giraffe.designsystem.R
import com.giraffe.designsystem.composable.navbar.BottomNavigationBar
import com.giraffe.designsystem.theme.Theme
import com.giraffe.match.MatchApi
import com.giraffe.presentation.home.navigation.home.HomeNavGraph
import com.giraffe.presentation.home.navigation.main.routes.ExploreRoute
import com.giraffe.presentation.home.navigation.main.routes.ExploreTab
import com.giraffe.presentation.home.navigation.main.routes.HomeRoute
import com.giraffe.presentation.home.navigation.main.routes.HomeTab
import com.giraffe.presentation.home.navigation.main.routes.MatchRoute
import com.giraffe.presentation.home.navigation.main.routes.MatchTab
import com.giraffe.presentation.home.navigation.main.routes.ProfileRoute
import com.giraffe.presentation.home.navigation.main.routes.ProfileTab
import com.giraffe.presentation.home.navigation.main.routes.navigateToExplore
import com.giraffe.presentation.home.navigation.main.routes.navigateToMatch

@Composable
fun MainNavGraph(
    navController: NavHostController,
    detailsApi: DetailsApi,
    exploreApi: ExploreApi,
    profileApi: ProfileApi,
    matchApi: MatchApi
) {

    val homeTab = HomeTab(
        labelRes = R.string.home,
        iconRes = listOf(Theme.icons.dueTone.home, Theme.icons.outline.home)
    )

    val exploreTab = ExploreTab(
        labelRes = R.string.explore,
        iconRes = listOf(Theme.icons.dueTone.search, Theme.icons.outline.search)
    )

    val profileTab = ProfileTab(
        labelRes = R.string.me,
        iconRes = listOf(Theme.icons.dueTone.userSquare, Theme.icons.outline.userSquare)
    )

    val matchTab = MatchTab(
        labelRes = R.string.match,
        iconRes = listOf(Theme.icons.dueTone.magicStick, Theme.icons.outline.magicStick)
    )

    val bottomTabs = listOf(
        homeTab,
        exploreTab,
        matchTab,
        profileTab
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRouteString = navBackStackEntry?.destination?.route

    val currentRoute = when (currentRouteString) {
        HomeRoute.route -> HomeRoute
        ExploreRoute.route -> ExploreRoute
        MatchRoute.route -> MatchRoute
        ProfileRoute.route -> ProfileRoute
        else -> null
    }

    var isBottomBarVisible by rememberSaveable {
        mutableStateOf(
            true
        )
    }

    LaunchedEffect(currentRouteString) {
        isBottomBarVisible == currentRouteString in listOf(
            HomeRoute.route,
            ExploreRoute.route,
            MatchRoute.route,
            ProfileRoute.route
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
    ) {
        NavHost(
            navController = navController,
            startDestination = HomeRoute.route,
            modifier = Modifier.weight(1f)
        ) {

            composable(
                route = HomeRoute.route
            ) {
                HomeNavGraph(
                    navigateToExplore = navController::navigateToExplore,
                    navigateToMatch = navController::navigateToMatch,
                    detailsApi = detailsApi,
                    profileApi = profileApi,
                ) {
                    isBottomBarVisible = it
                }
            }


            composable(
                route = ExploreRoute.route
            ) {
                exploreApi.ExploreContainer {
                    isBottomBarVisible = it
                }
            }

            composable(
                route = MatchRoute.route
            ) {
                matchApi.MatchContainer { isBottomBarVisible = it }
            }

            composable(
                route = ProfileRoute.route
            ) {
                profileApi.ProfileContainer { isBottomBarVisible = it }
            }

        }

        BottomNavigationBar(
            tabs = bottomTabs,
            selectedTabRoute = currentRoute,
            isBottomBarVisible = isBottomBarVisible,
            onTabSelected = { tab ->
                if (currentRoute == tab.route) {
                    return@BottomNavigationBar
                }

                navController.navigate(tab.route.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                        inclusive = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}