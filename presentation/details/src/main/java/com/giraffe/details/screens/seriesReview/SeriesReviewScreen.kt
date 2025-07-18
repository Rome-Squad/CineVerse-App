package com.giraffe.details.screens.seriesReview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.components.ReviewCard
import com.giraffe.details.models.ReviewUI
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun SeriesReviewScreen(
    seriesId: Int,
    navController: NavController,
    modifier: Modifier = Modifier,
    seriesReviewViewModel: SeriesReviewViewModel = koinViewModel(parameters = {
        parametersOf(seriesId)
    })
) {
    val lazyPagingItems = seriesReviewViewModel.reviewsFlow.collectAsLazyPagingItems()

    ReviewsContent(
        modifier = modifier,
        lazyPagingItems = lazyPagingItems,
        onBackArrowClick = { navController.navigateUp() }
    )
}

@Composable
private fun ReviewsContent(
    lazyPagingItems: LazyPagingItems<ReviewUI>,
    onBackArrowClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier
            .background(Theme.color.background.screen)
            .systemBarsPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            AppBar(
                showBackButton = true,
                title = "Reviews",
                modifier = Modifier.padding(16.dp),
                onBackButtonClick = onBackArrowClick
            )
        }
        items(lazyPagingItems.itemCount) { index ->
            val item = lazyPagingItems[index]!!

            ReviewCard(
                modifier = Modifier.padding(horizontal = 16.dp),
                rate = item.rating,
                reviewText = item.content,
                reviewDate = item.createdAt,
                reviewerImageUrl = item.authorImageUrl,
                reviewerName = item.authorName,
                reviewerUsername = item.authorUserName
            )
        }
    }
}