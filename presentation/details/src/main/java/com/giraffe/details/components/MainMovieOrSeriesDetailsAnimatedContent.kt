package com.giraffe.details.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    modifier: Modifier = Modifier,
    onClickAdd: (() -> Unit)? = null,
    duration: String? = null,
    animationProgress: Float,
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
            animationProgress = animationProgress,
            modifier = modifier.padding(top = 16.dp * (1f - animationProgress))
        )
    }
}