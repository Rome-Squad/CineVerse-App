package com.giraffe.profile.utils

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.media.entity.Genre
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.series.entity.Series
import com.giraffe.profile.model.RatedPoster
import com.giraffe.profile.screens.history.HistoryUiModel
import com.giraffe.profile.screens.history.MediaType


fun Movie.toHistoryUiModel(): HistoryUiModel {
    return HistoryUiModel(
        id = id,
        title = title,
        posterUrl = posterUrl.orEmpty(),
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

fun Series.toRatedPoster(genres: List<Genre>) = RatedPoster(
    poster = this.toPosterUi(genres),
    rating = userRating ?: 0f,
)

fun Series.toPosterUi(genres: List<Genre> = emptyList()): Poster {
    return Poster(
        id = id,
        name = name,
        genres = genres.joinToString(", ") { it.title },
        imageUri = posterUrl,
        rating = rating,
        date = releaseYear.toString(),
        mediaTypeOfPoster = "series"
    )
}

fun Movie.toRatedPoster(genres: List<Genre>) = RatedPoster(
    poster = this.toPosterUi(genres),
    rating = userRating ?: 0f,
)

fun Movie.toPosterUi(genres: List<Genre> = emptyList()): Poster {
    return Poster(
        id = id,
        name = title,
        genres = genres.joinToString(", ") { it.title },
        imageUri = posterUrl.orEmpty(),
        rating = rating,
        date = releaseYear.toString(),
        mediaTypeOfPoster = "movie"
    )
}


fun Poster.toHistoryUiModel(): HistoryUiModel {
    return HistoryUiModel(
        id = id,
        title = name,
        posterUrl = imageUri,
        rating = rating,
        mediaType = if (mediaTypeOfPoster == "movie") MediaType.MOVIE else MediaType.SERIES
    )
}