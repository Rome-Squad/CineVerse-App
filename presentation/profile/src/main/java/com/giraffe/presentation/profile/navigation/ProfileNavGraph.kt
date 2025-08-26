package com.giraffe.presentation.profile.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.giraffe.api.authentication.AuthenticationApi
import com.giraffe.api.details.DetailsApi
import com.giraffe.api.home.HomeApi
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
    val context = LocalContext.current
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
            homeApi = homeApi,
            navigateToMovieDetails = {detailsApi.launchMovieDetails(context, it)},
            navigateToSeriesDetails = {detailsApi.launchSeriesDetails(context, it)}
        )

        ratingsRoute(
            navController = navController,
            homeApi = homeApi,
            navigateToMovieDetails = {detailsApi.launchMovieDetails(context, it)},
            navigateToSeriesDetails = {detailsApi.launchSeriesDetails(context, it)}
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
            homeApi = homeApi,
            navigateToMovieDetails = {detailsApi.launchMovieDetails(context, it)},
            navigateBack = {
                if (navigateBack != null) {
                    navigateBack()
                } else {
                    navController.navigateUp()
                }
            }
        )

        loginRoute(authenticationApi)
    }

}