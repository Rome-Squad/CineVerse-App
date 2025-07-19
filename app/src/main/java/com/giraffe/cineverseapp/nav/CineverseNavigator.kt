package com.giraffe.cineverseapp.nav

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf

class AppNavigator {
    var currentScreen by mutableStateOf<AppScreen>(AppScreen.Explore)
        private set

    fun navigateTo(screen: AppScreen) {
        currentScreen = screen
    }
}

val LocalAppNavigator = staticCompositionLocalOf<AppNavigator> {
    error("AppNavigator not provided")
}
