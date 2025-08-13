package com.giraffe.match.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import com.giraffe.api.authentication.AuthenticationApi
import com.giraffe.api.details.DetailsApi
import com.giraffe.match.screen.MatchRouteStart
import com.giraffe.match.screen.matchRouteStart
import com.giraffe.match.screen.match_pager.matchPagerRoute
import com.giraffe.match.screen.match_pager.navigateToMatchPager
import com.giraffe.match.screen.result.matchRouteResult
import com.giraffe.match.screen.result.navigateToMatchResult
import com.giraffe.match.screen.videoPlayer.navigateToYouTubePlayer
import com.giraffe.match.screen.videoPlayer.youTubePlayerRouteRoute


@Composable
internal fun MatchNavGraph(
    navController: NavHostController,
    detailsApi: DetailsApi,
    authApi: AuthenticationApi,
    onShowBottomBarChange: (Boolean) -> Unit
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val startDestination = MatchRouteStart

    val isBottomBarVisible = currentRoute.orEmpty().endsWith(startDestination.toString())

    LaunchedEffect(currentRoute) {
        onShowBottomBarChange(isBottomBarVisible)
    }

    NavHost(
        navController = navController,
        startDestination = MatchRouteStart
    ) {
        matchRouteStart(
            onStartMatchingClick = navController::navigateToMatchPager
        )

        matchPagerRoute(
            onBackClick = navController::popBackStack,
            onFinish = { navController.navigateToMatchResult() }
        )

        matchRouteResult(
            onBackClick = navController::popBackStack,
            navigateToMovieDetails = { movieId ->
                navController.navigateToMovieDetails(movieId)
            },
            navigateToSeriesDetails = { seriesId ->
                navController.navigateToSeriesDetails(seriesId)
            },
            navigateToYouTubePlayer = { videoId ->
                navController.navigateToYouTubePlayer(videoId)
            },
            navigateToLoginScreen = {
                navController.navigateLoginScreen()
            }
        )

        composable<SeriesDetailsRoute> { backStackEntry ->
            val seriesId = backStackEntry.toRoute<SeriesDetailsRoute>().seriesId
            detailsApi.SeriesDetailsContainer(seriesId) {
                navController.popBackStack()
            }
        }

        loginRoute(authApi)

        composable<MovieDetailsRoute> { backStackEntry ->
            val movieId = backStackEntry.toRoute<MovieDetailsRoute>().movieId
            detailsApi.MovieDetailsContainer(movieId) {
                navController.popBackStack()
            }
        }

        youTubePlayerRouteRoute(
            onBackClick = navController::popBackStack
        )
    }
}
