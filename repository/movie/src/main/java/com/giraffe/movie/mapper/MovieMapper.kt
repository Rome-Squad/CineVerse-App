package com.giraffe.movie.mapper


import com.giraffe.movies.entity.Movie
import com.giraffe.movie.datasource.local.cacheDto.MovieCacheDto
import com.giraffe.movie.datasource.remote.dto.MovieDto

fun MovieCacheDto.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        description = overview,
        rate = voteAverage,
        duration = duration,
        posterUrl = posterPath,
        genresID = genresID,
        releaseYear = releaseDate
    )
}

fun Movie.toMovieCacheDto(): MovieCacheDto {
    return MovieCacheDto(
        id = id,
        title = title,
        overview = description,
        voteAverage = rate,
        posterPath = posterUrl,
        genresID = genresID,
        releaseDate = releaseYear,
        duration = duration
    )
}

fun Movie.toMovieDto(): MovieDto {
    return MovieDto(
        id = id,
        title = title,
        overview = description,
        voteAverage = rate,
        posterPath = posterUrl,
        genresID = genresID,
        releaseDate = releaseYear
    )
}

fun MovieDto.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        description = overview,
        rate = voteAverage,
        duration = null,
        posterUrl = posterPath,
        genresID = genresID,
        releaseYear = releaseDate
    )
}