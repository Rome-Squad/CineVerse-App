package com.giraffe.profile.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.designsystem.composable.InfoCard
import com.giraffe.designsystem.theme.Theme
import com.giraffe.profile.components.HistoryListItem
import com.giraffe.profile.components.HistoryTitleSection

@Composable
fun HistoryScreen(
    onBackClicked: () -> Unit = {},
    onExitClicked: () -> Unit = {},
    viewModel: HistoryViewModel= hiltViewModel()
) {

val state by viewModel.state.collectAsState()
    HistoryContent(
        state = state,
        onClosedClicked = onExitClicked,
        onBackClicked = onBackClicked,
        historyInteractionListener = viewModel
    )
}

@Composable
fun HistoryContent(
    state: HistoryScreenUiStateUiState,
    onClosedClicked: () -> Unit = {},
    onBackClicked: () -> Unit,
    historyInteractionListener: HistoryInteractionListener

    ) {
    Box {
        Column(
            modifier = Modifier
                .background(Theme.color.background.screen)
                .fillMaxSize()
        ) {
            HistoryTitleSection(
                title = state.historyListTitle,
                onBackClicked = onBackClicked,
            )

            InfoCard(
                description = "Tip: Swipe left to remove movies from your history.",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .padding(start = 16.dp, end = 16.dp)
                 ,
                onClickExit = historyInteractionListener::onExitClicked,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                HistoryListItem(
                    poster = state.mediaList,
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp),
                    onSwipedToLeft = historyInteractionListener::onSwipedToLeft,
                )
            }


        }
    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
fun HistoryContentPreview() {
    val historyInteractionListener = object : HistoryInteractionListener {
        override fun onSwipedToLeft() {}

        override fun onExitClicked() {}

    }
    HistoryContent(
        state = HistoryScreenUiStateUiState(
            historyListTitle = "History",
            mediaList = listOf(
                PosterUiState(
                    id = 1,
                    name = "Movie 1",
                    imageUri = "https://example.com/movie1.jpg",
                    rating = 2f, mediaType = MediaType.MOVIE,
                    genres = "Actions",
                    date = "2023-10-01"
                ),
                PosterUiState(
                    id = 2,
                    name = "Movie 2",
                    imageUri = "https://example.com/movie2.jpg",
                    rating = 3f, mediaType = MediaType.MOVIE,
                    genres = "Actions",
                    date = "2023-10-01"
                ),
                PosterUiState(
                    id = 3,
                    name = "Movie 1",
                    imageUri = "https://example.com/movie1.jpg",
                    rating = 2f, mediaType = MediaType.MOVIE,
                    genres = "Actions",
                    date = "2023-10-01"
                ),
                PosterUiState(
                    id = 4,
                    name = "Movie 2",
                    imageUri = "https://example.com/movie2.jpg",
                    rating = 3f, mediaType = MediaType.MOVIE,
                    genres = "Actions",
                    date = "2023-10-01"
                )
            )
        ),
        onClosedClicked = {},
        onBackClicked = {},
        historyInteractionListener = historyInteractionListener
    )
}

