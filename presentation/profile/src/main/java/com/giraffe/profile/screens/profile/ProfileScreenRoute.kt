package com.giraffe.profile.screens.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.profile.edit.navigateToEditProfileWebView
import kotlinx.serialization.Serializable

@Serializable
data object ProfileScreenRoute

fun NavGraphBuilder.profileScreenRoute(
    navController: NavController,
    onNavigateToAuth: () -> Unit
) {
    composable<ProfileScreenRoute> {
        SettingsScreen(
            onNavigateToEditProfileWebView = {
                navController.navigateToEditProfileWebView()
            },
            onNavigateToLogin = onNavigateToAuth
        )
    }
}