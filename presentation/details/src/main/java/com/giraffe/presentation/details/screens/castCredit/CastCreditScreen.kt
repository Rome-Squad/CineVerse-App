package com.giraffe.presentation.details.screens.castCredit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.designsystem.composable.ViewToggle
import com.giraffe.presentation.details.R
import com.giraffe.presentation.details.base.BaseScreen
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

            is CastCreditEffect.Error -> {}

            CastCreditEffect.NavigateBack -> navigateBack()
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
    interaction: CastCreditInteractionListener,
    modifier: Modifier = Modifier
) {
    BaseScreen(
        title = stringResource(R.string.best_of_, state.actorName),
        isLoading = state.isLoading,
        isNoInternet = state.isNoInternet,
        onRetryClick = interaction::onRetryClick,
        onBackClick = interaction::onBackClick
    ) {
        Box(modifier = modifier) {
            TransitionBetweenColumnAndVerticalGrid(
                poster = state.posters,
                isListSelected = !state.isGridSelected,
                onPosterClicked = interaction::onPosterClick
            )

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