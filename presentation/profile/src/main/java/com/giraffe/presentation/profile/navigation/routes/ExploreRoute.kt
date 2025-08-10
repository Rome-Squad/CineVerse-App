package com.giraffe.presentation.profile.navigation.routes

import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
internal object ExploreRoute

internal fun NavController.navigateToExploreScreen() {
    navigate(ExploreRoute)
}