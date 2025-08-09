package com.giraffe.presentation.home.navigation.main

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.giraffe.designsystem.composable.navbar.BottomTab
import com.giraffe.designsystem.composable.navbar.Route
import kotlinx.serialization.Serializable

@Serializable
object ExploreRoute : Route("explore")

class ExploreTab(
    override val labelRes: Int,
    override val iconRes: Int
) : BottomTab<ExploreRoute> {
    override val route = ExploreRoute
}

fun NavHostController.navigateToExplore() {
    val navController = this

    navigate(ExploreRoute.route){
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
            inclusive = true
        }
        launchSingleTop = true
        restoreState = true
    }
}