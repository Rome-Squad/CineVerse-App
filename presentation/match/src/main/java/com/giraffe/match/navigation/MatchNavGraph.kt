package com.giraffe.match.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.giraffe.api.details.DetailsApi
import com.giraffe.match.screen.match_pager.MatchRoutePager
import com.giraffe.match.screen.match_pager.matchPagerRoute
import com.giraffe.match.screen.result.matchRoute
import com.giraffe.match.screen.result.navigateToMatchResult
import com.giraffe.match.screen.videoPlayer.navigateToYouTubePlayer
import com.giraffe.match.screen.videoPlayer.youTubePlayerRouteRoute


@Composable
internal fun MatchNavGraph(
    navController: NavHostController,
    detailsApi: DetailsApi
) {
    NavHost(
        navController = navController,
        startDestination = MatchRoutePager
    ) {

        matchPagerRoute(
            onBackClick = { navController.popBackStack() },
            onFinish = { navController.navigateToMatchResult() }
        )

        matchRoute(
            onBackButtonClick = { navController.popBackStack() },
            navigateToMovieDetails = { movieId ->
                navController.navigateToMovieDetails(movieId)
            },
            navigateToSeriesDetails = { seriesId ->
                navController.navigateToSeriesDetails(seriesId)
            },
            navigateToYouTubePlayer = { videoId ->
                navController.navigateToYouTubePlayer(videoId)
            }
        )

        composable<SeriesDetailsRoute> { backStackEntry ->
            val seriesId = backStackEntry.toRoute<SeriesDetailsRoute>().seriesId
            detailsApi.SeriesDetailsContainer(seriesId) {
                navController.popBackStack()
            }
        }

        composable<MovieDetailsRoute> { backStackEntry ->
            val movieId = backStackEntry.toRoute<MovieDetailsRoute>().movieId
            detailsApi.MovieDetailsContainer(movieId) {
                navController.popBackStack()
            }
        }

        youTubePlayerRouteRoute(
            onBackClick = { navController.popBackStack() }
        )
    }
}
