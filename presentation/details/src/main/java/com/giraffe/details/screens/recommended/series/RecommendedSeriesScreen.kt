package com.giraffe.details.screens.recommended.series

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.navigation.SeriesDetailsRoute
import com.giraffe.details.utils.EventListener
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun RecommendedSeriesScreen(
    title: String,
    seriesId: Long,
    navController: NavController,
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecommendedSeriesViewModel = koinViewModel(parameters = {
        parametersOf(
            title,
            seriesId
        )
    })
) {
    EventListener(
        events = viewModel.effect,
    ) { effect ->
        when (effect) {
            is RecommendedSeriesEffect.NavigateToSeriesDetails -> {
                navController.navigate(
                    SeriesDetailsRoute(
                        effect.seriesId,
                    )
                )
            }
        }
    }

    val lazyPagingItems = viewModel.recommendationScreenState.collectAsLazyPagingItems()
    RecommendedSeriesContent(
        title = title,
        lazyPagingItems = lazyPagingItems,
        onBackButtonClick = onBackButtonClick,
        interaction = viewModel,
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .systemBarsPadding()
    )
}