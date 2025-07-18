package com.giraffe.media.movie.mapper

import com.giraffe.media.movie.model.cacheDto.MovieCacheDto
import com.giraffe.media.movie.model.dto.MovieDto
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.utils.BASE_IMAGE_URL
import kotlinx.datetime.LocalDate

fun MovieCacheDto.toEntity() = Movie(
    id = id,
    title = title,
    description = overview,
    rating = voteAverage,
    duration = duration,
    posterUrl = posterPath,
    genresID = genresID,
    releaseYear = if (releaseDate.isNullOrEmpty()) null else LocalDate.parse(releaseDate)
)

fun Movie.toDto() = MovieCacheDto(
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

fun MovieDto.toEntity() = Movie(
    id = id,
    title = title,
    description = overview,
    rating = voteAverage,
    duration = runtime,
    posterUrl = BASE_IMAGE_URL + posterPath,
    genresID = genresID.ifEmpty { genres.map { it.id } },
    releaseYear = if (releaseDate.isNullOrEmpty()) null else LocalDate.parse(releaseDate)
)