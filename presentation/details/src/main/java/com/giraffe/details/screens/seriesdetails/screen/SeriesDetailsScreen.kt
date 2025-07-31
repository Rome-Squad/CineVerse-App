package com.giraffe.details.screens.seriesdetails.screen

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
import com.giraffe.details.models.ReviewUI
import com.giraffe.details.screens.seriesdetails.SeriesDetailsEffect
import com.giraffe.details.screens.seriesdetails.SeriesDetailsViewModel
import com.giraffe.details.utils.EventListener

@Composable
fun SeriesDetailsScreen(
    navigateToRecommendedSeries: (seriesID: Int, titleSeries: String) -> Unit,
    navigateToCastDetails: (castID: Int) -> Unit,
    navigateToSeason: (seriesId: Int) -> Unit,
    navigateToSeriesDetails: (seriesId: Int) -> Unit,
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    navigateToReviews: (reviews: List<ReviewUI>) -> Unit = {},
    viewModel: SeriesDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    EventListener(events = viewModel.effect) {
        when (it) {
            is SeriesDetailsEffect.Error -> {}
            is SeriesDetailsEffect.NavigateToCastDetails -> navigateToCastDetails(it.personId)

            is SeriesDetailsEffect.NavigateToSeasons -> navigateToSeason(it.seriesId)

            is SeriesDetailsEffect.NavigateToSeriesDetails -> navigateToSeriesDetails(it.seriesId)

            is SeriesDetailsEffect.NavigateToRecommendedSeries -> navigateToRecommendedSeries(
                it.seriesId,
                it.title
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
            SeriesDetailsContent(
                state = state,
                interaction = viewModel,
                onBackButtonClick = onBackButtonClick,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}