package com.giraffe.presentation.details.screens.recommended.series

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.details.utils.EventListener

@Composable
fun RecommendedSeriesScreen(
    navigateToSeriesDetails: (Int) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecommendedSeriesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val lazyPagingItems = state.recommendedSeriesFlow.collectAsLazyPagingItems()

    EventListener(
        events = viewModel.effect,
    ) { effect ->
        when (effect) {
            is RecommendedSeriesEffect.NavigateToSeriesDetails -> {
                navigateToSeriesDetails(effect.seriesId)
            }
        }
    }

    RecommendedSeriesContent(
        title = state.seriesTitle.orEmpty(),
        lazyPagingItems = lazyPagingItems,
        onBackButtonClick = onBackClick,
        interaction = viewModel,
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .systemBarsPadding()
    )
}