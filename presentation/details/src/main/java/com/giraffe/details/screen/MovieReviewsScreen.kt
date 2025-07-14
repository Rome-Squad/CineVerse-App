package com.giraffe.details.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.components.ReviewCard
import com.giraffe.details.utils.getCurrentLocalDateTime
import com.giraffe.movies.entity.MovieReview
import com.giraffe.movies.entity.MovieReviewAuthor

@Composable
fun MovieReviewsScreen(
    movieReviewList : List<MovieReview>
){

    MovieReviewsContent(
        movieReviewList = movieReviewList
    )
}

@Composable
private fun MovieReviewsContent(
    modifier : Modifier = Modifier,
    movieReviewList : List<MovieReview>
){

    LazyColumn (
        modifier = modifier
            .background(Theme.color.background.screen)
            .padding(vertical = 20.dp)
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
        items(movieReviewList.size){ index->
            ReviewCard(
                modifier = Modifier.padding(horizontal = 16.dp),
                rate = movieReviewList[index].author.rating,
                reviewText = movieReviewList[index].content,
                reviewDate = movieReviewList[index].createdAt,
                reviewerImageSource = movieReviewList[index].author.avatarImage,
                reviewerName = movieReviewList[index].author.name,
                reviewerUsername = movieReviewList[index].author.username
            )
        }
    }
}

@Preview
@Composable
private fun MovieReviewsPreview(){

    val movieReviews = listOf(
        MovieReview(
            id = "2",
            content = "Good acting and soundtrack, but the plot was a bit predictable.",
            createdAt = getCurrentLocalDateTime(),
            updatedAt = getCurrentLocalDateTime(),
            url = "https://example.com/review/2",
            author = MovieReviewAuthor(
                name = "Mark Brown",
                username = "mbrown",
                avatarImage = "https://example.com/avatars/linaahmed.png",
                rating = 3,
            )
        ),
        MovieReview(
            id = "2",
            content = "Good acting and soundtrack, but the plot was a bit predictable.",
            createdAt = getCurrentLocalDateTime(),
            updatedAt = getCurrentLocalDateTime(),
            url = "https://example.com/review/2",
            author = MovieReviewAuthor(
                name = "Mark Brown",
                username = "mbrown",
                avatarImage = "https://example.com/avatars/linaahmed.png",
                rating = 5,
            )
        ),
        MovieReview(
            id = "3",
            content = "One of the best thrillers this year. Highly recommended!",
            createdAt = getCurrentLocalDateTime(),
            updatedAt = getCurrentLocalDateTime(),
            url = "https://example.com/review/3",
            author = MovieReviewAuthor(
                name = "Lina Ahmed",
                username = "linaahmed",
                avatarImage = "https://example.com/avatars/linaahmed.png",
                rating = 5
            )
        ),
        MovieReview(
            id = "4",
            content = "One of the best thrillers this year. Highly recommended!",
            createdAt = getCurrentLocalDateTime(),
            updatedAt = getCurrentLocalDateTime(),
            url = "https://example.com/review/3",
            author = MovieReviewAuthor(
                name = "Lina Ahmed",
                username = "linaahmed",
                avatarImage = "https://example.com/avatars/linaahmed.png",
                rating = 5
            )
        ),
        MovieReview(
            id = "5",
            content = "One of the best thrillers this year. Highly recommended!",
            createdAt = getCurrentLocalDateTime(),
            updatedAt = getCurrentLocalDateTime(),
            url = "https://example.com/review/3",
            author = MovieReviewAuthor(
                name = "Lina Ahmed",
                username = "linaahmed",
                avatarImage = "https://example.com/avatars/linaahmed.png",
                rating = 5
            )
        ),
        MovieReview(
            id = "3",
            content = "One of the best thrillers this year. Highly recommended!",
            createdAt = getCurrentLocalDateTime(),
            updatedAt = getCurrentLocalDateTime(),
            url = "https://example.com/review/3",
            author = MovieReviewAuthor(
                name = "Lina Ahmed",
                username = "linaahmed",
                avatarImage = "https://example.com/avatars/linaahmed.png",
                rating = 5
            )
        ),

    )
    MovieReviewsContent(
        movieReviewList = movieReviews

    )
}