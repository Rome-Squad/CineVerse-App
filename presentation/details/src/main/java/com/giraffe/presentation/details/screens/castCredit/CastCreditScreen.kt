package com.giraffe.presentation.details.screens.castCredit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.HorizontalDivider
import com.giraffe.designsystem.composable.ViewToggle
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.details.R
import com.giraffe.presentation.details.base.ScreenStates
import com.giraffe.presentation.details.components.TransitionBetweenColumnAndVerticalGrid
import com.giraffe.presentation.details.utils.EventListener

@Composable
fun CastCreditScreen(
    navigateBack: () -> Unit,
    navigateToSeriesDetails: (Int) -> Unit,
    navigateToMovieDetails: (Int) -> Unit,
    viewModel: CastCreditViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    EventListener(events = viewModel.effect) {
        when (it) {
            is CastCreditEffect.NavigateToSeriesDetails -> navigateToSeriesDetails(it.seriesId)
            is CastCreditEffect.NavigateToMovieDetails -> navigateToMovieDetails(it.movieId)
            is CastCreditEffect.NavigateBack -> navigateBack()
            is CastCreditEffect.Error -> {}
        }
    }

    CastCreditContent(
        state = state,
        interaction = viewModel
    )
}


@Composable
private fun CastCreditContent(
    state: CastCreditScreenState,
    interaction: CastCreditInteractionListener
) {
    ScreenStates(
        isLoading = state.isLoading,
        isNoInternet = state.isNoInternet,
        onRetryClick = interaction::onRetryClick
    ) {
        Box {
            Column(
                modifier = Modifier
                    .background(Theme.color.background.screen)
                    .fillMaxSize()
                    .statusBarsPadding()
            ) {
                AppBar(
                    stringResource(R.string.best_of) + " " + state.actorName,
                    showBackButton = true,
                    onBackButtonClick = interaction::onBackClick
                )
                HorizontalDivider()
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 40.dp)
                ) {
                    TransitionBetweenColumnAndVerticalGrid(
                        posters = state.posters,
                        isListSelected = !state.isGridSelected,
                        onPosterClicked = interaction::onPosterClick
                    )
                }

            }

            ViewToggle(
                isListSelected = !state.isGridSelected,
                onGridSelected = interaction::changeView,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .navigationBarsPadding()
                    .padding(bottom = 16.dp, end = 16.dp)
            )
        }
    }
}