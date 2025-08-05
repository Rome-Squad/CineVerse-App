package com.giraffe.profile.edit

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object EditProfileWebViewRoute

fun NavController.navigateToEditProfileWebView() {
    navigate(EditProfileWebViewRoute)
}

fun NavGraphBuilder.editProfileWebViewRoute(
    navController: NavController
) {
    composable<EditProfileWebViewRoute> {
        EditProfileWebViewScreen(onBack = { navController.popBackStack() })
    }
}