package com.giraffe.home.screen.show_more.upcoming_movies

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
fun UpcomingMoviesScreen(
    upcomingMoviesViewModel: UpcomingMoviesViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
    navigateToMoviesDetailsScreen: (Int) -> Unit,
    navigateToSeriesDetailsScreen: (Int) -> Unit,
) {
    val state by upcomingMoviesViewModel.state.collectAsState()
    EventListener(events = upcomingMoviesViewModel.effect) {
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
        showMoreInteractionListener = upcomingMoviesViewModel,
        onBackClick = onBackClick,
        title = stringResource(R.string.upcoming_movies),
    )
}