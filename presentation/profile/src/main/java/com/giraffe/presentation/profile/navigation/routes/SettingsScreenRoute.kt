package com.giraffe.presentation.profile.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.presentation.profile.screens.settings.SettingsScreen
import kotlinx.serialization.Serializable

@Serializable
data object SettingsScreenRoute

fun NavGraphBuilder.settingsScreenRoute(
    navController: NavController,
) {
    composable<SettingsScreenRoute> { backStackEntry ->
        SettingsScreen(
            onNavigateToEditProfileWebView = navController::navigateToEditProfileWebView,
            onNavigateToLogin = navController::navigateLoginScreen,
            onNavigateToHistory = navController::navigateToHistory,
            onNavigateToRatings = navController::navigateToRatings,
            onNavigateToMyCollections = navController::navigateToMyCollections,
        )
    }
}