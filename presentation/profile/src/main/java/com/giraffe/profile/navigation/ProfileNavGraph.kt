
package com.giraffe.profile.navigation

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.giraffe.profile.edit.editProfileWebViewRoute
import com.giraffe.profile.screens.settings.SettingsScreenRoute
import com.giraffe.profile.screens.settings.settingsScreenRoute
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.giraffe.authentication.AuthenticationApi
import com.giraffe.details.DetailsApi
import com.giraffe.explore.ExploreApi
import com.giraffe.profile.screens.collections.collection.collectionRoute
import com.giraffe.profile.screens.collections.collection.navigateToCollection
import com.giraffe.profile.screens.collections.mycollections.myCollectionsRoute
import com.giraffe.profile.screens.collections.mycollections.navigateToMyCollections
import com.giraffe.profile.screens.history.historyRoute
import com.giraffe.profile.screens.history.navigateToHistory
import com.giraffe.profile.screens.ratings.navigateToRatings
import com.giraffe.profile.screens.ratings.ratingsRoute

@Composable
internal fun ProfileNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    detailsApi: DetailsApi,
    exploreApi: ExploreApi,
    authenticationApi: AuthenticationApi
) {
    val activity = LocalActivity.current

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = SettingsScreenRoute,
    ) {
        settingsScreenRoute(
            navController = navController,
            onNavigateToLogin = navController::navigateToAuthentication,
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
            exploreApi.ExploreContainer {  }
        }

        composable<AuthenticationRoute> {
            authenticationApi.LoginContainer(
                onBack = {
                    activity?.finish()
                },
                isLoggedIn = false,
                isOnboardingFirstTime = false
            )
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
