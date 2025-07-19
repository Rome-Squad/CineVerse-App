package com.giraffe.cineverseapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.giraffe.details.DetailsApi
import com.giraffe.details.navigation.CastDetailsRoute
import com.giraffe.details.navigation.MovieDetailsRoute
import com.giraffe.details.navigation.SeriesDetailsRoute
import com.giraffe.explore.ExploreApi
import com.giraffe.explore.navigation.DiscoverRoute

@Composable
fun AppNavigation(
    exploreApi: ExploreApi,
    detailsApi: DetailsApi,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = DiscoverRoute
    ) {

        with(exploreApi) {
            exploreGraph(
                navController = navController,
                navigateToMovieDetails = { movieId ->
                    navController.navigate(MovieDetailsRoute(movieId))
                },
                navigateToSeriesDetails = { seriesId ->
                    navController.navigate(SeriesDetailsRoute(seriesId))
                }
            )
            searchGraph(
                navController = navController,
                navigateToDetails = { personId ->
                    navController.navigate(CastDetailsRoute(personId))
                }
            )
            searchResultGraph(
                navController = navController,
                navigateToMovieDetails = { movieId ->
                    navController.navigate(MovieDetailsRoute(movieId))
                },
                navigateToSeriesDetails = { seriesId ->
                    navController.navigate(SeriesDetailsRoute(seriesId))
                },
                navigateToCastDetails = { personId ->
                    navController.navigate(CastDetailsRoute(personId))
                }
            )
        }

        with(detailsApi) {
            castDetailsGraph(
                navController = navController,
            )
            castGalleryGraph(
                navController = navController,
            )
            castCreditsGraph(
                navController = navController,
            )
            movieDetailsGraph(
                navController = navController
            )
            movieRecommendationGraph(
                navController = navController,
            )
            movieCastsGraph(
                navController = navController,
            )
            movieReviewsCastsGraph(
                navController = navController,
            )
            seriesDetailsGraph(
                navController = navController,
            )
            seriesRecommendationGraph(
                navController = navController,
            )
            seriesCastsGraph(
                navController = navController,
            )
            seriesReviewsGraph(
                navController = navController,
            )
            seasonGraph(
                navController = navController,
            )
        }
    }
}