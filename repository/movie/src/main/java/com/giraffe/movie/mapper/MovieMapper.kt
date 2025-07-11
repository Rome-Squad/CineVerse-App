package com.giraffe.movie.mapper


import com.giraffe.movie.datasource.local.cacheDto.MovieCacheDto
import com.giraffe.movie.datasource.remote.dto.MovieDetailsDto
import com.giraffe.movie.datasource.remote.dto.MovieDto
import com.giraffe.movie.datasource.remote.dto.MovieGenreDto
import com.giraffe.movies.entity.Movie
import kotlinx.datetime.LocalDate

fun MovieCacheDto.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        description = overview,
        rating = voteAverage,
        duration = duration,
        posterUrl = posterPath,
        genresID = genresID,
        releaseYear = LocalDate.parse(releaseDate)
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
        releaseDate = releaseYear.toString(),
        duration = duration
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
        releaseDate = releaseYear.toString()
    )
}

fun MovieDto.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        description = overview,
        rating = voteAverage,
        duration = null,
        posterUrl = posterPath,
        genresID = genresID,
        releaseYear = LocalDate.parse(releaseDate)
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