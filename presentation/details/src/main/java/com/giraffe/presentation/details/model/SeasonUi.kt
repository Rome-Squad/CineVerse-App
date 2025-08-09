package com.giraffe.presentation.details.model

import com.giraffe.media.series.entity.Season

data class SeasonUi(
    val id: Int = 0,
    val overview: String = "",
    val rating: Float = 0.0f,
    val posterUrl: String? = null,
    val releaseYear: String = "",
    val seasonNumber: Int = 0,
    val episodeCount: Int = 0
) {
    companion object {
        fun fromEntity(season: Season) = SeasonUi(
            id = season.id,
            overview = season.overview,
            rating = season.rating,
            posterUrl = season.posterUrl,
            releaseYear = season.releaseYear,
            seasonNumber = season.seasonNumber,
            episodeCount = season.episodeCount
        )
    }
}