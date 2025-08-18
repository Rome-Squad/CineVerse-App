package com.giraffe.presentation.profile.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.designsystem.composable.InfoCard
import com.giraffe.designsystem.composable.MessageInfoBox
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.profile.R
import com.giraffe.presentation.profile.components.BaseScreen
import com.giraffe.presentation.profile.components.DeleteButton
import com.giraffe.presentation.profile.components.PosterItemHorizontal
import com.giraffe.presentation.profile.components.SwipableItem
import com.giraffe.presentation.profile.utils.EffectListener

@Composable
fun HistoryScreen(
    onBackClicked: () -> Unit = {},
    navigateToMoviesDetailsScreen: (Int) -> Unit,
    navigateToSeriesDetailsScreen: (Int) -> Unit,
    navigateToExploreScreen: () -> Unit,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    EffectListener(
        events = viewModel.effect
    ) { effect ->
        when (effect) {
            is HistoryEffect.NavigateToMovieDetails -> navigateToMoviesDetailsScreen(effect.movieId)
            is HistoryEffect.NavigateToSeriesDetails -> navigateToSeriesDetailsScreen(effect.seriesId)
            HistoryEffect.NavigateToExploreScreen -> navigateToExploreScreen()
            is HistoryEffect.NavigateToProfileScreen -> onBackClicked()
            HistoryEffect.NavigateBack -> onBackClicked()
            is HistoryEffect.ShowError -> {}
        }
    }

    HistoryContent(
        state = state,
        interaction = viewModel
    )
}

@Composable
private fun HistoryContent(
    state: HistoryScreenState,
    interaction: HistoryInteractionListener
) {
    BaseScreen(
        title = stringResource(R.string.history),
        isLoading = state.isLoading,
        onBackClick = interaction::onBackClick,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (state.mediaList.isEmpty()) {
                MessageInfoBox(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(60.dp),
                    title = stringResource(R.string.no_history_yet),
                    caption = stringResource(R.string.it_s_quiet_in_here_start_watching_and_we_ll_keep_track_for_you),
                    icon = painterResource(id = Theme.icons.dueTone.history),
                    buttonBackgroundColor = Theme.color.brand.primary,
                    iconBackgroundColor = Theme.color.button.disabled,
                    iconTintColor = Theme.color.brand.primary,
                    titlePrimaryButton = stringResource(R.string.find_something_to_watch),
                    isButtonsVisible = true,
                    isSecondaryButtonVisible = false,
                    onClickPrimaryButton = { interaction.navigateToExploreScreen() },
                )
            } else {
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
                    if (state.isTipVisible) {
                        item {

                            InfoCard(
                                description = stringResource(
                                    id = R.string.screen_info
                                ),
                                modifier = Modifier
                                    .fillMaxWidth(),
                                onClosedClick = interaction::onCloseClicked
                            )

                        }
                    }
                    items(state.mediaList, key = { poster -> poster.id }) { poster ->
                        SwipableItem(
                            actionButton = {
                                DeleteButton(
                                    onDeleteClick = {
                                        interaction.onDeleteClicked(
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
                                        interaction.onMediaClicked(
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
}