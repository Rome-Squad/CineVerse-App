package com.giraffe.home.screen.show_more.top_rated_tv_shows

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
fun TopRatedTvShowsScreen(
    topRatedTvShowsViewModel: TopRatedTvShowsViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
    navigateToMoviesDetailsScreen: (Int) -> Unit,
    navigateToSeriesDetailsScreen: (Int) -> Unit,
) {
    val state by topRatedTvShowsViewModel.state.collectAsState()
    EventListener(topRatedTvShowsViewModel.effect) {
        when (it) {
            is ShowMoreEffect.NavigateToMovieDetails -> {
                navigateToMoviesDetailsScreen(it.movieId)
            }

            is ShowMoreEffect.NavigateToSeriesDetails -> {
                navigateToSeriesDetailsScreen(it.seriesId)
            }

            is ShowMoreEffect.ShowError -> {}
        }
    }
    ShowMoreContent(
        state = state,
        showMoreInteractionListener = topRatedTvShowsViewModel,
        onBackClick = onBackClick,
        title = stringResource(R.string.top_rated_tv_shows),
    )
}