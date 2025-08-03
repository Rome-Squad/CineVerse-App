package com.giraffe.profile.screens.profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object SettingsRoute

fun NavGraphBuilder.settingsRoute() {
    composable<SettingsRoute> {
        SettingsScreen()
    }
}