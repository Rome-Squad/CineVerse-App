package com.giraffe.profile.screens.ratings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.InfoCard
import com.giraffe.designsystem.composable.Tabs
import com.giraffe.profile.R
import com.giraffe.profile.history.composable.RatedMovie

@Composable
fun RatingScreen(
    modifier: Modifier = Modifier,
    viewModel: RatingViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is RatingEffect.NavigateToDetails -> {}
                RatingEffect.NavigateBack -> {}
            }
        }
    }
    RatingContent(
        modifier = modifier,
        state = state,
        interaction = viewModel)
}

@Composable
private fun RatingContent(
    modifier: Modifier = Modifier,
    state: RatingScreenState,
    interaction: RatingInteractionListener
) {
    LazyColumn(
        modifier = modifier
    ) {
        stickyHeader {
            AppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                title = stringResource(R.string.my_ratings),
                showBackButton = true,
                onBackButtonClick = interaction::onBackClick
            )
        }
        item {
            AnimatedVisibility(
                visible = state.isTipVisible
            ) {
                InfoCard(
                    description = stringResource(R.string.tap_an_item_to_see_details_or_update_your_rating),
                    modifier = Modifier.fillMaxWidth(),
                    onClosedClick = interaction::onCloseTipClick
                )
            }
        }
        item {
            Tabs(
                listOf(
                    stringResource(R.string.movies),
                    stringResource(R.string.series)
                ),
                selectedTabIndex = state.selectedTabIndex,
                onTabSelected = interaction::onTabSelected,
            )
        }
        items(state.selectedPosters) {
            RatedMovie(poster = it)
        }
    }
}

@Preview
@Composable
private fun Preview() {
    RatingContent(
        state = RatingScreenState(),
        interaction = object : RatingInteractionListener {
            override fun navigateToDetails(id: Int) {}
            override fun onCloseTipClick() {}
            override fun onBackClick() {}
            override fun onTabSelected(tabIndex: Int) {}
        }
    )
}