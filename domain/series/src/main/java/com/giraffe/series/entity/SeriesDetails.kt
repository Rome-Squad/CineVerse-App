package com.giraffe.series.entity

import kotlinx.datetime.LocalDate

data class SeriesDetails(
    val id: Int,
    val posterUrl: String,
    val name: String,
    val genres: List<GenreInfo> = emptyList(),
    val rating: Float,
    val releaseYear: LocalDate?,
    val overview: String,
    val seasons: List<Season> = emptyList()
)

sealed interface GenreInfo {
    data class GenreID(val id: Int) : GenreInfo
    data class FullGenre(val seriesGenre: SeriesGenre) : GenreInfo
}