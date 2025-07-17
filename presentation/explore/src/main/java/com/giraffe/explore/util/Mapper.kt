package com.giraffe.explore.util

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.explore.screen.discover.GenreUi
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.entity.MovieGenre
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.entity.SeriesGenre

const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

fun Movie.toPoster(allGenres: List<GenreUi>): Poster {
    val genreTitles = allGenres
        .filter { it.id in genresID }
        .joinToString(", ") { it.title }
        .ifBlank { null }

    return Poster(
        id = id,
        name = title,
        imageUri = "$TMDB_IMAGE_BASE_URL$posterUrl",
        rating = rating,
        genres = genreTitles,
        time = duration.toString(),
        date = releaseYear?.toString()
    )
}
fun Series.toPoster(allGenres: List<GenreUi>): Poster {
    val genreTitles = allGenres
        .filter { it.id in genreIDs }
        .joinToString(", ") { it.title }
        .ifBlank { null }

    return Poster(
        id = id,
        name = name,
        imageUri = "$TMDB_IMAGE_BASE_URL$posterUrl",
        rating = rating,
        genres = genreTitles
    )
}
fun MovieGenre.toUi() = GenreUi(id, title)
fun SeriesGenre.toUi() = GenreUi(id, name)