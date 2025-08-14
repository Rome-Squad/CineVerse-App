package com.giraffe.media.movie.mapper

import com.giraffe.media.entity.Genre
import com.giraffe.media.movie.datasource.local.cacheDto.MovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.MovieGenreCacheDto
import com.giraffe.media.movie.datasource.remote.dto.MovieDto
import com.giraffe.media.movie.datasource.remote.dto.MovieGenreDto
import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.utils.BASE_IMAGE_URL
import com.giraffe.media.utils.orEmpty
import kotlinx.datetime.LocalDate

fun MovieGenreCacheDto.toEntity() = Genre(id, name, language, count)

fun Genre.toDto() = MovieGenreCacheDto(id, title, language, rank)

fun MovieGenreDto.toEntity(language: String) = Genre(
    id = id,
    title = name,
    language = language,
    rank = 0
)

fun MovieCacheDto.toEntity() =
    Movie(
        id = id,
        name = title,
        overview = overview,
        rating = voteAverage,
        duration = duration,
        posterUrl = posterPath?.let {
            if (it.contains(BASE_IMAGE_URL))
                it
            else BASE_IMAGE_URL + it
        }.orEmpty(),
        backdropUrl = backdropPath?.let {
            if (it.contains(BASE_IMAGE_URL))
                it else
                BASE_IMAGE_URL + it
        }.orEmpty(),
        youtubeVideoId = youtubeVideoId.orEmpty(),
        genresID = genresID,
        recentViewedAt = recentViewedAt,
        popularity = popularity,
        userRating = null,
        releaseYear = releaseDate?.let { LocalDate.parse(it) }
    )

fun Movie.toCacheDto() = MovieCacheDto(
    id = id,
    title = name,
    overview = overview,
    voteAverage = rating,
    posterPath = posterUrl.let {
        if (it.contains(BASE_IMAGE_URL)) it else BASE_IMAGE_URL + it
    },
    backdropPath = backdropUrl.let {
        if (it.contains(BASE_IMAGE_URL))
            it else
            BASE_IMAGE_URL + it
    },
    youtubeVideoId = youtubeVideoId,
    genresID = genresID,
    releaseDate = releaseYear.orEmpty(),
    duration = duration,
    popularity = popularity,
    recentViewedAt = recentViewedAt,
)

fun MovieDto.toEntity(
    recentViewedAt: Long? = null,
) = Movie(
    id = id,
    name = title.orEmpty(),
    overview = overview.orEmpty(),
    rating = voteAverage.orEmpty(),
    duration = runtime,
    posterUrl = posterPath?.let {
        if (it.contains(BASE_IMAGE_URL))
            it else
            BASE_IMAGE_URL + it
    }.orEmpty(),
    backdropUrl = backdropPath?.let {
        if (it.contains(BASE_IMAGE_URL))
            it else
            BASE_IMAGE_URL + it
    }.orEmpty(),
    genresID = genresID.ifEmpty { genres.map { it.id } },
    releaseYear = releaseDate?.let { LocalDate.parse(it) },
    youtubeVideoId = youtubeVideoId.orEmpty(),
    recentViewedAt = recentViewedAt,
    popularity = popularity.orEmpty(),
    userRating = userRating
)