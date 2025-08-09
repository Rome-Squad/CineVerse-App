package com.giraffe.presentation.details.model

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.media.movie.entity.Movie
import com.giraffe.presentation.details.utils.toFormattedDate
import com.giraffe.presentation.details.utils.toFormattedDuration

data class MovieUi(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val rating: Float = 0.0f,
    val duration: String? = null,
    val genresID: List<Int> = emptyList(),
    val genres: List<String> = emptyList(),
    val posterUrl: String? = null,
    val backdropUrl: String? = null,
    val releaseYear: String? = null,
    val youtubeVideoId: String = ""
)


fun Movie.toMovieUi() = MovieUi(
    id = id,
    title = title,
    description = description,
    rating = rating,
    duration = if (duration != null && duration!! > 0) duration?.toFormattedDuration() else null,
    posterUrl = posterUrl,
    backdropUrl = backdropUrl,
    genresID = genresID,
    releaseYear = if (releaseYear != null) releaseYear.toString().toFormattedDate() else null,
    youtubeVideoId = youtubeVideoId.orEmpty()

)

fun MovieUi.toPoster(): Poster {
    return Poster(
        id = id,
        name = title,
        imageUri = posterUrl.orEmpty(),
        rating = rating,
        genres = if (genres.isNotEmpty()) genres.joinToString(", ") else null,
        time = duration,
        date = releaseYear
    )
}
