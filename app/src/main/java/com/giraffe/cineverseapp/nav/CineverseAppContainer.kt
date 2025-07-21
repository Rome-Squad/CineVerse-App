package com.giraffe.cineverseapp.nav

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.giraffe.details.DetailsApi
import com.giraffe.details.DetailsStartDestination
import com.giraffe.explore.ExploreApi


@Composable
fun CineVerseAppContainer(
    exploreApi: ExploreApi,
    detailsApi: DetailsApi
) {

    val navigator = remember { AppNavigator<AppScreen>(AppScreen.Explore) }

    CompositionLocalProvider(LocalAppNavigator provides navigator) {
        val currentScreen = navigator.currentScreen

        BackHandler(enabled = currentScreen !is AppScreen.Explore) {
            navigator.navigateBack()
        }

        when (currentScreen) {
            is AppScreen.Explore -> {
                exploreApi.ExploreContainer(
                    modifier = Modifier.fillMaxSize(),
                    navigateToMovieDetails = { movieId ->
                        navigator.navigateTo(
                            screen = AppScreen.Details(
                                startDestination = DetailsStartDestination.Movie(movieId)
                            )
                        )
                    },
                    navigateToSeriesDetails = { seriesId ->
                        navigator.navigateTo(
                            screen = AppScreen.Details(
                                startDestination = DetailsStartDestination.Series(seriesId)
                            )
                        )
                    },
                    navigateToCastDetails = { castId ->
                        navigator.navigateTo(
                            screen = AppScreen.Details(
                                startDestination = DetailsStartDestination.Cast(castId)
                            )
                        )
                    }
                )
            }

            is AppScreen.Details -> {
                detailsApi.DetailsContainer(
                    modifier = Modifier.fillMaxSize(),
                    startDestination = currentScreen.startDestination,
                    backPress = {
                        navigator.navigateBack()
                    }
                )
            }
        }
    }
}