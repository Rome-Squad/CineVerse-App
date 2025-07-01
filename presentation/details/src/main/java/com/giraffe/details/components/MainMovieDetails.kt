package com.giraffe.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.designsystem.R


@Composable
fun MainMovieDetails(
    posterRes: Int,
    title: String,
    genres: String,
    rating: String,
    duration: String,
    releaseDate: String,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .background(Theme.color.background.screen)
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        MainMoviePoster(
            modifier = Modifier.width(216.dp).height(289.dp),
            posterUrl = posterRes)

        MainInfoCardSection(
            modifier = Modifier
                .fillMaxWidth(),
            title = title,
            genres = genres,
            rating = rating,
            duration = duration,
            releaseDate = releaseDate
        )

        MinimizedInfoRow(posterRes, title)
    }
}
@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
fun PreviewMainMovieDetails() {

    CineVerseTheme (
        isDarkTheme = true
    ) {
        MainMovieDetails(
            modifier = Modifier
                .width(360.dp),
            posterRes = R.drawable.main_poster_test,
            title = "The Dark Knight",
            genres = "Drama, Action, Crime, Thriller",
            rating = "8.5",
            duration = "2h 32m",
            releaseDate = "2008, Jul 18"
        )

    }
}