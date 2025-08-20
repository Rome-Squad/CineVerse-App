package com.giraffe.presentation.home.navigation.main

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.giraffe.api.details.DetailsApi
import com.giraffe.api.explore.ExploreApi
import com.giraffe.api.profile.ProfileApi
import com.giraffe.designsystem.R
import com.giraffe.designsystem.composable.navbar.BottomNavigationBar
import com.giraffe.designsystem.composable.navbar.BottomTab
import com.giraffe.designsystem.theme.Theme
import com.giraffe.match.MatchApi
import com.giraffe.presentation.home.navigation.home.HomeNavGraph
import com.giraffe.presentation.home.navigation.main.routes.ExploreRoute
import com.giraffe.presentation.home.navigation.main.routes.HomeRoute
import com.giraffe.presentation.home.navigation.main.routes.MatchRoute
import com.giraffe.presentation.home.navigation.main.routes.ProfileRoute
import com.giraffe.presentation.home.navigation.main.routes.navigateToExplore
import com.giraffe.presentation.home.navigation.main.routes.navigateToMatch
import java.util.Locale

@Composable
fun MainNavGraph(
    navController: NavHostController,
    detailsApi: DetailsApi,
    exploreApi: ExploreApi,
    profileApi: ProfileApi,
    matchApi: MatchApi
) {

    val homeTab = BottomTab(
        labelRes = R.string.home,
        iconRes = listOf(Theme.icons.dueTone.home, Theme.icons.outline.home),
        route = HomeRoute
    )

    val exploreTab = BottomTab(
        labelRes = R.string.explore,
        iconRes = listOf(Theme.icons.dueTone.search, Theme.icons.outline.search),
        route = ExploreRoute
    )

    val profileTab = BottomTab(
        labelRes = R.string.me,
        iconRes = listOf(Theme.icons.dueTone.userSquare, Theme.icons.outline.userSquare),
        route = ProfileRoute
    )

    val matchTab = BottomTab(
        labelRes = R.string.match,
        iconRes = listOf(Theme.icons.dueTone.magicStick, Theme.icons.outline.magicStick),
        route = MatchRoute
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

            animatedComposable(
                route = HomeRoute.route,
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

            animatedComposable(
                route = ExploreRoute.route
            ) {
                exploreApi.ExploreContainer {
                    isBottomBarVisible = it
                }
            }

            animatedComposable(
                route = MatchRoute.route
            ) {
                matchApi.MatchContainer { isBottomBarVisible = it }
            }

            animatedComposable(
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
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}

fun NavGraphBuilder.animatedComposable(
    route: String,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    val locale = AppCompatDelegate.getApplicationLocales().get(0) ?: Locale.getDefault()
    val rtlFlag = if (locale.language == "ar") -1 else 1
    composable(
        route = route,
        enterTransition = {
            when (initialState.destination.route to targetState.destination.route) {
                HomeRoute.route to ExploreRoute.route,
                HomeRoute.route to MatchRoute.route,
                HomeRoute.route to ProfileRoute.route,
                ExploreRoute.route to MatchRoute.route,
                ExploreRoute.route to ProfileRoute.route,
                MatchRoute.route to ProfileRoute.route -> {
                    slideInHorizontally(tween(300)) { it * rtlFlag }
                }

                ExploreRoute.route to HomeRoute.route,
                MatchRoute.route to HomeRoute.route,
                MatchRoute.route to ExploreRoute.route,
                ProfileRoute.route to HomeRoute.route,
                ProfileRoute.route to ExploreRoute.route,
                ProfileRoute.route to MatchRoute.route -> {
                    slideInHorizontally(tween(300)) { -it * rtlFlag }
                }

                else -> null
            }
        },
        exitTransition = {
            when (initialState.destination.route to targetState.destination.route) {
                HomeRoute.route to ExploreRoute.route,
                HomeRoute.route to MatchRoute.route,
                HomeRoute.route to ProfileRoute.route,
                ExploreRoute.route to MatchRoute.route,
                ExploreRoute.route to ProfileRoute.route,
                MatchRoute.route to ProfileRoute.route -> {
                    slideOutHorizontally(tween(300)) { -it * rtlFlag }
                }

                ExploreRoute.route to HomeRoute.route,
                MatchRoute.route to HomeRoute.route,
                MatchRoute.route to ExploreRoute.route,
                ProfileRoute.route to HomeRoute.route,
                ProfileRoute.route to ExploreRoute.route,
                ProfileRoute.route to MatchRoute.route -> {
                    slideOutHorizontally(tween(300)) { it * rtlFlag }
                }

                else -> null
            }
        },
        popEnterTransition = {
            when (initialState.destination.route to targetState.destination.route) {
                HomeRoute.route to ExploreRoute.route,
                HomeRoute.route to MatchRoute.route,
                HomeRoute.route to ProfileRoute.route,
                ExploreRoute.route to MatchRoute.route,
                ExploreRoute.route to ProfileRoute.route,
                MatchRoute.route to ProfileRoute.route -> {
                    slideInHorizontally(tween(300)) { it * rtlFlag }
                }

                ExploreRoute.route to HomeRoute.route,
                MatchRoute.route to HomeRoute.route,
                MatchRoute.route to ExploreRoute.route,
                ProfileRoute.route to HomeRoute.route,
                ProfileRoute.route to ExploreRoute.route,
                ProfileRoute.route to MatchRoute.route -> {
                    slideInHorizontally(tween(300)) { -it * rtlFlag }
                }

                else -> null
            }
        },
        popExitTransition = {
            when (initialState.destination.route to targetState.destination.route) {
                HomeRoute.route to ExploreRoute.route,
                HomeRoute.route to MatchRoute.route,
                HomeRoute.route to ProfileRoute.route,
                ExploreRoute.route to MatchRoute.route,
                ExploreRoute.route to ProfileRoute.route,
                MatchRoute.route to ProfileRoute.route -> {
                    slideOutHorizontally(tween(300)) { -it * rtlFlag }
                }

                ExploreRoute.route to HomeRoute.route,
                MatchRoute.route to HomeRoute.route,
                MatchRoute.route to ExploreRoute.route,
                ProfileRoute.route to HomeRoute.route,
                ProfileRoute.route to ExploreRoute.route,
                ProfileRoute.route to MatchRoute.route -> {
                    slideOutHorizontally(tween(300)) { it * rtlFlag }
                }

                else -> null
            }
        },
        content = content
    )
}
