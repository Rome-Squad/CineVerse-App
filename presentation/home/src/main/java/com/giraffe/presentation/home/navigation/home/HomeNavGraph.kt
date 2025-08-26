package com.giraffe.presentation.home.navigation.home

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.giraffe.api.details.DetailsApi
import com.giraffe.api.profile.ProfileApi
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.home.navigation.home.routes.CollectionRoute
import com.giraffe.presentation.home.navigation.home.routes.HomeRoute
import com.giraffe.presentation.home.navigation.home.routes.YourCollectionsRoute
import com.giraffe.presentation.home.navigation.home.routes.categoryMediaRoute
import com.giraffe.presentation.home.navigation.home.routes.homeRoute
import com.giraffe.presentation.home.navigation.home.routes.navigateToCategoryMedia
import com.giraffe.presentation.home.navigation.home.routes.navigateToCollection
import com.giraffe.presentation.home.navigation.home.routes.navigateToYourCollections
import com.giraffe.presentation.home.navigation.main.routes.HomeRoute.route as HomeNavGraphRoute


@Composable
fun HomeNavGraph(
    detailsApi: DetailsApi,
    profileApi: ProfileApi,
    navigateToExplore: () -> Unit,
    navigateToMatch: () -> Unit,
    onShowBottomBar: (Boolean) -> Unit,
) {
    val context = LocalContext.current
    val navController = rememberNavController()
    val startDestination = HomeRoute

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val isBottomBarVisible = currentRoute.orEmpty().endsWith(startDestination.toString())

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
            startDestination = startDestination,
            modifier = Modifier.weight(1f)
        ) {
            homeRoute(
                navigateToCategoryMediaSection = {
                    navController.navigateToCategoryMedia(it)
                },
                navigateToMoviesDetailsScreen = {detailsApi.launchMovieDetails(context, it)},
                navigateToSeriesDetailsScreen = {detailsApi.launchSeriesDetails(context, it)},
                navigateToCollectionList = navController::navigateToCollection,
                navigateToYourCollection = navController::navigateToYourCollections,
                navigateToExploreScreen = navigateToExplore,
                navigateToMatchScreen = navigateToMatch,
                navigateToCollection = navController::navigateToCollection
            )

            categoryMediaRoute(
                onBackClick = navController::popBackStack,
                navigateToMoviesDetailsScreen = {detailsApi.launchMovieDetails(context, it)},
                navigateToSeriesDetailsScreen = {detailsApi.launchSeriesDetails(context, it)}
            )


            composable<YourCollectionsRoute> {
                profileApi.YourCollectionsContainer(
                    navigateBack = {
                        navController.popBackStack()
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
                )
            }

        }

    }

    val activity = LocalActivity.current
    val isAtRoot =
        navBackStackEntry?.destination?.route == HomeRoute::class.qualifiedName || navBackStackEntry?.destination?.route == HomeNavGraphRoute
    BackHandler(isAtRoot) { activity?.finish() }
}