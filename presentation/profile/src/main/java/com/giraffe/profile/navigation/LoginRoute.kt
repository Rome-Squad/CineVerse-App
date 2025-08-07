package com.giraffe.profile.navigation

import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
internal object LoginRoute

internal fun NavController.navigateToLogin() {
    navigate(LoginRoute)
}