package com.giraffe.profile.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import com.giraffe.details.DetailsApi
import com.giraffe.explore.ExploreApi
import com.giraffe.profile.edit.editProfileWebViewRoute
import com.giraffe.profile.screens.collections.collection.collectionRoute
import com.giraffe.profile.screens.collections.collection.navigateToCollection
import com.giraffe.profile.screens.collections.mycollections.myCollectionsRoute
import com.giraffe.profile.screens.collections.mycollections.navigateToMyCollections
import com.giraffe.profile.screens.history.historyRoute
import com.giraffe.profile.screens.history.navigateToHistory
import com.giraffe.profile.screens.ratings.navigateToRatings
import com.giraffe.profile.screens.ratings.ratingsRoute
import com.giraffe.profile.screens.settings.SettingsScreenRoute
import com.giraffe.profile.screens.settings.settingsScreenRoute

@Composable
internal fun ProfileNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    detailsApi: DetailsApi,
    exploreApi: ExploreApi,
    onShowBottomBarChange: (Boolean) -> Unit
) {


    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination
    val bottomBarRoutes = listOf(
        ExploreRoute::class,
        SettingsScreenRoute::class,
    )
    val isBottomBarVisible = currentRoute?.hierarchy?.any { navDestination ->
        navDestination.route?.let { route ->
            bottomBarRoutes.any { klass ->
                route.contains(klass.simpleName .orEmpty())
            }
        } == true
    } == true

    LaunchedEffect(currentRoute) {
        onShowBottomBarChange(isBottomBarVisible)
    }



    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = SettingsScreenRoute,
    ) {
        settingsScreenRoute(
            navController = navController,
            onNavigateToLogin = {  },
            onNavigateToMyCollections = navController::navigateToMyCollections,
            onNavigateToHistory = navController::navigateToHistory,
            onNavigateToRatings = navController::navigateToRatings,
        )

        editProfileWebViewRoute(navController)

        historyRoute(
            onBackClicked = navController::navigateUp,
            navigateToMoviesDetailsScreen = navController::navigateToMovieDetails,
            navigateToSeriesDetailsScreen = navController::navigateToSeriesDetails,
            navigateToExploreScreen = navController::navigateToExploreScreen
        )

        ratingsRoute()

        myCollectionsRoute(
            modifier = Modifier
                .fillMaxSize(),
            navigateBack = navController::navigateUp,
            navigateToCollection = {
                navController.navigateToCollection(
                    collectionId = it.id,
                    collectionName = it.name
                )
            },
            navigateToExploreScreen = navController::navigateToExploreScreen
        )

        collectionRoute(
            modifier = Modifier
                .fillMaxSize(),
            navigateBack = navController::navigateUp,
            navigateToMovieDetails = navController::navigateToMovieDetails
        )

        composable<ExploreRoute> {
            exploreApi.ExploreContainer { }
        }

        composable<SeriesDetailsRoute> { backStackEntry ->
            val seriesId = backStackEntry.toRoute<SeriesDetailsRoute>().seriesId
            detailsApi.SeriesDetailsContainer(
                seriesId = seriesId,
                onBackClick = navController::popBackStack
            )
        }

        composable<MovieDetailsRoute> { backStackEntry ->
            val movieId = backStackEntry.toRoute<MovieDetailsRoute>().movieId
            detailsApi.MovieDetailsContainer(
                movieId = movieId,
                onBackClick = navController::popBackStack
            )
        }
    }

}
