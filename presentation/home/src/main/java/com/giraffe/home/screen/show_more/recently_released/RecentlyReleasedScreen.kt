package com.giraffe.home.screen.show_more.recently_released

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
fun RecentlyReleasedScreen(
    recentlyReleasedViewModel: RecentlyReleasedViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    navigateToMovieDetailsScreen: (Int) -> Unit,
    navigateToSeriesDetailsScreen: (Int) -> Unit,
) {
    val state by recentlyReleasedViewModel.state.collectAsState()
    EventListener(recentlyReleasedViewModel.effect) {
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
        showMoreInteractionListener = recentlyReleasedViewModel,
        onBackClick = onBackClick,
        title = stringResource(R.string.recently_released),
    )
}