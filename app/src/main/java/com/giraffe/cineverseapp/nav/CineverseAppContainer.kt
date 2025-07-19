package com.giraffe.cineverseapp.nav

import android.util.Log
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
    val navigator = remember { AppNavigator() }

    CompositionLocalProvider(LocalAppNavigator provides navigator) {
        when (val screen = navigator.currentScreen) {
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
                        Log.d("TAG", "CineVerseAppContainer: $castId")
                        navigator.navigateTo(
                            screen = AppScreen.Details(
                                startDestination = DetailsStartDestination.Cast(castId)
                            )
                        )
                    }
                )
            }

            is AppScreen.Details -> {
                Log.d("TAG", "CineVerseAppContainer: details")
                detailsApi.DetailsContainer(
                    modifier = Modifier.fillMaxSize(),
                    startDestination = screen.startDestination
                )
            }
        }
    }
}