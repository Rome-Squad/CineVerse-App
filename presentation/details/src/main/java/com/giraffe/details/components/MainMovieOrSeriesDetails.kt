package com.giraffe.details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.utils.imageSourceToPainter


@Composable
fun MainMovieOrSeriesDetails(
    type: String,
    poster: Painter,
    name: String,
    genres: List<String>,
    rating: Float,
    duration: String?,
    releaseDate: String,
    onClickPlay: () -> Unit,
    onClickAdd: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.background(Theme.color.background.screen),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = poster,
            contentDescription = null,
            modifier = Modifier
                .size(width = 216.dp, height = 289.dp)
                .clip(RoundedCornerShape(Theme.radius.xl)),
            contentScale = ContentScale.Crop
        )

        MainInfoCard(
            type = type,
            modifier = Modifier.fillMaxWidth(),
            name = name,
            genres = genres.joinToString(", "),
            rating = rating,
            duration = duration,
            releaseDate = releaseDate,
            onClickAdd = onClickAdd,
            onClickPlay = onClickPlay
        )
    }
}

@Composable
@Preview()
fun PreviewMainMovieDetails() {
    CineVerseTheme(isDarkTheme = true) {
        MainMovieOrSeriesDetails(
            modifier = Modifier.width(360.dp),
            poster = R.drawable.main_poster_test.imageSourceToPainter(),
            name = "The Dark Knight",
            genres = listOf("Drama", "Action", "Crime", "Thriller", "Drama", "Action", "Crime"),
            rating = 8.5f,
            duration = "2h 32m",
            releaseDate = "2008, Jul 18",
            type = "Movie",
            onClickAdd = {},
            onClickPlay = {}
        )
    }
}