package com.giraffe.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.Theme
import com.giraffe.imageviewer.component.SafeIslamicImage

@Composable
fun MainMovieOrSeriesDetails(
    type: String,
    posterUrl: String,
    name: String,
    genres: List<String>,
    rating: Float,
    duration: String?,
    releaseDate: String,
    modifier: Modifier = Modifier,
    onPlayMovieClick: () -> Unit = {},
    onAddToCollectionClick: () -> Unit = {}
) {
    Column(
        modifier = modifier.background(Theme.color.background.screen),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SafeIslamicImage(
            imageUrl = posterUrl,
            contentDescription = posterUrl,
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
            onClickAdd = onAddToCollectionClick,
            onClickPlay = onPlayMovieClick
        )
    }
}