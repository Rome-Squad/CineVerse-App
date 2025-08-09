package com.giraffe.presentation.home.navigation.main

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.giraffe.designsystem.composable.navbar.BottomTab
import com.giraffe.designsystem.composable.navbar.Route
import kotlinx.serialization.Serializable


@Serializable
object ProfileRoute : Route("profile")

class ProfileTab(
    override val labelRes: Int,
    override val iconRes: Int
) : BottomTab<ProfileRoute> {
    override val route = ProfileRoute
}

fun NavHostController.navigateToProfile() {
    val navController = this
    navigate(ProfileRoute.route) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
            inclusive = true
        }
        launchSingleTop = true
        restoreState = true
    }
}