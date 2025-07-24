package com.giraffe.authentication.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.giraffe.authentication.login.webViewRoute
import com.giraffe.authentication.screen.LoginRoute
import com.giraffe.authentication.screen.loginRoute
import com.giraffe.explore.ExploreApi
import com.giraffe.explore.screen.discover.DiscoverRoute

@Composable
internal fun AuthenticationNavGraph(
    navController: NavHostController,
    //TODO home api should be passed here
    exploreApi:ExploreApi
){

    NavHost(
        navController = navController,
        startDestination = LoginRoute,
    ){
        webViewRoute(navController)

        loginRoute(navController)

        composable<DiscoverRoute> {

            exploreApi.ExploreContainer ({},{},{})


        }

    }
}