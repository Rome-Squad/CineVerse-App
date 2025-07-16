package com.giraffe.details.screens.seriesdetails

import com.giraffe.media.series.entity.Season
import com.giraffe.media.series.entity.Series

data class SeriesDetailsScreenState(
    val seriesDetails: SeriesUi = SeriesUi(),
    val genres: List<String> = emptyList(),
    val seasons: List<SeasonUi> = emptyList(),
    val isLoadingSeries: Boolean = true,
    val isLoadingSeason: Boolean = true,
    val isLoadingGenres: Boolean = true,
)

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