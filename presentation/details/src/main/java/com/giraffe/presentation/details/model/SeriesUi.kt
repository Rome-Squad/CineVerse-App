package com.giraffe.presentation.details.model

import com.giraffe.designsystem.uimodel.Poster
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
) {
    companion object {
        fun fromEntity(series: Series) = SeriesUi(
            id = series.id,
            name = series.name,
            overview = series.overview,
            rating = series.rating,
            posterUrl = series.posterUrl,
            releaseYear = series.releaseYear,
            genres = emptyList(),
            youtubeVideoId = series.youtubeVideoId
        )
    }
}

fun SeriesUi.toPoster(): Poster = Poster(
    id = id,
    name = name,
    imageUri = posterUrl .orEmpty(),
    rating = rating,
    genres = genres.joinToString(", "),
    date = releaseYear
)
