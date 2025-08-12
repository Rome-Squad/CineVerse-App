package com.giraffe.match.screen.result

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.giraffe.match.navigation.MatchRouteResult
import com.giraffe.match.screen.match_pager.MatchRoutePager
internal fun NavController.navigateToMatchResult(
    selectedGenres: List<Int>,
    moodSelections: List<Int>,
    timeSelection: Int? = null,
    releasePeriodSelection: String? = null
) {
    navigate(
        MatchRouteResult(
            selectedGenres = selectedGenres,
            moodSelections = moodSelections,
            timeSelection = timeSelection,
            releasePeriodSelection = releasePeriodSelection
        )
    ) {
        popUpTo(MatchRoutePager) { inclusive = true }
    }
}

fun NavGraphBuilder.matchRouteResult(
    onBackClick: () -> Unit,
    navigateToMovieDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit,
    navigateToYouTubePlayer: (String) -> Unit
) {
    composable<MatchRouteResult> { backStackEntry ->
        val args = backStackEntry.toRoute<MatchRouteResult>()

        MatchResultScreen(
            navigateBack = onBackClick,
            navigateToMoviesDetailsScreen = navigateToMovieDetails,
            navigateToSeriesDetailsScreen = navigateToSeriesDetails,
            navigateToYouTubePlayer = navigateToYouTubePlayer,
            selectedGenres = args.selectedGenres,
            moodSelections = args.moodSelections,
            timeSelection = args.timeSelection,
            releasePeriodSelection = args.releasePeriodSelection
        )
    }
}