package com.giraffe.match.screen.result

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.match.navigation.MatchRouteResult
import com.giraffe.match.screen.match_pager.MatchRoutePager

internal fun NavController.navigateToMatchResult() {
    navigate(
        MatchRouteResult
    ) {
        popUpTo(MatchRoutePager) { inclusive = true }
    }
}


fun NavGraphBuilder.matchRouteResult(
    onBackClick: () -> Unit,
    navigateToMovieDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit,
    navigateToYouTubePlayer: (String) -> Unit,
    navigateToLoginScreen: () -> Unit,
) {
    composable<MatchRouteResult> { backStackEntry ->

        MatchResultScreen(
            navigateBack = onBackClick,
            navigateToMoviesDetailsScreen = navigateToMovieDetails,
            navigateToSeriesDetailsScreen = navigateToSeriesDetails,
            navigateToYouTubePlayer = navigateToYouTubePlayer,
            navigateToLoginScreen = navigateToLoginScreen
        )
    }

}
