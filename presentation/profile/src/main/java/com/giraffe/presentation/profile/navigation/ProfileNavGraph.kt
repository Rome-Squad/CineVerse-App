package com.giraffe.presentation.profile.navigation

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
import com.giraffe.api.authentication.AuthenticationApi
import com.giraffe.api.details.DetailsApi
import com.giraffe.api.explore.ExploreApi
import com.giraffe.presentation.profile.navigation.routes.ExploreRoute
import com.giraffe.presentation.profile.navigation.routes.MovieDetailsRoute
import com.giraffe.presentation.profile.navigation.routes.SeriesDetailsRoute
import com.giraffe.presentation.profile.navigation.routes.SettingsScreenRoute
import com.giraffe.presentation.profile.navigation.routes.collectionRoute
import com.giraffe.presentation.profile.navigation.routes.historyRoute
import com.giraffe.presentation.profile.navigation.routes.loginRoute
import com.giraffe.presentation.profile.navigation.routes.myCollectionsRoute
import com.giraffe.presentation.profile.navigation.routes.navigateLoginScreen
import com.giraffe.presentation.profile.navigation.routes.navigateToCollection
import com.giraffe.presentation.profile.navigation.routes.navigateToExploreScreen
import com.giraffe.presentation.profile.navigation.routes.navigateToHistory
import com.giraffe.presentation.profile.navigation.routes.navigateToMovieDetails
import com.giraffe.presentation.profile.navigation.routes.navigateToMyCollections
import com.giraffe.presentation.profile.navigation.routes.navigateToRatings
import com.giraffe.presentation.profile.navigation.routes.navigateToSeriesDetails
import com.giraffe.presentation.profile.navigation.routes.ratingsRoute
import com.giraffe.presentation.profile.navigation.routes.settingsScreenRoute
import editProfileWebViewRoute


@Composable
internal fun ProfileNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestinationRoute: Any,
    authenticationApi: AuthenticationApi,
    detailsApi: DetailsApi,
    exploreApi: ExploreApi,
    onShowBottomBarChange: (Boolean) -> Unit,
    navigateBack: (() -> Unit)? = null,
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
                route.contains(klass.simpleName.orEmpty())
            }
        } == true
    } == true

    LaunchedEffect(currentRoute) {
        onShowBottomBarChange(isBottomBarVisible)
    }



    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestinationRoute,
    ) {
        settingsScreenRoute(
            navController = navController,
            onNavigateToLogin = navController::navigateLoginScreen,
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

        ratingsRoute(
            navigateBack = navController::navigateUp,
            navigateToMovieDetails = navController::navigateToMovieDetails,
            navigateToSeriesDetails = navController::navigateToSeriesDetails
        )

        myCollectionsRoute(
            navigateBack = {
                if (navigateBack != null) {
                    navigateBack()
                } else {
                    navController.navigateUp()
                }
            },
            navigateToCollection = {
                navController.navigateToCollection(
                    collectionId = it.id,
                    collectionName = it.name
                )
            },
            navigateToExploreScreen = navController::navigateToExploreScreen
        )

        collectionRoute(
            navigateBack = {
                if (navigateBack != null) {
                    navigateBack()
                } else {
                    navController.navigateUp()
                }
            },
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

        loginRoute(authenticationApi)
    }

}