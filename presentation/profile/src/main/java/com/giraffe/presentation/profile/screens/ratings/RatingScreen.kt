package com.giraffe.presentation.profile.screens.ratings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.InfoCard
import com.giraffe.designsystem.composable.Tabs
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.profile.R
import com.giraffe.presentation.profile.components.RatedItem
import com.giraffe.presentation.profile.utils.EffectListener

@Composable
fun RatingScreen(
    modifier: Modifier = Modifier,
    navigateToMovieDetails: (Int) -> Unit = {},
    navigateToSeriesDetails: (Int) -> Unit = {},
    navigateBack: () -> Unit = {},
    viewModel: RatingViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    EffectListener(
        events = viewModel.effect
    ) { effect ->
        when (effect) {
            RatingEffect.NavigateBack -> {
                navigateBack()
            }

            is RatingEffect.NavigateToMovieDetails -> {
                navigateToMovieDetails(effect.movieId)
            }

            is RatingEffect.NavigateToSeriesDetails -> {
                navigateToSeriesDetails(effect.seriesId)
            }

            is RatingEffect.ShowError -> {

            }
        }
    }
    RatingContent(
        modifier = modifier,
        state = state,
        interaction = viewModel
    )
}

@Composable
private fun RatingContent(
    modifier: Modifier = Modifier,
    state: RatingScreenState,
    interaction: RatingInteractionListener
) {
    Column(
        modifier = modifier
            .background(Theme.color.background.screen)
            .fillMaxSize()
            .systemBarsPadding(),
    ) {
        AppBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            title = stringResource(R.string.my_ratings),
            showBackButton = true,
            onBackButtonClick = interaction::onBackClick
        )
        AnimatedVisibility(
            modifier = Modifier.padding(horizontal = 16.dp),
            visible = state.isTipVisible
        ) {
            InfoCard(
                description = stringResource(R.string.tap_an_item_to_see_details_or_update_your_rating),
                modifier = Modifier.fillMaxWidth(),
                onClosedClick = interaction::onCloseTipClick
            )
        }
        Tabs(
            listOf(
                stringResource(R.string.movies),
                stringResource(R.string.series)
            ),
            selectedTabIndex = state.selectedTabIndex,
            onTabSelected = interaction::onTabSelected,
        )
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(state.selectedPosters, key = { it.poster.id }) {
                RatedItem(
                    ratedPoster = it,
                    onItemClick = interaction::onPosterClick,
                    onDeleteClick = interaction::onDeleteRatedPosterClick
                )
            }
        }
    }
}