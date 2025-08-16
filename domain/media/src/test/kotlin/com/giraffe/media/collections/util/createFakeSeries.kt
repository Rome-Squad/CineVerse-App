package com.giraffe.media.collections.util

import com.giraffe.media.series.entity.Season
import com.giraffe.media.series.entity.Series
import kotlinx.datetime.LocalDate

fun createFakeSeries(
    id: Int = 1,
    name: String = "Default Title",
    overview: String = "",
    rating: Float = 0f,
    posterUrl: String = "",
    backdropUrl: String = "",
    genreIDs: List<Int> = listOf(),
    releaseYear: LocalDate? = null,
    seasons: List<Season> = emptyList(),
    youtubeVideoId: String = "",
    popularity: Float = 0f,
    userRating: Float = 0f,
    recentViewedAt: ULong = 0u
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


fun createFakeSeason(id: Int) = Season(
    id = id,
    overview = "",
    rating = 0f,
    posterUrl = "",
    seasonNumber = 1,
    releaseYear = null,
    episodeCount = 1
)
