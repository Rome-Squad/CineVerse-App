package com.giraffe.presentation.explore.util

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.media.entity.Genre
import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.person.entity.Person
import com.giraffe.media.series.entity.Series
import com.giraffe.presentation.explore.model.GenreUi


fun Movie.toPoster(allGenres: List<GenreUi> = emptyList()): Poster {
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
        name = name,
        imageUri = posterUrl,
        rating = rating,
        genres = genreTitles,
        time = duration.orEmpty(),
        date = date,
        mediaTypeOfPoster = Poster.Type.MOVIE.value
    )
}


fun Series.toPoster(allGenres: List<GenreUi> = emptyList()): Poster {
    val genreTitles = allGenres
        .filter { it.id in genreIDs }
        .joinToString(", ") { it.title }
        .ifBlank { null }

    return Poster(
        id = id,
        name = name,
        imageUri = "https://image.tmdb.org/t/p/w500$posterUrl",
        rating = rating,
        genres = genreTitles,
        date = releaseYear.orEmpty(),
        mediaTypeOfPoster = Poster.Type.SERIES.value
    )
}

fun Person.toPoster() = Poster(
    id = id,
    name = name,
    imageUri = imageUrl.orEmpty(),
    rating = 0f,
    mediaTypeOfPoster = Poster.Type.PERSON.value
)

fun Genre.toUi() = GenreUi(id, title)