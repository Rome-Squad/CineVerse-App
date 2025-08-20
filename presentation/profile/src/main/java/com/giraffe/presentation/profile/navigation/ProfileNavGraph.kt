package com.giraffe.presentation.profile.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import com.giraffe.api.authentication.AuthenticationApi
import com.giraffe.api.details.DetailsApi
import com.giraffe.api.home.HomeApi
import com.giraffe.presentation.profile.navigation.routes.MovieDetailsRoute
import com.giraffe.presentation.profile.navigation.routes.SeriesDetailsRoute
import com.giraffe.presentation.profile.navigation.routes.SettingsScreenRoute
import com.giraffe.presentation.profile.navigation.routes.collectionRoute
import com.giraffe.presentation.profile.navigation.routes.editProfileWebViewRoute
import com.giraffe.presentation.profile.navigation.routes.historyRoute
import com.giraffe.presentation.profile.navigation.routes.loginRoute
import com.giraffe.presentation.profile.navigation.routes.myCollectionsRoute
import com.giraffe.presentation.profile.navigation.routes.ratingsRoute
import com.giraffe.presentation.profile.navigation.routes.settingsScreenRoute

@Composable
internal fun ProfileNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestinationRoute: Any,
    homeApi: HomeApi,
    authenticationApi: AuthenticationApi,
    detailsApi: DetailsApi,
    onShowBottomBarChange: (Boolean) -> Unit = {},
    navigateBack: (() -> Unit)? = null,
) {


    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val isBottomBarVisible = currentRoute.orEmpty().endsWith(SettingsScreenRoute.toString())

    LaunchedEffect(currentRoute) {
        onShowBottomBarChange(isBottomBarVisible)
    }


    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestinationRoute,
    ) {
        settingsScreenRoute(navController = navController)

        editProfileWebViewRoute(navController = navController)

        historyRoute(
            navController = navController,
            homeApi = homeApi
        )

        ratingsRoute(
            navController = navController,
            homeApi = homeApi
        )

        myCollectionsRoute(
            navController = navController,
            homeApi = homeApi,
            navigateBack = {
                if (navigateBack != null) {
                    navigateBack()
                } else {
                    navController.navigateUp()
                }
            }
        )

        collectionRoute(
            navController = navController,
            homeApi = homeApi,
            navigateBack = {
                if (navigateBack != null) {
                    navigateBack()
                } else {
                    navController.navigateUp()
                }
            }
        )

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