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


/*

class AppNavigator(
    val initialScreen: AppScreen
) {

    var backStack = NavBackStack(initialScreen)
        private set
    var currentScreen by mutableStateOf<AppScreen>(initialScreen)
        private set

    init {
        currentScreen = backStack.seek()
    }

    fun navigateTo(screen: AppScreen) {
        backStack.push(screen)
        updateCurrentScreen()
        Log.d("TAG", "navigateTo: $currentScreen")
    }

    fun navigateBack() {
        backStack.pop()
        updateCurrentScreen()
    }

    fun updateCurrentScreen() {
        currentScreen = backStack.seek()
    }
}

val LocalAppNavigator = staticCompositionLocalOf<AppNavigator> {
    error("AppNavigator not provided")
}


class NavBackStack(
    val initialScreen: AppScreen
) {
    private var data by mutableStateOf<List<AppScreen>>(listOf(initialScreen))

    fun push(entry: AppScreen) {
        val newData = data.toMutableList()
        newData.add(
            index = size() - 1,
            entry
        )
        updateData(
            newData = newData
        )
    }

    fun pop(): AppScreen {
        val newData = data.toMutableList()
        val previousScreen = newData.removeAt(
            index = size() - 1
        )
        updateData(
            newData = newData
        )
        return previousScreen
    }

    fun seek(): AppScreen{
        return data[size() - 1]
    }

    fun size(): Int {
        return data.size
    }

    private fun updateData(newData: List<AppScreen>) {
        data = newData
    }
}
 */