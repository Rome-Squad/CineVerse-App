package com.giraffe.home.screen.movies_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.ViewToggle
import com.giraffe.designsystem.theme.Theme
import com.giraffe.home.components.ListTitleSection
import com.giraffe.home.components.TransitionLazyColumnToGrid
import com.giraffe.home.screen.home.MediaType
import org.koin.androidx.compose.koinViewModel

@Composable
fun MoviesListScreen(
    moviesListViewModel: MoviesListViewModel = koinViewModel(),
    onBackClick: () -> Unit = {},
    navigateToMoviesDetailsScreen: (Int) -> Unit,
    navigateToSeriesDetailsScreen: (Int) -> Unit,
) {
    val state by moviesListViewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        moviesListViewModel.effect.collect { effect ->
            when (effect) {
                is MoviesListEffect.NavigateToMovieDetails -> {
                    navigateToMoviesDetailsScreen(effect.movieId)
                }

                is MoviesListEffect.NavigateToSeriesDetails -> {
                    navigateToSeriesDetailsScreen(effect.seriesId)
                }

                is MoviesListEffect.ShowError -> {}
            }
        }
    }
    MoviesListContent(
        state = state,
        moviesListInteractionListener = moviesListViewModel,
        onBackClick = onBackClick,
    )
}

@Composable
fun MoviesListContent(
    state: MoviesListUiState,
    moviesListInteractionListener: MoviesListInteractionListener,
    onBackClick: () -> Unit
) {
    Box {
        Column(
            modifier = Modifier
                .background(Theme.color.background.screen)
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            ListTitleSection(
                title = state.moviesListTitle,
                onBackClick = onBackClick
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TransitionLazyColumnToGrid(
                    poster = state.mediaList,
                    isListSelected = state.isListSelected,
                    onClickItem = moviesListInteractionListener::onMediaClicked,
                )
            }

        }
        ViewToggle(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .navigationBarsPadding()
                .padding(bottom = 16.dp, end = 16.dp),
            isListSelected = state.isListSelected,
            onGridSelected = moviesListInteractionListener::onViewChanged
        )
    }
}


@Preview(showSystemUi = false, showBackground = true)
@Composable
fun MoviesListPreview() {
    val interactionListener = object : MoviesListInteractionListener {
        override fun onViewChanged(isGrid: Boolean) {}
        override fun onMediaClicked(mediaId: Int, mediaType: MediaType) {}
    }
    MoviesListContent(
        state = MoviesListUiState(),
        moviesListInteractionListener = interactionListener,
        onBackClick = {},
    )
}