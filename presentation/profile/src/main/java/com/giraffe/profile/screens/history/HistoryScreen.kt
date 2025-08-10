package com.giraffe.profile.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.InfoCard
import com.giraffe.designsystem.composable.MessageInfoBox
import com.giraffe.designsystem.composable.NoInternetScreen
import com.giraffe.designsystem.composable.PosterItemHorizontal
import com.giraffe.designsystem.theme.Theme
import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.profile.R
import com.giraffe.profile.components.DeleteButton
import com.giraffe.profile.components.SwipableItem

@Composable
fun HistoryScreen(
    onBackClicked: () -> Unit = {},
    navigateToMoviesDetailsScreen: (Int) -> Unit,
    navigateToSeriesDetailsScreen: (Int) -> Unit,
    navigateToExploreScreen: () -> Unit,

    viewModel: HistoryViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()
    val isNoInternet by viewModel.isNoInternet.collectAsState()

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .systemBarsPadding()
    ) {
        AppBar(
            title = stringResource(R.string.history),
            showBackButton = true,
            onBackButtonClick = onBackClicked
        )

        if (isNoInternet) {
            NoInternetScreen()
        }

        if (!isNoInternet) {
            HistoryContent(
                state = state,
                historyInteractionListener = viewModel,

                )
        }
    }

}


@Composable
fun HistoryContent(
    state: HistoryUiState,
    historyInteractionListener: HistoryInteractionListener
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        if (state.mediaList.isEmpty() && state.isLoading.not()) {
                MessageInfoBox(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(60.dp)
                    ,
                    title = stringResource(R.string.no_history_yet),
                    caption = stringResource(R.string.it_s_quiet_in_here_start_watching_and_we_ll_keep_track_for_you),
                    icon = painterResource(id = Theme.icons.dueTone.history),
                    buttonBackgroundColor = Theme.color.brand.primary,
                    iconBackgroundColor = Theme.color.button.disabled,
                    iconTintColor = Theme.color.brand.primary,
                    titlePrimaryButton = stringResource(R.string.find_something_to_watch),
                    isButtonsVisible = true,
                    isSecondaryButtonVisible = false,
                    onClickPrimaryButton = { historyInteractionListener.navigateToExploreScreen() },
                )

        }
        else{

            LazyColumn(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .background(Theme.color.background.screen)
                    .fillMaxSize()
                    .systemBarsPadding()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {


                if (state.isVisible) {
                    item {

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

                items(state.mediaList, key = { poster -> poster.id }) { poster ->
                    SwipableItem(
                        actionButton = {
                            DeleteButton(
                                onDeleteClick = {
                                    historyInteractionListener.onDeleteClicked(
                                        poster.id,
                                        poster.mediaTypeOfPoster ?: ""
                                    )
                                }
                            )
                        },
                    ) {
                        PosterItemHorizontal(
                            modifier = Modifier.fillMaxWidth(),
                            movie = poster,
                            onClickPoster = {
                                poster.mediaTypeOfPoster?.let {
                                    historyInteractionListener.onMediaClicked(
                                        poster.id,
                                        it
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }


    }

}

@Preview(showSystemUi = false, showBackground = true)
@Composable
fun HistoryContentPreview() {
    val historyInteractionListener = object : HistoryInteractionListener {
        override fun onDeleteClicked(id: Int, mediaType: String): Unit = Unit
        override fun onCloseClicked() {}
        override fun onMediaClicked(mediaId: Int, mediaType: String) {}
        override fun navigateToExploreScreen() {}
        override fun retry() {}
    }
    HistoryContent(
        state = HistoryUiState(
            historyListTitle = "History",
            mediaList = listOf(
                Poster(
                    id = 1,
                    name = "Movie 1",
                    imageUrl = "https://example.com/movie1.jpg",
                    rating = 2f, mediaTypeOfPoster = "Movie",
                    genres = "Actions",
                    date = "2023-10-01"
                ),
                Poster(
                    id = 2,
                    name = "Movie 2",
                    imageUrl = "https://example.com/movie2.jpg",
                    rating = 3f, mediaTypeOfPoster = "Movie",
                    genres = "Actions",
                    date = "2023-10-01"
                ),
                Poster(
                    id = 3,
                    name = "Movie 1",
                    imageUrl = "https://example.com/movie1.jpg",
                    rating = 2f, mediaTypeOfPoster = "Movie",
                    genres = "Actions",
                    date = "2023-10-01"
                ),
                Poster(
                    id = 4,
                    name = "Movie 2",
                    imageUrl = "https://example.com/movie2.jpg",
                    rating = 3f, mediaTypeOfPoster = "Movie",
                    genres = "Actions",
                    date = "2023-10-01"
                )
            )
        ),
        historyInteractionListener = historyInteractionListener,
    )
}

