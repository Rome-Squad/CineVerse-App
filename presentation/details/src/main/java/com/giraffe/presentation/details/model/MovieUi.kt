package com.giraffe.presentation.details.model

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.media.movie.entity.Movie
import com.giraffe.presentation.details.utils.toFormattedDate
import com.giraffe.presentation.details.utils.toFormattedDuration

data class MovieUi(
    val id: Int = 0,
    val name: String = "",
    val overview: String = "",
    val rating: Float = 0.0f,
    val duration: String = "",
    val genresID: List<Int> = emptyList(),
    val genres: List<String> = emptyList(),
    val posterUrl: String? = null,
    val backdropUrl: String? = null,
    val releaseYear: String = "",
    val youtubeVideoId: String = ""
)


fun Movie.toMovieUi() = MovieUi(
    id = id,
    name = name,
    overview = overview,
    rating = rating,
    duration = duration.toFormattedDuration(),
    posterUrl = posterUrl,
    backdropUrl = backdropUrl,
    genresID = genresID,
    releaseYear = releaseYear.toFormattedDate(),
    youtubeVideoId = youtubeVideoId.orEmpty()

)

fun MovieUi.toPoster(): Poster {
    return Poster(
        id = id,
        name = name,
        imageUri = posterUrl.orEmpty(),
        rating = rating,
        genres = if (genres.isNotEmpty()) genres.joinToString(", ") else null,
        time = duration,
        date = releaseYear
    )
}
