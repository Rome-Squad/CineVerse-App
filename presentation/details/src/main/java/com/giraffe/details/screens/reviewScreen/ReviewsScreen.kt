package com.giraffe.details.screens.reviewScreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.R
import com.giraffe.details.components.LoadingView
import com.giraffe.details.components.ReviewCard
import com.giraffe.details.models.ReviewUI


@Composable
fun ReviewsScreen(
    navController: NavController,
    reviewsViewModel: ReviewsViewModel = hiltViewModel()
) {
    val state = reviewsViewModel.state.collectAsState().value
    val reviews = state.reviewsFlow.collectAsLazyPagingItems()

    AnimatedContent(
        state.isLoading
    ) {
        when (it) {
            true -> LoadingView()
            false -> ReviewsContent(
                reviewsList = reviews,
                onBackArrowClick = { navController.navigateUp() }
            )
        }
    }
}

@Composable
private fun ReviewsContent(
    reviewsList: LazyPagingItems<ReviewUI>,
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
        stickyHeader {
            AppBar(
                showBackButton = true,
                title = stringResource(R.string.reviews),
                modifier = Modifier.padding(horizontal = 8.dp),
                onBackButtonClick = onBackArrowClick
            )
        }
        items(reviewsList.itemCount) { index ->
            reviewsList[index]?.let { review ->
                ReviewCard(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    rate = review.rating,
                    reviewText = review.content,
                    reviewDate = review.createdAt,
                    reviewerImageUrl = review.authorImageUrl,
                    reviewerName = review.authorName,
                    reviewerUsername = review.authorUserName
                )
            }
        }
    }
}