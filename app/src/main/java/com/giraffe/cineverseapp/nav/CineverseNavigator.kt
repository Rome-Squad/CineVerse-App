package com.giraffe.cineverseapp.nav

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf

class AppNavigator {
    var currentScreen by mutableStateOf<AppScreen>(AppScreen.Explore)
        private set

    fun navigateTo(screen: AppScreen) {
        currentScreen = screen
        Log.d("TAG", "navigateTo: $currentScreen")
    }
}

val LocalAppNavigator = staticCompositionLocalOf<AppNavigator> {
    error("AppNavigator not provided")
}
