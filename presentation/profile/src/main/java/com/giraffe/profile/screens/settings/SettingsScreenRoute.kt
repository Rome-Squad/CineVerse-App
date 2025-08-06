package com.giraffe.profile.screens.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.profile.edit.navigateToEditProfileWebView
import kotlinx.serialization.Serializable

@Serializable
data object SettingsScreenRoute

fun NavGraphBuilder.settingsScreenRoute(
    navController: NavController,
    onNavigateToLogin: () -> Unit,
    onNavigateToMyCollections: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToRatings: () -> Unit
) {
    composable<SettingsScreenRoute> {
        SettingsScreen(
            onNavigateToEditProfileWebView = {
                navController.navigateToEditProfileWebView()
            },
            onNavigateToLogin = onNavigateToLogin,
            onNavigateToHistory = onNavigateToHistory,
            onNavigateToRatings = onNavigateToRatings,
            onNavigateToMyCollections = onNavigateToMyCollections

        )
    }
}