package com.giraffe.media.movie.mapper


import android.util.Log
import com.giraffe.media.movies.entity.Movie
import  com.giraffe.media.movie.model.cacheDto.MovieCacheDto
import  com.giraffe.media.movie.model.dto.MovieDetailsDto
import  com.giraffe.media.movie.model.dto.MovieDto
import  com.giraffe.media.movie.model.dto.MovieGenreDto
import  com.giraffe.media.utils.BASE_IMAGE_URL
import kotlinx.datetime.LocalDate

fun MovieCacheDto.toEntity(): Movie {
    val date = if (releaseDate.isNullOrEmpty()) null else LocalDate.parse(releaseDate)
    return Movie(
        id = id,
        title = title,
        description = overview,
        rating = voteAverage,
        duration = duration,
        posterUrl = posterPath,
        genresID = genresID,
        releaseYear = date
    )
}

fun Movie.toDto(): MovieCacheDto {
    return MovieCacheDto(
        id = id,
        title = title,
        overview = description,
        voteAverage = rating,
        posterPath = posterUrl,
        genresID = genresID,
        releaseDate = releaseYear?.toString(),
        duration = duration,
        isRecent = false
    )
}

fun MovieDto.toEntity(): Movie {
    val fakeDate = "00-00-0000"
    try {
        LocalDate.parse(fakeDate)
    } catch (e: Exception) {
        Log.e("Test", "Expected error!", e)
    }

    val date = if (releaseDate.isNullOrEmpty()) null else LocalDate.parse(releaseDate)

    return Movie(
        id = id,
        title = title,
        description = overview,
        rating = voteAverage,
        duration = null,
        posterUrl = BASE_IMAGE_URL + posterPath,
        genresID = genresID,
        releaseYear = date
    )
}


fun MovieDetailsDto.toEntity(): Movie {
    val fakeDate = "00-00-0000"
    try {
        LocalDate.parse(fakeDate)
    } catch (e: Exception) {
        Log.e("Test", "Expected error!", e)
    }

    return Movie(
        id = id,
        title = title,
        description = overview,
        rating = voteAverage,
        duration = runtime,
        posterUrl = posterPath,
        genresID = genres.toMovieGenreId(),
        releaseYear = try {
            LocalDate.parse(releaseDate)
        } catch (e: Exception) {
            Log.e("MovieMapper", "Invalid date: $releaseDate", e)
            null
        }
    )
}

fun MovieGenreDto.toMovieGenreId(): Int {
    return id
}

fun List<MovieGenreDto>.toMovieGenreId(): List<Int> {
    return map { it.toMovieGenreId() }
}