package com.giraffe.home.screen.show_more.mathces_your_vibes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.home.R
import com.giraffe.home.screen.show_more.ShowMoreContent
import com.giraffe.home.screen.show_more.ShowMoreEffect
import com.giraffe.home.screen.show_more.recommendations.MatchesYourVibeViewModel
import com.giraffe.home.utils.EventListener

@Composable
fun MatchesYourVibesScreen(
    matchesYourVibeViewModel: MatchesYourVibeViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    navigateToMovieDetailsScreen: (Int) -> Unit,
    navigateToSeriesDetailsScreen: (Int) -> Unit,
) {
    val state by matchesYourVibeViewModel.state.collectAsState()
    EventListener(matchesYourVibeViewModel.effect) {
        when (it) {
            is ShowMoreEffect.NavigateToMovieDetails -> {
                navigateToMovieDetailsScreen(it.movieId)
            }

            is ShowMoreEffect.NavigateToSeriesDetails -> {
                navigateToSeriesDetailsScreen(it.seriesId)
            }

            is ShowMoreEffect.ShowError -> {}
        }
    }
    ShowMoreContent(
        state = state,
        showMoreInteractionListener = matchesYourVibeViewModel,
        onBackClick = onBackClick,
        title = stringResource(R.string.matches_your_vibe),
    )
}