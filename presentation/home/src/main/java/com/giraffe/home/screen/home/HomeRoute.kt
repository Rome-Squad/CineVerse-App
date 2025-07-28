package com.giraffe.home.screen.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

fun NavGraphBuilder.homeRoute(
    navigateToMoviesScreen: (String, String) -> Unit,
    navigateToMoviesDetailsScreen: (Int) -> Unit,
    navigateToSeriesDetailsScreen: (Int) -> Unit,
) {
    composable<HomeRoute> {
//        HomeScreen(
//            navigateToMoviesListScreen = navigateToMoviesScreen,
//            navigateToMoviesDetailsScreen = navigateToMoviesDetailsScreen,
//            navigateToSeriesDetailsScreen = navigateToSeriesDetailsScreen,
//        )
    }
}