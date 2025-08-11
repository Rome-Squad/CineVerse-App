package com.giraffe.presentation.details.screens.seasons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.presentation.details.R
import com.giraffe.presentation.details.base.BaseScreen
import com.giraffe.presentation.details.components.SeasonCard
import com.giraffe.presentation.details.utils.EventListener
import com.giraffe.presentation.details.utils.showToast
import com.giraffe.presentation.details.utils.toStringResource

@Composable
fun SeasonsScreen(
    onBackClick: () -> Unit,
    viewModel: SeasonsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current

    EventListener(
        events = viewModel.effect
    ) { effect ->
        when (effect) {
            is SeasonsEffect.NavigateBack -> onBackClick()
            is SeasonsEffect.ShowError -> context.showToast(effect.error.toStringResource())
        }
    }

    SeasonsContent(
        state = state,
        modifier = Modifier.fillMaxSize(),
        interaction = viewModel
    )
}


@Composable
private fun SeasonsContent(
    state: SeasonsScreenState,
    modifier: Modifier,
    interaction: SeasonInteractionListener
) {
    BaseScreen(
        title = stringResource(R.string.seasons),
        isLoading = state.isLoading,
        isNoInternet = state.isNoInternet,
        onBackClick = interaction::onBackClick,
        onRetryClick = interaction::retry
    ) {
        LazyColumn(
            modifier = modifier,
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
                    year = if (state.seasons[index].releaseYear != null) state.seasons[index].releaseYear.split(
                        ","
                    ).first().toInt()
                    else null,
                )
            }
        }
    }
}