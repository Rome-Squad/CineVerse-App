package com.giraffe.profile.utils

import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.series.entity.Series
import com.giraffe.profile.screens.history.HistoryUiModel
import com.giraffe.profile.screens.history.MediaType
import com.giraffe.profile.screens.history.PosterUiState




fun Movie.toHistoryUiModel(): HistoryUiModel {
    return HistoryUiModel(
        id = id,
        title = title,
        posterUrl = posterUrl .orEmpty(),
        rating = rating,
        mediaType = MediaType.MOVIE
    )
}

fun Series.toHistoryUiModel(): HistoryUiModel {
    return HistoryUiModel(
        id = id,
        title = name,
        posterUrl = posterUrl,
        rating = rating,
        mediaType = MediaType.SERIES
    )
}

fun Series.toPosterUi(): PosterUiState {
    return PosterUiState(
        id = id,
        name = name,
        imageUri = posterUrl,
        rating = rating,
        date = releaseYear,
        mediaType =MediaType.SERIES
    )
}

fun Movie.toPosterUi(): PosterUiState {
    return PosterUiState(
        id = id,
        name = title,
        imageUri = posterUrl .orEmpty(),
        rating = rating,
        date = releaseYear.toString(),
        mediaType = MediaType.MOVIE
    )
}

