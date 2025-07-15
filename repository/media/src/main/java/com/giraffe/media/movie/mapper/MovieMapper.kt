package com.giraffe.media.movie.mapper


import com.giraffe.movie.datasource.local.cacheDto.MovieCacheDto
import com.giraffe.movie.datasource.remote.dto.MovieDetailsDto
import com.giraffe.movie.datasource.remote.dto.MovieDto
import com.giraffe.movie.datasource.remote.dto.MovieGenreDto
import com.giraffe.movie.utils.BASE_IMAGE_URL
import com.giraffe.movies.entity.Movie
import kotlinx.datetime.LocalDate

fun MovieCacheDto.toMovie(): Movie {
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

fun Movie.toMovieCacheDto(): MovieCacheDto {
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

fun Movie.toMovieDto(): MovieDto {
    return MovieDto(
        id = id,
        title = title,
        overview = description,
        voteAverage = rating,
        posterPath = posterUrl,
        genresID = genresID,
        releaseDate = releaseYear?.toString()
    )
}

fun MovieDto.toMovie(): Movie {
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
    return Movie(
        id = id,
        title = title,
        description = overview,
        rating = voteAverage,
        duration = runtime,
        posterUrl = posterPath,
        genresID = genres.toMovieGenreId(),
        releaseYear = LocalDate.parse(releaseDate)
    )
}

fun MovieGenreDto.toMovieGenreId(): Int {
    return id
}

fun List<MovieGenreDto>.toMovieGenreId(): List<Int> {
    return map { it.toMovieGenreId() }
}