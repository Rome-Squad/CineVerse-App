package com.giraffe.movie.datasource.local.mapper


import com.giraffe.movies.entity.Movie
import com.giraffe.movie.datasource.local.cacheDto.MovieCacheDto

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

fun Movie.toMovieDto(): MovieCacheDto {
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
