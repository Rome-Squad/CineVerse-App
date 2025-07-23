package com.giraffe.authentication.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.giraffe.authentication.AuthApi
import com.giraffe.authentication.login.WebViewRoute
import com.giraffe.authentication.login.webViewRoute
import androidx.navigation.compose.composable

@Composable
internal fun AuthNavGraph(
    navController: NavHostController,
    authApi : AuthApi,
    //TODO home api should be passed here
){

    NavHost(
        navController = navController,
        startDestination = "",
    ){
        webViewRoute(navController)
    }


//    composable<HomeRoute>{
//        homeApi.HomeContainer()
//    }

    composable<WebViewRoute>{
        authApi.WebViewContainer(
            onBack = navController.popBackStack()
        )
    }
}