package com.giraffe.profile.navigation

import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
internal object AuthenticationRoute

internal fun NavController.navigateToAuthentication() {
    navigate(AuthenticationRoute)
}