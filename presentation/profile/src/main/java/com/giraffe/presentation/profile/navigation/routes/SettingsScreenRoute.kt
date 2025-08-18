package com.giraffe.presentation.profile.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.presentation.profile.screens.settings.SettingsScreen
import com.giraffe.presentation.profile.screens.settings.SettingsViewModel
import kotlinx.serialization.Serializable

@Serializable
data object SettingsScreenRoute

fun NavGraphBuilder.settingsScreenRoute(
    settingsViewModel: SettingsViewModel,
    navController: NavController,
    onNavigateToLogin: () -> Unit,
    onNavigateToMyCollections: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToRatings: () -> Unit
) {
    composable<SettingsScreenRoute> { backStackEntry ->
        SettingsScreen(
            viewModel = settingsViewModel,
            onNavigateToEditProfileWebView = navController::navigateToEditProfileWebView,
            onNavigateToLogin = onNavigateToLogin,
            onNavigateToHistory = onNavigateToHistory,
            onNavigateToRatings = onNavigateToRatings,
            onNavigateToMyCollections = onNavigateToMyCollections
        )
    }
}