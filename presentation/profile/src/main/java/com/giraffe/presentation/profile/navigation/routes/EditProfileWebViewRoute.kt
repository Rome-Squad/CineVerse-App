package com.giraffe.presentation.profile.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.presentation.profile.screens.edit.EditProfileWebViewScreen
import com.giraffe.presentation.profile.screens.settings.SettingsViewModel
import kotlinx.serialization.Serializable

@Serializable
data object EditProfileWebViewRoute

fun NavController.navigateToEditProfileWebView() {
    navigate(EditProfileWebViewRoute)
}

fun NavGraphBuilder.editProfileWebViewRoute(
    settingsViewModel: SettingsViewModel,
    navController: NavController
) {
    composable<EditProfileWebViewRoute> {
        EditProfileWebViewScreen(
            viewModel = settingsViewModel,
            onBack = navController::popBackStack
        )
    }
}