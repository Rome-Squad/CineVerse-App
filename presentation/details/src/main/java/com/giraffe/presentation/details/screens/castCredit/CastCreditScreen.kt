package com.giraffe.presentation.details.screens.castCredit

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.theme.Theme
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