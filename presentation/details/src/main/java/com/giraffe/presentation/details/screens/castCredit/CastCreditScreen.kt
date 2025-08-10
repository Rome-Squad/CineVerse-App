package com.giraffe.presentation.details.screens.castCredit

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.composable.ViewToggle
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.details.R
import com.giraffe.presentation.details.components.TransitionBetweenColumnAndVerticalGrid
import com.giraffe.presentation.details.utils.EventListener

@Composable
fun CastCreditScreen(
    onBackClick: () -> Unit,
    navigateToSeriesDetails: (Int) -> Unit,
    navigateToMovieDetails: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CastCreditViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    EventListener(
        events = viewModel.effect,
    ) {
        when (it) {
            is CastCreditEffect.NavigateToSeriesDetails -> navigateToSeriesDetails(
                it.seriesId,
            )

            is CastCreditEffect.NavigateToMovieDetails -> navigateToMovieDetails(
                it.movieId,
            )
        }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .systemBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(state.isLoading) {
            Progress(modifier = Modifier.size(40.dp))
        }
        AnimatedVisibility(!state.isLoading) {
            CastCreditContent(
                actorName = state.actorName,
                state = state,
                interaction = viewModel,
                onBackClick = onBackClick,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopCenter)
            )
        }
    }
}


@Composable
private fun CastCreditContent(
    actorName: String,
    state: CastCreditScreenState,
    interaction: CastCreditInteractionListener,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Column {
            AppBar(
                title = stringResource(R.string.best_of_, actorName),
                showBackButton = true,
                onBackButtonClick = onBackClick,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            TransitionBetweenColumnAndVerticalGrid(
                poster = state.posters,
                isListSelected = !state.isGridSelected,
                onPosterClicked = interaction::onPosterClick
            )
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