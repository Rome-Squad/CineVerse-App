package com.giraffe.media.collections.fake

import com.giraffe.media.movies.entity.Movie
import kotlinx.datetime.LocalDate


fun createFakeMovie(
    id: Int = 1,
    title: String = "Default Title",
    description: String = "",
    rating: Float = 0f,
    duration: Int = 0,
    posterUrl: String = "",
    backdropUrl: String = "",
    genresID: List<Int> = listOf(),
    releaseYear: LocalDate = LocalDate.parse("2025-08-03"),
    youtubeVideoId: String = "",
): Movie {
    return Movie(
        id = id,
        title = title,
        description = description,
        rating = rating,
        duration = duration,
        posterUrl = posterUrl,
        backdropUrl = backdropUrl,
        genresID = genresID,
        releaseYear = releaseYear,
        youtubeVideoId = youtubeVideoId,
    )
}