package com.giraffe.details.screens.recommended

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.components.recomended.RecommendedContent
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun RecommendedSeriesScreen(
    seriesId: Long,
    title: String = " ",
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: RecommendedSeriesViewModel = koinViewModel(
        parameters = { parametersOf(title, seriesId) }
    )
) {
    val viewModel: RecommendedSeriesViewModel = koinViewModel(
        parameters = { parametersOf(title, seriesId) }
    )
    val lazyPagingItems = viewModel.recommendationScreenState.collectAsLazyPagingItems()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .systemBarsPadding()
    ) {
        Column {

            RecommendedContent(
                title = viewModel.title,
                lazyPagingItems = lazyPagingItems,
                navController = navController,
            )
        }

        AnimatedVisibility(
            visible = lazyPagingItems.loadState.refresh is androidx.paging.LoadState.Loading,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Progress(modifier = Modifier.size(48.dp))
        }
    }
}
