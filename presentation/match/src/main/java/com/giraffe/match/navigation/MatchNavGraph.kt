package com.giraffe.match.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
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
    val context = LocalContext.current

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
            onFinish = { genres, moods, time, period ->
                navController.navigateToMatchResult(genres, moods, time, period)
            }
        )

        matchRouteResult(
            onBackClick = navController::popBackStack,
            navigateToMovieDetails = { detailsApi.launchMovieDetails(context, it) },
            navigateToSeriesDetails = { detailsApi.launchSeriesDetails(context, it) },
            navigateToYouTubePlayer = { videoId ->
                navController.navigateToYouTubePlayer(videoId)
            },
            navigateToLoginScreen = navController::navigateLoginScreen
        )

        loginRoute(authApi)

        youTubePlayerRouteRoute(
            onBackClick = navController::popBackStack
        )
    }
}
