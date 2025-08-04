package com.giraffe.details.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun MainMovieOrSeriesDetailsAnimatedContent(
    type: String,
    name: String,
    imageUrl: String?,
    rating: Float,
    genres: List<String>,
    releaseYear: String,
    isPlayButtonEnabled: Boolean,
    onClickPlay: () -> Unit,
    onClickAdd: () -> Unit,
    modifier: Modifier = Modifier,
    duration: String? = null,
    topPadding: Dp = 0.dp,
    textTopPadding: Dp,
    imageWidth: Dp,
    imageHeight: Dp,
    startAndBottomPadding: Dp,
) {
    if (name.isNotBlank()) {
        MainMovieOrSeriesDetails(
            type = type,
            posterUrl = imageUrl,
            name = name,
            genres = genres,
            rating = rating,
            duration = duration,
            releaseDate = releaseYear,
            onClickAdd = onClickAdd,
            onClickPlay = onClickPlay,
            isPlayButtonEnabled = isPlayButtonEnabled,
            textTopPadding = textTopPadding,
            imageWidth = imageWidth,
            imageHeight = imageHeight,
            startAndBottomPadding = startAndBottomPadding,
            modifier = modifier.padding(top = topPadding)
        )
    }
}