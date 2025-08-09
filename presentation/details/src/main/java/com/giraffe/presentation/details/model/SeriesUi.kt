package com.giraffe.presentation.details.model

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.media.series.entity.Series
import com.giraffe.presentation.details.utils.toFormattedDate

data class SeriesUi(
    val id: Int = 0,
    val name: String = "",
    val overview: String = "",
    val rating: Float = 0.0f,
    val posterUrl: String? = null,
    val releaseYear: String? = null,
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
            releaseYear = series.releaseYear.toString().toFormattedDate(),
            genres = emptyList(),
            youtubeVideoId = series.youtubeVideoId.orEmpty(),
        )
    }
}

fun SeriesUi.toPoster(): Poster = Poster(
    id = id,
    name = name,
    imageUri = posterUrl.orEmpty(),
    rating = rating,
    genres = genres.joinToString(", "),
    date = releaseYear
)
