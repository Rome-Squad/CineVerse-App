package com.giraffe.cineverseapp.nav

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import com.giraffe.authentication.AuthenticationApi
import com.giraffe.details.DetailsApi
import com.giraffe.details.DetailsStartDestination
import com.giraffe.explore.ExploreApi


@Composable
fun CineVerseAppContainer(
    exploreApi: ExploreApi,
    detailsApi: DetailsApi,
    authenticationApi: AuthenticationApi
) {

    val navigator = remember { AppNavigator<AppScreen>(AppScreen.Authentication) }

    CompositionLocalProvider(LocalAppNavigator provides navigator) {
        val currentScreen = navigator.currentScreen

        BackHandler(enabled = currentScreen !is AppScreen.Authentication) {
            navigator.navigateBack()
        }

        when (currentScreen) {
            is AppScreen.Explore -> {
                exploreApi.ExploreContainer(
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
                    startDestination = currentScreen.startDestination,
                    backPress = {
                        navigator.navigateBack()
                    }
                )
            }

            is AppScreen.Authentication -> {
                authenticationApi.LoginContainer {
                     navigator.navigateBack()
                }
            }
        }
    }
}