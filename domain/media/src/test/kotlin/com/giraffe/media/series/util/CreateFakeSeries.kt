package com.giraffe.media.series.util

import com.giraffe.media.series.entity.Season
import com.giraffe.media.series.entity.Series
import kotlinx.datetime.LocalDate

fun createFakeSeries(
    id: Int = 1,
    name: String = "",
    overview: String = "",
    rating: Float = 0f,
    posterUrl: String = "",
    backdropUrl: String = "",
    youtubeVideoId: String = "",
    popularity: Float = 0f,
    userRating: Float = 0f,
    recentViewedAt: Long = 0L,
    releaseYear: LocalDate? = null,
    seasons: List<Season> = emptyList(),
    genreIDs: List<Int> = listOf(),
) = Series(
    id = id,
    name = name,
    overview = overview,
    rating = rating,
    posterUrl = posterUrl,
    backdropUrl = backdropUrl,
    genreIDs = genreIDs,
    releaseYear = releaseYear,
    seasons = seasons,
    youtubeVideoId = youtubeVideoId,
    popularity = popularity,
    userRating = userRating,
    recentViewedAt = recentViewedAt
)