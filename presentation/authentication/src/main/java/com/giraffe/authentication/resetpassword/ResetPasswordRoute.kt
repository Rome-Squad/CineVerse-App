package com.giraffe.authentication.resetpassword

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.authentication.login.navigateLoginScreen
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