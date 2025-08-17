package com.giraffe.media.movie.util

import com.giraffe.media.movie.entity.Movie
import kotlinx.datetime.LocalDate

const val movieId = 1

val fakeMovie = fakeMovie(id = movieId, title = "Test Movie")

fun fakeMovie(
    id: Int,
    title: String,
    popularity: Float = 0f,
    recentViewedAt: Long? = null,
    genresID: List<Int> = listOf(28, 878),
    userRating: Float? = null,
): Movie {
    return Movie(
        id = id,
        name = title,
        overview = "A sample description for the movie.",
        rating = 8.0f,
        duration = 120,
        posterUrl = "https://example.com/poster.jpg",
        backdropUrl = "https://example.com/backdrop.jpg",
        youtubeVideoId = "abc123",
        genresID = genresID,
        releaseYear = LocalDate(2024, 1, 1),
        popularity = popularity,
        recentViewedAt = recentViewedAt,
        userRating = userRating
    )
}

val fakeMovies: List<Movie> = listOf(
    fakeMovie(id = movieId, title = "title 1", popularity = 100.5f, userRating = 9.1f),
    fakeMovie(id = 2, title = "title 2", popularity = 200f, genresID = listOf(28)),
    fakeMovie(id = 3, title = "title 3", popularity = 333f, genresID = listOf(878)),
    fakeMovie(id = 4, title = "title 4", popularity = 50f),
    fakeMovie(id = 5, title = "title 5", popularity = 1f),
    fakeMovie(id = 6, title = "title 6", popularity = 1000333f),
    fakeMovie(id = 7, title = "title 7", recentViewedAt = 1234),
    fakeMovie(id = 8, title = "title 8"),
    fakeMovie(id = 9, title = "title 9", userRating = 0.9f),
)