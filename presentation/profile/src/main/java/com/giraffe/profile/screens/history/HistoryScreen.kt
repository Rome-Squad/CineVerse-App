package com.giraffe.profile.screens.history

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.InfoCard
import com.giraffe.designsystem.composable.MessageInfoBox
import com.giraffe.designsystem.composable.PosterItemHorizontal
import com.giraffe.designsystem.theme.Theme
import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.profile.R
import com.giraffe.profile.components.SwipableItem
import com.giraffe.profile.components.DeleteButton

@Composable
fun HistoryScreen(
    onBackClicked: () -> Unit = {},
    navigateToMoviesDetailsScreen: (Int) -> Unit,
    navigateToSeriesDetailsScreen: (Int) -> Unit,
    navigateToExploreScreen: () -> Unit,

    viewModel: HistoryViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HistoryEffect.NavigateToMovieDetails -> {
                    navigateToMoviesDetailsScreen(effect.movieId)
                }

                is HistoryEffect.NavigateToSeriesDetails -> {
                    navigateToSeriesDetailsScreen(effect.seriesId)
                }


                is HistoryEffect.navigateToExploreScreen -> {
                    navigateToExploreScreen()
                }
                is HistoryEffect.navigateToProfileScreen -> {
                    onBackClicked()
                }

                is HistoryEffect.ShowError -> {}

            }
        }
    }
    HistoryContent(
        state = state,
        onBackClicked = onBackClicked,
        historyInteractionListener = viewModel,

        )
}

@Composable
fun HistoryContent(
    state: HistoryUiState,
    onBackClicked: () -> Unit,
    historyInteractionListener: HistoryInteractionListener
) {
    LazyColumn(
        modifier = Modifier
            .background(Theme.color.background.screen)
            .fillMaxSize()
            .systemBarsPadding()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        stickyHeader {
            AppBar(
                title = stringResource(R.string.history),
                showBackButton = true,
                onBackButtonClick = onBackClicked
            )
        }

        item {
            AnimatedVisibility(
                visible = state.isVisible
            ) {
                InfoCard(
                    description = stringResource(
                        id = R.string.screen_info
                    ),
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClosedClick = historyInteractionListener::onCloseClicked
                )

            }
        }


        if (state.mediaList.isEmpty()) {
            item {
                MessageInfoBox(
                    title = stringResource(R.string.no_history_yet),
                    caption = stringResource(R.string.it_s_quiet_in_here_start_watching_and_we_ll_keep_track_for_you),
                    icon = painterResource(id = Theme.icons.dueTone.history),
                    buttonBackgroundColor = Theme.color.brand.primary,
                    iconBackgroundColor = Theme.color.button.disabled,
                    iconTintColor = Theme.color.brand.primary,
                    titlePrimaryButton = stringResource(R.string.find_something_to_watch),
                    isButtonsVisible = true,
                    isSecondaryButtonVisible = false,
                    onClickPrimaryButton ={historyInteractionListener.navigateToExploreScreen() },
                )
            }
        }


        items(state.mediaList, key = { poster -> poster.id }) { poster ->
            SwipableItem(
                actionButton = {
                    DeleteButton(
                        onDeleteClick = { historyInteractionListener.onDeleteClicked(poster.id) }
                    )
                },
            ) {
                PosterItemHorizontal(
                    modifier = Modifier.fillMaxWidth(),
                    movie = poster,
                    onClickPoster = { historyInteractionListener.onMediaClicked(poster.id) }
                )
            }
        }
    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
fun HistoryContentPreview() {
    val historyInteractionListener = object : HistoryInteractionListener {
        override fun onDeleteClicked(id:Int): Unit = Unit
        override fun onCloseClicked() {}
        override fun onMediaClicked(mediaId: Int) {}
        override fun navigateToExploreScreen() {}
    }
    HistoryContent(
        state = HistoryUiState(
            historyListTitle = "History",
            mediaList = listOf(
                Poster(
                    id = 1,
                    name = "Movie 1",
                    imageUri = "https://example.com/movie1.jpg",
                    rating = 2f, mediaTypeOfPoster = "Movie",
                    genres = "Actions",
                    date = "2023-10-01"
                ),
                Poster(
                    id = 2,
                    name = "Movie 2",
                    imageUri = "https://example.com/movie2.jpg",
                    rating = 3f, mediaTypeOfPoster = "Movie",
                    genres = "Actions",
                    date = "2023-10-01"
                ),
                Poster(
                    id = 3,
                    name = "Movie 1",
                    imageUri = "https://example.com/movie1.jpg",
                    rating = 2f, mediaTypeOfPoster = "Movie",
                    genres = "Actions",
                    date = "2023-10-01"
                ),
                Poster(
                    id = 4,
                    name = "Movie 2",
                    imageUri = "https://example.com/movie2.jpg",
                    rating = 3f, mediaTypeOfPoster = "Movie",
                    genres = "Actions",
                    date = "2023-10-01"
                )
            )
        ),
        onBackClicked = {},
        historyInteractionListener = historyInteractionListener,
    )
}

