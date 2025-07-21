package com.giraffe.explore.util

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.explore.screen.discover.GenreUi
import com.giraffe.explore.screen.searchresult.ActorUi
import com.giraffe.media.entity.Genre
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.person.entity.Person
import com.giraffe.media.series.entity.Series

fun Movie.toPoster(allGenres: List<GenreUi> = emptyList()): Poster {
    val genreTitles = allGenres
        .filter { it.id in genresID }
        .joinToString(", ") { it.title }
        .ifBlank { null }

    return Poster(
        id = id,
        name = title,
        imageUri = posterUrl.orEmpty(),
        rating = rating,
        genres = genreTitles,
        time = duration.toString(),
        date = releaseYear?.toString()
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
        date = releaseYear,
    )
}

fun Person.toPoster() = Poster(
    id = id,
    name = name,
    imageUri = imageUrl.toString(),
    rating = 0f,
)

fun Person.toUi() = ActorUi(id, name, imageUrl.toString())
fun Genre.toUi() = GenreUi(id, title)
