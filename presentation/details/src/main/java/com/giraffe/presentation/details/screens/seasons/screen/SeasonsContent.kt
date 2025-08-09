package com.giraffe.presentation.details.screens.seasons.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.presentation.details.R
import com.giraffe.presentation.details.components.SeasonCard
import com.giraffe.presentation.details.screens.seasons.SeasonsScreenState

@Composable
fun SeasonsContent(
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
                    year = if (state.seasons[index].releaseYear != null) state.seasons[index].releaseYear?.split(
                        "-"
                    )
                        ?.first()?.toInt()
                    else null,
                )
            }
        }
    }
}