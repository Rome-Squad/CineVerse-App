package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.entity.Movie
import kotlinx.datetime.LocalDate

fun fakeMovie(id: Int, title: String, popularity: Float = 0f): Movie {
    return Movie(
        id = id,
        title = title,
        description = "A sample description for the movie.",
        rating = 8.0f,
        duration = 120,
        posterUrl = "https://example.com/poster.jpg",
        backdropUrl = "https://example.com/backdrop.jpg",
        youtubeVideoId = "abc123",
        genresID = listOf(28, 878),
        releaseYear = LocalDate(2024, 1, 1),
        popularity = popularity
    )
}

val fakeMovies: List<Movie> = listOf(
    fakeMovie(id = 1, title = "title 1", popularity = 100.5f),
    fakeMovie(id = 2, title = "title 2", popularity = 200f),
    fakeMovie(id = 3, title = "title 3", popularity = 333f),
    fakeMovie(id = 4, title = "title 4", popularity = 50f),
    fakeMovie(id = 5, title = "title 5", popularity = 1f),
    fakeMovie(id = 6, title = "title 6", popularity = 1000333f),
    fakeMovie(id = 7, title = "title 7"),
    fakeMovie(id = 8, title = "title 8"),
    fakeMovie(id = 9, title = "title 9"),
)