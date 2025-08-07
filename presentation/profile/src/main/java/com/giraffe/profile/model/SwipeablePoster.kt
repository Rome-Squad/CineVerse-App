package com.giraffe.profile.model

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.media.entity.Genre
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.series.entity.Series

data class SwipeablePoster(
    val poster: Poster,
    val isSwiped: Boolean
)

fun Movie.toPoster(allGenres: List<Genre> = emptyList()): Poster {
    val genreTitles = allGenres
        .filter { it.id in genresID }
        .joinToString(", ") { it.title }
        .ifBlank { null }


    val date = releaseYear?.let {
        "${it.year}, ${
            it.month.name.lowercase().replaceFirstChar { char -> char.uppercase() }.take(3)
        } ${it.day}"
    } ?: ""

    return Poster(
        id = id,
        name = title,
        imageUri = posterUrl.orEmpty(),
        rating = rating,
        genres = genreTitles,
        time = null,
        date = date,
        mediaTypeOfPoster = Poster.Type.MOVIE.value
    )
}


fun Series.toPoster(allGenres: List<Genre> = emptyList()): Poster {
    val genreTitles = allGenres
        .filter { it.id in genreIDs }
        .joinToString(", ") { it.title }
        .ifBlank { null }


    return Poster(
        id = id,
        name = name,
        imageUri = posterUrl.orEmpty(),
        rating = rating,
        genres = genreTitles,
        time = null,
        date = releaseYear,
        mediaTypeOfPoster = Poster.Type.MOVIE.value
    )
}


fun Movie.toSwipeablePoster(
    isSwiped: Boolean = false,
    genres: List<Genre> = emptyList()
) = SwipeablePoster(
    poster = toPoster(genres),
    isSwiped = isSwiped
)