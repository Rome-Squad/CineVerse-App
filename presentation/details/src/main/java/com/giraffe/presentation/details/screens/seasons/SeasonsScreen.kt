package com.giraffe.presentation.details.screens.seasons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.details.R
import com.giraffe.presentation.details.components.SeasonCard

@Composable
fun SeasonsScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SeasonsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .systemBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(state.isLoading) {
            Progress(
                modifier = Modifier
                    .size(40.dp)
            )
        }
        AnimatedVisibility(!state.isLoading) {
            SeasonsContent(
                state = state,
                onBackClick = onBackClick,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


@Composable
private fun SeasonsContent(
    state: SeasonsScreenState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        AppBar(
            title = stringResource(R.string.seasons),
            showBackButton = true,
            onBackButtonClick = onBackClick,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                count = state.seasons.size
            ) { index ->
                SeasonCard(
                    posterUrl = state.seasons[index].posterUrl,
                    title = stringResource(
                        R.string.season,
                        state.seasons[index].seasonNumber + 1
                    ),
                    overview = state.seasons[index].overview,
                    rating = state.seasons[index].rating,
                    episodes = state.seasons[index].episodeCount,
                    year = state.seasons[index].releaseYear.split("-").first().toIntOrNull(),
                )
            }
        }
    }
}