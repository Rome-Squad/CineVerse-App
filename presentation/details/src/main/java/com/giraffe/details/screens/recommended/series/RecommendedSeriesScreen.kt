package com.giraffe.details.screens.recommended.series

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.utils.EventListener

@Composable
fun RecommendedSeriesScreen(
    navigateToSeriesDetails: (Int) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecommendedSeriesViewModel = hiltViewModel()
) {
    EventListener(
        events = viewModel.effect,
    ) { effect ->
        when (effect) {
            is RecommendedSeriesEffect.NavigateToSeriesDetails -> {
                navigateToSeriesDetails(effect.seriesId)
            }
        }
    }

    val lazyPagingItems = viewModel.recommendationScreenState.collectAsLazyPagingItems()
    RecommendedSeriesContent(
        title = viewModel.titleSeries,
        lazyPagingItems = lazyPagingItems,
        onBackButtonClick = onBackClick,
        interaction = viewModel,
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .systemBarsPadding()
    )
}