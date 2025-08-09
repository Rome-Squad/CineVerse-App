package com.giraffe.media.series.entity

import kotlinx.datetime.LocalDate


data class Series(
    val id: Int,
    val name: String,
    val overview: String,
    val rating: Float,
    val posterUrl: String,
    val backdropUrl: String,
    val genreIDs: List<Int> = emptyList(),
    val releaseYear: LocalDate?,
    val seasons: List<Season> = emptyList(),
    val youtubeVideoId: String?,
    val popularity: Float,
    val userRating: Float?,
    val recentViewedAt: ULong?
)