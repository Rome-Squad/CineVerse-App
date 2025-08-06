package com.giraffe.home.screen.show_more

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.home.screen.home.MediaType

@Composable
fun MoviesListScreen(
    moviesListViewModel: ShowMoreViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
    navigateToMoviesDetailsScreen: (Int) -> Unit,
    navigateToSeriesDetailsScreen: (Int) -> Unit,
) {
    val state by moviesListViewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        moviesListViewModel.effect.collect { effect ->
            when (effect) {
                is ShowMoreEffect.NavigateToMovieDetails -> {
                    navigateToMoviesDetailsScreen(effect.movieId)
                }

                is ShowMoreEffect.NavigateToSeriesDetails -> {
                    navigateToSeriesDetailsScreen(effect.seriesId)
                }

                is ShowMoreEffect.ShowError -> {}
            }
        }
    }
    ShowMoreContent(
        state = state,
        showMoreInteractionListener = moviesListViewModel,
        onBackClick = onBackClick,
        title = state.title
    )
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
fun MoviesListPreview() {
    val interactionListener = object : ShowMoreInteractionListener {
        override fun onViewChanged(isGrid: Boolean) {}
        override fun onMediaClicked(mediaId: Int, mediaType: MediaType) {}
    }
    ShowMoreContent(
        state = ShowMoreUiState(),
        showMoreInteractionListener = interactionListener,
        onBackClick = {},
        title = "title",
    )
}