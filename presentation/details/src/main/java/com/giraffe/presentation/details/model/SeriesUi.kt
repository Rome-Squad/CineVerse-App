package com.giraffe.presentation.details.model

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.media.entity.Genre
import com.giraffe.media.series.entity.Series

data class SeriesUi(
    val id: Int = 0,
    val name: String = "",
    val overview: String = "",
    val rating: Float = 0.0f,
    val posterUrl: String? = null,
    val releaseYear: String = "",
    val youtubeVideoId: String = "",
    val genres: List<String> = emptyList(),
)

fun Series.toSeriesUi(
    genres: List<Genre> = emptyList()
) = SeriesUi(
    id = id,
    name = name,
    overview = overview,
    rating = rating,
    posterUrl = posterUrl,
    releaseYear = releaseYear,
    genres = genres.map { it.title },
    youtubeVideoId = youtubeVideoId
)

fun SeriesUi.toPoster(): Poster = Poster(
    id = id,
    name = name,
    imageUri = posterUrl .orEmpty(),
    rating = rating,
    genres = genres.joinToString(", "),
    date = releaseYear
)
