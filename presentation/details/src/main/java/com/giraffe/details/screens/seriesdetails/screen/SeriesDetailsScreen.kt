package com.giraffe.details.screens.seriesdetails.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import com.giraffe.designsystem.composable.NoInternetScreen
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.theme.Theme
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
    onClickPlay: (String) -> Unit,
    navigateToLogIn: () -> Unit,
    navigateToReviews: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SeriesDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    EventListener(events = viewModel.effect) {
        when (it) {
            is SeriesDetailsEffect.Error -> {}
            SeriesDetailsEffect.NavigateToLogin -> navigateToLogIn()
            is SeriesDetailsEffect.NavigateToCastDetails -> navigateToCastDetails(it.personId)

            is SeriesDetailsEffect.NavigateToSeasons -> navigateToSeason(it.seriesId)

            is SeriesDetailsEffect.NavigateToSeriesDetails -> navigateToSeriesDetails(it.seriesId)

            is SeriesDetailsEffect.NavigateToRecommendedSeries -> navigateToRecommendedSeries(
                it.seriesId,
                it.title
            )

            is SeriesDetailsEffect.NavigateToReviews -> navigateToReviews(it.seriesId)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .systemBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(state.isNetworkError) {
                NoInternetScreen(
                    onRetryClick = {
                        viewModel.loadSeriesDetailsScreen()
                    }
                )
            }
            AnimatedVisibility(state.isLoading) {
                Progress(modifier = Modifier.size(40.dp))
            }
        }
        AnimatedVisibility(
            visible = !state.isLoading && !state.isNetworkError,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            SeriesDetailsContent(
                state = state,
                interaction = viewModel,
                onBackButtonClick = onBackButtonClick,
                onClickPlay = onClickPlay,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}