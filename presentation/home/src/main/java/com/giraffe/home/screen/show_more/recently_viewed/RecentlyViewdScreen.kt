package com.giraffe.home.screen.show_more.recently_viewed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.home.R
import com.giraffe.home.screen.show_more.ShowMoreContent
import com.giraffe.home.screen.show_more.ShowMoreEffect
import com.giraffe.home.utils.EventListener

@Composable
fun RecentlyViewedScreen(
    recentlyViewedViewModel: RecentlyViewedViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    navigateToMovieDetailsScreen: (Int) -> Unit,
    navigateToSeriesDetailsScreen: (Int) -> Unit,
) {
    val state by recentlyViewedViewModel.state.collectAsState()
    EventListener(recentlyViewedViewModel.effect) {
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
        showMoreInteractionListener = recentlyViewedViewModel,
        onBackClick = onBackClick,
        title = stringResource(R.string.you_recent_viewed),
    )
}