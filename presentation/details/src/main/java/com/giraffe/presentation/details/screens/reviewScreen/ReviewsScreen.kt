package com.giraffe.presentation.details.screens.reviewScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.giraffe.presentation.details.R
import com.giraffe.presentation.details.base.BaseScreen
import com.giraffe.presentation.details.components.ReviewCard
import com.giraffe.presentation.details.utils.EventListener
import com.giraffe.presentation.details.utils.showToast
import com.giraffe.presentation.details.utils.toStringResource


@Composable
fun ReviewsScreen(
    navigateBack: () -> Unit,
    reviewsViewModel: ReviewsViewModel = hiltViewModel()
) {
    val state by reviewsViewModel.state.collectAsState()
    val context = LocalContext.current
    EventListener(
        events = reviewsViewModel.effect
    ) {
        when (it) {
            is ReviewEffect.NavigateBack -> navigateBack()

            is ReviewEffect.ShowError -> context.showToast(
                it.error.toStringResource()
            )
        }
    }
    ReviewsContent(
        state = state,
        interaction = reviewsViewModel,
    )
}

@Composable
private fun ReviewsContent(
    state: ReviewsScreenState,
    interaction: ReviewsInteractionListener,
) {
    val reviewsList = state.reviewsFlow.collectAsLazyPagingItems()
    BaseScreen(
        title = stringResource(R.string.reviews),
        isLoading = state.isLoading,
        isNoInternet = state.isNoInternet,
        onBackClick = interaction::onBackClick,
        onRetryClick = interaction::onRetryClick
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
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
}