package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.entity.Movie
import kotlinx.datetime.LocalDate

fun fakeMovie(id: Int, title: String): Movie {
    return Movie(
        id = id,
        title = title,
        description = "A sample description for the movie.",
        rating = 8.0f,
        duration = 120,
        posterUrl = "https://example.com/poster.jpg",
        backdropUrl = "https://example.com/backdrop.jpg",
        genresID = listOf(28, 878),
        releaseYear = LocalDate(2024, 1, 1)
    )
}