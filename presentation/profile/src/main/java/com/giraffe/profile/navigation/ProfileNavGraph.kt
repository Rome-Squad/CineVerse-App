package com.giraffe.profile.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.giraffe.profile.screens.profile.SettingsRoute
import com.giraffe.profile.screens.profile.settingsRoute

@Composable
internal fun ProfileNavGraph(
    navController: NavHostController,
    onShowBottomBarChange: (Boolean) -> Unit
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination
    LaunchedEffect(currentRoute) {
        onShowBottomBarChange(true)
    }

    NavHost(
        navController = navController,
        startDestination = SettingsRoute
    ) {
        settingsRoute()
    }
}