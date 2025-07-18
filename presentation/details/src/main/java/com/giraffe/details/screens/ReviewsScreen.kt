package com.giraffe.details.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.components.ReviewCard
import com.giraffe.details.models.ReviewUI
import com.giraffe.details.utils.getCurrentLocalDateTime


@Composable
fun ReviewsScreen(
    reviewsList : List<ReviewUI>
){
    ReviewsContent(
        reviewsList = reviewsList
    )
}

@Composable
private fun ReviewsContent(
    modifier : Modifier = Modifier,
    reviewsList : List<ReviewUI>
){

    LazyColumn (
        modifier = modifier
            .background(Theme.color.background.screen)
            .systemBarsPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        item {
            AppBar(
                showBackButton = true,
                title = "Reviews",
                modifier = Modifier.padding(16.dp)
            )
        }
        items(reviewsList.size){ index->
            ReviewCard(
                modifier = Modifier.padding(horizontal = 16.dp),
                rate = reviewsList[index].rating,
                reviewText = reviewsList[index].content,
                reviewDate = reviewsList[index].createdAt,
                reviewerImageSource = reviewsList[index].authorImageUrl,
                reviewerName = reviewsList[index].authorName,
                reviewerUsername = reviewsList[index].authorUserName
            )
        }
    }
}

@Preview
@Composable
private fun MovieReviewsPreview(){

    val reviews = listOf(
        ReviewUI(
            id = "2",
            content = "Good acting and soundtrack, but the plot was a bit predictable.",
            createdAt = getCurrentLocalDateTime(),
                authorName = "Mark Brown",
                authorUserName = "mbrown",
                authorImageUrl = "https://example.com/avatars/linaahmed.png",
                rating = 3,
        ),
        ReviewUI(
            id = "2",
            content = "Good acting and soundtrack, but the plot was a bit predictable.",
            createdAt = getCurrentLocalDateTime(),
            authorName = "Mark Brown",
            authorUserName = "mbrown",
            authorImageUrl = "https://example.com/avatars/linaahmed.png",
            rating = 3,
        ),
        ReviewUI(
            id = "2",
            content = "Good acting and soundtrack, but the plot was a bit predictable.",
            createdAt = getCurrentLocalDateTime(),
            authorName = "Mark Brown",
            authorUserName = "mbrown",
            authorImageUrl = "https://example.com/avatars/linaahmed.png",
            rating = 3,
        ),
        ReviewUI(
            id = "2",
            content = "Good acting and soundtrack, but the plot was a bit predictable.",
            createdAt = getCurrentLocalDateTime(),
            authorName = "Mark Brown",
            authorUserName = "mbrown",
            authorImageUrl = "https://example.com/avatars/linaahmed.png",
            rating = 3,
        ),
        ReviewUI(
            id = "2",
            content = "Good acting and soundtrack, but the plot was a bit predictable.",
            createdAt = getCurrentLocalDateTime(),
            authorName = "Mark Brown",
            authorUserName = "mbrown",
            authorImageUrl = "https://example.com/avatars/linaahmed.png",
            rating = 3,
        ),
    )
    ReviewsContent(
        reviewsList = reviews
    )
}