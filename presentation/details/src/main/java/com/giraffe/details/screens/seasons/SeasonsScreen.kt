package com.giraffe.details.screens.seasons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.R
import com.giraffe.details.components.SeasonCard
import com.giraffe.details.utils.imageSourceToPainter
import org.koin.androidx.compose.koinViewModel

@Composable
fun SeasonsScreen(
    modifier: Modifier = Modifier,
    viewModel: SeasonsViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.loadSeason(2288)
    }
    SeasonsContent(
        state = state,
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .systemBarsPadding()
    )
}

@Composable
fun SeasonsContent(
    state: SeasonsScreenState,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        AppBar(
            title = stringResource(R.string.seasons),
            showBackButton = true,
            onBackButtonClick = {},
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                count = state.seasons.size
            ) { index ->
                SeasonCard(
                    poster = (state.seasons[index].posterUrl
                        ?: com.giraffe.designsystem.R.drawable.main_poster_test).imageSourceToPainter(),
                    title = "Season ${state.seasons[index].seasonNumber + 1}",
                    overview = state.seasons[index].overview,
                    rating = state.seasons[index].rating,
                    episodes = state.seasons[index].episodeCount,
                    year = state.seasons[index].releaseYear.split("-").first().toInt(),
                    onClick = {}
                )
            }
        }
    }
}