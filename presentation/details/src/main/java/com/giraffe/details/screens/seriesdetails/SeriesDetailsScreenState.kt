package com.giraffe.details.screens.seriesdetails

import com.giraffe.media.series.entity.Season
import com.giraffe.media.series.entity.Series

data class SeriesDetailsScreenState(
    val seriesDetails: SeriesUi = SeriesUi(),
    val genres: List<String> = emptyList(),
    val seasons: List<Season> = emptyList(),
    val isLoadingSeries: Boolean = false,
)

data class SeriesUi(
    val id: Int = 0,
    val name: String = "",
    val overview: String = "",
    val rating: Float = 0.0f,
    val posterUrl: String? = null,
    val releaseYear: String = "",
) {
    fun toEntity() = Series(
        id = id,
        name = name,
        overview = overview,
        rating = rating,
        posterUrl = posterUrl.toString(),
        releaseYear = releaseYear,
    )

    companion object {
        fun fromEntity(series: Series) = SeriesUi(
            id = series.id,
            name = series.name,
            overview = series.overview,
            rating = series.rating,
            posterUrl = series.posterUrl,
            releaseYear = series.releaseYear,
        )
    }

}

