package com.giraffe.details.screens.seriesReview

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.giraffe.designsystem.composable.Progress
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun SeriesReviewScreen(
    seriesId: Int,
    modifier: Modifier = Modifier,
    seriesReviewViewModel: SeriesReviewViewModel = koinViewModel(parameters = {
        parametersOf(
            seriesId
        )
    })
) {
    val lazyPagingItems = seriesReviewViewModel.reviewsFlow.collectAsLazyPagingItems()

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