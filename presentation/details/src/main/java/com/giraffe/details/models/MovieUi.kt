package com.giraffe.details.models

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.details.utils.toFormattedDate
import com.giraffe.details.utils.toFormattedDuration
import com.giraffe.media.movies.entity.Movie

data class MovieUi(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val rating: Float = 0.0f,
    val duration: String? = null,
    val genresID: List<Int> = emptyList(),
    val genres: List<String> = emptyList(),
    val posterUrl: String? = null,
    val releaseYear: String = ""
)


fun Movie.toMovieUi() = MovieUi(
    id = id,
    title = title,
    description = description,
    rating = rating,
    duration = duration?.toFormattedDuration(),
    posterUrl = posterUrl,
    genresID = genresID,
    releaseYear = releaseYear.toString().toFormattedDate()
)

fun MovieUi.toPoster(): Poster {
    return Poster(
        id = id,
        name = title,
        imageUri = posterUrl ?: "",
        rating = rating,
        genres = if (genres.isNotEmpty()) genres.joinToString(", ") else null,
        time = duration,
        date = releaseYear
    )
}
