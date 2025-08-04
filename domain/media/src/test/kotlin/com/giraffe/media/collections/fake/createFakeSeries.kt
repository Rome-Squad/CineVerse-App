package com.giraffe.media.collections.fake

import com.giraffe.media.series.entity.Season
import com.giraffe.media.series.entity.Series

fun createFakeSeries(
    id: Int = 1,
    name: String = "Default Title",
    overview: String = "",
    rating: Float = 0f,
    posterUrl: String = "",
    backdropUrl: String = "",
    genreIDs: List<Int> = listOf(),
    releaseYear: String = "2025-08-03",
    seasons: List<Season> = emptyList(),
    youtubeVideoId: String = ""
): Series {
    return Series(
        id = id,
        name = name,
        overview = overview,
        rating = rating,
        posterUrl = posterUrl,
        backdropUrl = backdropUrl,
        genreIDs = genreIDs,
        releaseYear = releaseYear,
        seasons = seasons,
        youtubeVideoId = youtubeVideoId
    )
}
