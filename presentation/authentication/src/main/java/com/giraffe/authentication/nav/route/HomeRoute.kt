package com.giraffe.authentication.nav.route

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.home.HomeApi
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

fun NavController.navigateHomeScreen() {
    navigate(HomeRoute)
}

fun NavGraphBuilder.homeRoute(
    homeApi: HomeApi,
) {
    composable<HomeRoute> {
        val activity = LocalActivity.current
        BackHandler { activity?.finish() }
        homeApi.MainContainer()
    }
}