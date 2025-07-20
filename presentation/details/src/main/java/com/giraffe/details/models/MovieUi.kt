package com.giraffe.details.models

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.media.movies.entity.Movie
import kotlinx.datetime.LocalDate

data class MovieUi(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val rating: Float = 0.0f,
    val duration: Int? = null,
    val genresID: List<Int> = emptyList(),
    val genres: List<String> = emptyList(),
    val posterUrl: String? = null,
    val releaseYear: String = ""
) {
    companion object {
        fun fromEntity(movie: Movie): MovieUi = MovieUi(
            id = movie.id,
            title = movie.title,
            description = movie.description,
            rating = movie.rating,
            duration = movie.duration,
            posterUrl = movie.posterUrl,
            genresID = movie.genresID,
            releaseYear = movie.releaseYear.toString()
        )
    }
}



fun MovieUi.toMovieEntity() = Movie(
    id = id,
    title = title,
    description = description,
    rating = rating,
    duration = duration,
    posterUrl = posterUrl,
    genresID = genresID,
    releaseYear = LocalDate.parse(releaseYear)
)

fun Movie.MovieUi()= MovieUi(
    id = id,
    title = title,
    description = description,
    rating = rating,
    duration = duration,
    posterUrl = posterUrl,
    genresID = genresID,
    releaseYear = releaseYear.toString()
)

fun MovieUi.toPoster(): Poster {
    return Poster(
        id = id,
        name = title,
        imageUri = posterUrl ?: "",
        rating = rating,
        genres = if (genres.isNotEmpty()) genres.joinToString(", ") else null,
        time = duration?.let { "$it min" },
        date = releaseYear
    )
}
