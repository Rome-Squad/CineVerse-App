package com.giraffe.details.screens.reviewScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.components.ReviewCard
import com.giraffe.details.models.ReviewUI


@Composable
fun ReviewsScreen(
    movieId: Int?,
    seriesId: Int?,
    navController: NavController,
    reviewsViewModel: ReviewsViewModel = hiltViewModel()
) {
    LaunchedEffect(movieId, seriesId) {
        reviewsViewModel.fetchReviews(movieId, seriesId)
    }

    val reviews = reviewsViewModel.reviewsState.value

    ReviewsContent(
        reviewsList = reviews ?: emptyList(),
        onBackArrowClick = { navController.navigateUp() }
    )
}

@Composable
private fun ReviewsContent(
    reviewsList: List<ReviewUI>,
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
                modifier = Modifier.padding(8.dp),
                onBackButtonClick = onBackArrowClick
            )
        }
        items(reviewsList.size) { index ->
            ReviewCard(
                modifier = Modifier.padding(horizontal = 16.dp),
                rate = reviewsList[index].rating,
                reviewText = reviewsList[index].content,
                reviewDate = reviewsList[index].createdAt,
                reviewerImageUrl = reviewsList[index].authorImageUrl,
                reviewerName = reviewsList[index].authorName,
                reviewerUsername = reviewsList[index].authorUserName
            )
        }
    }
}