package com.giraffe.presentation.authentication.nav.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.api.home.HomeApi
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

fun NavController.navigateHomeScreen() {
    navigate(HomeRoute)
}/*

fun NavGraphBuilder.homeRoute(
    homeApi: HomeApi,
) {
    composable<HomeRoute> {
        homeApi.MainContainer()
    }
}*/