package com.giraffe.home.navigation

import android.util.Log
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
import com.giraffe.home.screen.home.HomeRoute
import com.giraffe.home.screen.home.HomeTab
import com.giraffe.home.screen.home.homeRoute
import com.giraffe.home.screen.movies_list.moviesListRoute
import com.giraffe.home.screen.movies_list.navigateToCollectionList
import com.giraffe.home.screen.movies_list.navigateToMoviesList


@Composable
fun HomeNavGraph(
    navController: NavHostController,
    detailsApi: DetailsApi,
    exploreApi: ExploreApi
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
        HomeRoute::class.qualifiedName -> HomeRoute
        ExploreRoute::class.qualifiedName -> ExploreRoute
        MatchRoute::class.qualifiedName -> MatchRoute
        ProfileRoute::class.qualifiedName -> ProfileRoute
        else -> null
    }

    var isBottomBarVisible by rememberSaveable {
        mutableStateOf(
            true
        )
    }

    LaunchedEffect(currentRouteString) {
        if (currentRouteString == HomeRoute::class.qualifiedName) {
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
            startDestination = HomeRoute,
            modifier = Modifier.weight(1f)
        ) {

            homeRoute(
                navigateToMoviesScreen = { sectionType, sectionTitle ->
                    navController.navigateToMoviesList(sectionType, sectionTitle)
                    isBottomBarVisible = false
                },
                navigateToMoviesDetailsScreen = {
                    navController.navigateToMovieDetails(it)
                    isBottomBarVisible = false
                },
                navigateToSeriesDetailsScreen = {
                    navController.navigateToSeriesDetails(it)
                    isBottomBarVisible = false
                },
                navigateToCollectionList = { collectionId, collectionTitle ->
                    navController.navigateToCollectionList(
                        collectionId = collectionId,
                        collectionTitle = collectionTitle
                    )
                },
                navigateToExploreScreen = {
                    navController.navigateToExplore()
                },
                navigateToMatchScreen = {
                    navController.navigateToMatch()
                }
            )

            moviesListRoute(
                onBackClick = {
                    navController.popBackStack()
                },
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

            composable<ExploreRoute> {
                exploreApi.ExploreContainer {
                    isBottomBarVisible = it
                }
            }

            composable<MatchRoute> {
                MatchScreen { isBottomBarVisible = it }
            }

            composable<ProfileRoute> {
                ProfileScreen { isBottomBarVisible = it }
            }
        }

        BottomNavigationBar(
            tabs = bottomTabs,
            selectedTabRoute = currentRoute,
            isBottomBarVisible = isBottomBarVisible,
            onTabSelected = { tab ->
                Log.d("Tab", "Tapping tab: ${tab.route}, current route: $currentRoute")
                val route =
                    navBackStackEntry?.destination?.route ?: return@BottomNavigationBar
                navController.navigate(tab.route) {
//                    popUpTo(navController.graph.startDestinationRoute.orEmpty()) {
                    popUpTo(route) {
                        saveState = true
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