package com.giraffe.details.screens.seriesRecommendation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
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
                Text(
                    text = "Waiting for items to load from the backend",
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally),
                )
            }
        }

        items(lazyPagingItems.itemCount) { index ->
            val item = lazyPagingItems[index]

        }
    }
}