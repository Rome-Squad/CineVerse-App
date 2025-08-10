package com.giraffe.presentation.authentication.nav.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.presentation.authentication.screens.resetpassword.ResetPasswordWebViewScreen
import kotlinx.serialization.Serializable


@Serializable
data object ResetPasswordRoute


fun NavController.navigateToResetPassword() {
    navigate(ResetPasswordRoute)
}

fun NavGraphBuilder.resetPasswordRoute(
    navController: NavController
) {
    composable<ResetPasswordRoute> {
        ResetPasswordWebViewScreen(onBack = { navController.navigateLoginScreen() })
    }
}