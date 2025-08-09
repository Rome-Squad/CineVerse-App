package com.giraffe.presentation.home.navigation.main

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.giraffe.designsystem.composable.navbar.BottomTab
import com.giraffe.designsystem.composable.navbar.Route
import kotlinx.serialization.Serializable


@Serializable
object MatchRoute : Route("match")

class MatchTab(
    override val labelRes: Int,
    override val iconRes: Int
) : BottomTab<MatchRoute> {
    override val route = MatchRoute
}

fun NavHostController.navigateToMatch() {
    val navController = this

    navigate(MatchRoute.route) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
            inclusive = true
        }
        launchSingleTop = true
        restoreState = true
    }
}