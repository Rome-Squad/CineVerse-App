package com.giraffe.home.navigation.main

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
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
import androidx.navigation.toRoute
import com.giraffe.designsystem.R
import com.giraffe.designsystem.composable.navbar.BottomNavigationBar
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.DetailsApi
import com.giraffe.explore.ExploreApi
import com.giraffe.home.navigation.home.HomeNavGraph
import com.giraffe.match.MatchApi
import com.giraffe.profile.ProfileApi

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
        iconRes = Theme.icons.outline.home
    )

    val exploreTab = ExploreTab(
        labelRes = R.string.explore,
        iconRes = Theme.icons.outline.search
    )

    val profileTab = ProfileTab(
        labelRes = R.string.me,
        iconRes = Theme.icons.outline.userSquare
    )

    val matchTab = MatchTab(
        labelRes = R.string.match,
        iconRes = Theme.icons.outline.magicStick
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
        if (currentRouteString == HomeRoute.route) {
            isBottomBarVisible = true
        }
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
                    navigateToMovieDetails = {
                        navController.navigateToMovieDetails(it)
                        isBottomBarVisible = false
                    },
                    navigateToSeriesDetails = {
                        navController.navigateToSeriesDetails(it)
                        isBottomBarVisible = false
                    },
                    navigateToCollection = navController::navigateToCollection,
                    navigateToYourCollections = navController::navigateToYourCollections,
                    navigateToExplore = navController::navigateToExplore,
                    navigateToMatch = navController::navigateToMatch,
                ) {
                    isBottomBarVisible = it
                }

            }

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

            composable<YourCollectionsRoute> {
                profileApi.YourCollectionsContainer(
                    navigateBack = {
                        navController.popBackStack()
                    },
                    onShowBottomBarChange = { isBottomBarVisible = it }
                )
            }

            composable<CollectionRoute> { backStackEntry ->
                val args = backStackEntry.toRoute<CollectionRoute>()
                val collectionId = args.collectionId
                val collectionName = args.collectionName
                profileApi.CollectionContainer(
                    collectionId = collectionId,
                    collectionName = collectionName,
                    navigateBack = {
                        navController.popBackStack()
                    }
                ) {
                    isBottomBarVisible = it
                }
            }
        }

        BottomNavigationBar(
            tabs = bottomTabs,
            selectedTabRoute = currentRoute,
            isBottomBarVisible = isBottomBarVisible,
            onTabSelected = { tab ->
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

    val activity = LocalActivity.current
    val isAtRoot =
        navController.currentDestination?.route == navController.graph.startDestinationRoute
    BackHandler(isAtRoot) { activity?.finish() }
}