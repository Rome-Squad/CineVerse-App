package com.giraffe.cineverseapp.nav

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
/*

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

*/


class AppNavigator<T>(
    val initialScreen: T
) {

    var backStack = NavBackStack(initialScreen)
        private set
    var currentScreen by mutableStateOf(initialScreen)
        private set

    init {
        currentScreen = backStack.seek()
    }

    fun navigateTo(screen: T) {
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

val LocalAppNavigator = staticCompositionLocalOf<AppNavigator<AppScreen>> {
    error("AppNavigator not provided")
}


class NavBackStack<T>(
    val initialScreen: T
) {
    private var data by mutableStateOf<List<T>>(listOf(initialScreen))

    fun push(entry: T) {
        val newData = data.toMutableList()

        newData.add(
            entry
        )

        updateData(
            newData = newData
        )
    }

    fun pop(): T? {
        if (data.size <= 1) return null

        val newData = data.toMutableList()
        newData.removeAt(size() - 1)
        data = newData

        return data.last()
    }

    fun seek() = data.last()

    fun size() = data.size

    private fun updateData(newData: List<T>) {
        data = newData
    }
}
