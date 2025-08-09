package com.giraffe.presentation.home.navigation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.giraffe.api.details.DetailsApi
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.home.navigation.main.CollectionRoute
import com.giraffe.presentation.home.navigation.main.MovieDetailsRoute
import com.giraffe.presentation.home.navigation.main.SeriesDetailsRoute
import com.giraffe.presentation.home.navigation.main.YourCollectionsRoute
import com.giraffe.presentation.home.navigation.main.navigateToCollection
import com.giraffe.presentation.home.navigation.main.navigateToMovieDetails
import com.giraffe.presentation.home.navigation.main.navigateToSeriesDetails
import com.giraffe.presentation.home.navigation.main.navigateToYourCollections
import com.giraffe.presentation.home.screen.home.HomeRoute
import com.giraffe.presentation.home.screen.home.homeRoute
import com.giraffe.presentation.home.screen.movies_list.moviesListRoute
import com.giraffe.presentation.home.screen.movies_list.navigateToMoviesList
import com.giraffe.profile.ProfileApi


@Composable
fun HomeNavGraph(
    detailsApi: DetailsApi,
    profileApi: ProfileApi,
    navigateToExplore: () -> Unit,
    navigateToMatch: () -> Unit,
    onShowBottomBar: (Boolean) -> Unit,
) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination
    val bottomBarRoutes = listOf(
        HomeRoute::class,
    )
    val isBottomBarVisible = currentRoute?.hierarchy?.any { navDestination ->
        navDestination.route?.let { route ->
            bottomBarRoutes.any { klass ->
                route.contains(klass.simpleName.orEmpty())
            }
        } == true
    } == true
    LaunchedEffect(currentRoute) {
        onShowBottomBar(isBottomBarVisible)
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
                },
                navigateToMoviesDetailsScreen = {
                    navController.navigateToMovieDetails(it)
                },
                navigateToSeriesDetailsScreen = {
                    navController.navigateToSeriesDetails(it)
                },
                navigateToCollectionList = navController::navigateToCollection,
                navigateToYourCollection = navController::navigateToYourCollections,
                navigateToExploreScreen = navigateToExplore,
                navigateToMatchScreen = navigateToMatch,
                navigateToCollection = navController::navigateToCollection
            )

            moviesListRoute(
                onBackClick = navController::popBackStack,
                navigateToMoviesDetailsScreen = {
                    navController.navigateToMovieDetails(it)
                },
                navigateToSeriesDetailsScreen = {
                    navController.navigateToSeriesDetails(it)
                }
            )


            composable<YourCollectionsRoute> {
                profileApi.YourCollectionsContainer(
                    navigateBack = {
                        navController.popBackStack()
                    },
                    onShowBottomBarChange = {
                        onShowBottomBar(it)
                    }
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
                    onShowBottomBar(it)
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
        }

    }

}