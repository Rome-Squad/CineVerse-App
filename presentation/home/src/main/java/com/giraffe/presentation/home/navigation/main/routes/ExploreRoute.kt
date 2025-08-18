package com.giraffe.presentation.home.navigation.main.routes

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.giraffe.designsystem.composable.navbar.Route
import kotlinx.serialization.Serializable

@Serializable
object ExploreRoute : Route("explore")

fun NavHostController.navigateToExplore() {
    val navController = this

    navigate(ExploreRoute.route) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
            inclusive = true
        }
        launchSingleTop = true
        restoreState = true
    }
}