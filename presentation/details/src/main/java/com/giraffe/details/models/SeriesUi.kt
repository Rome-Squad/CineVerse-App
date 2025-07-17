package com.giraffe.details.models

import com.giraffe.media.series.entity.Series

data class SeriesUi(
    val id: Int = 0,
    val name: String = "",
    val overview: String = "",
    val rating: Float = 0.0f,
    val posterUrl: String? = null,
    val releaseYear: String = "",
) {
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