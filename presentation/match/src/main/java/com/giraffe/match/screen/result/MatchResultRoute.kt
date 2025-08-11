package com.giraffe.match.screen.result

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.match.screen.match_pager.MatchRoutePager
import kotlinx.serialization.Serializable

@Serializable
internal object MatchRouteResult

internal fun NavController.navigateToMatchResult() {
    navigate(MatchRouteResult) {
        popUpTo(MatchRoutePager) { inclusive = true }
    }
}

fun NavGraphBuilder.matchRouteResult(
    onBackClick: () -> Unit,
    navigateToMovieDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit,
    navigateToYouTubePlayer: (String) -> Unit
) {
    composable<MatchRouteResult> {
        MatchResultScreen(
            navigateBack = onBackClick,
            navigateToMoviesDetailsScreen = navigateToMovieDetails,
            navigateToSeriesDetailsScreen = navigateToSeriesDetails,
            navigateToYouTubePlayer = navigateToYouTubePlayer
        )
    }
}
