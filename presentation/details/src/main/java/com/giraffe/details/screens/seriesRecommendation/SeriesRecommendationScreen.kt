package com.giraffe.details.screens.seriesRecommendation

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.giraffe.designsystem.composable.Progress
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun SeriesRecommendationScreen(
    seriesId: Long,
    modifier: Modifier = Modifier,
    recommendationViewModel: SeriesRecommendationViewModel = koinViewModel(parameters = {
        parametersOf(
            seriesId
        )
    })
) {
    val lazyPagingItems =
        recommendationViewModel.recommendationScreenState.collectAsLazyPagingItems()

    LazyColumn(modifier.systemBarsPadding()) {
        if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
            item {
                Progress(Modifier.size(50.dp))
            }
        }

        items(lazyPagingItems.itemCount) { index ->
            val item = lazyPagingItems[index]

        }
    }
}