package com.giraffe.media.movie.mapper

import com.giraffe.media.entity.Genre
import com.giraffe.media.movie.datasource.local.cacheDto.MovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.MovieGenreCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.MovieWithRecentlyViewedAt
import com.giraffe.media.movie.datasource.remote.dto.MovieDto
import com.giraffe.media.movie.datasource.remote.dto.MovieGenreDto
import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.utils.BASE_IMAGE_URL
import com.giraffe.media.utils.orEmptyString
import com.giraffe.media.utils.orZero
import kotlinx.datetime.LocalDate

fun MovieGenreCacheDto.toEntity() = Genre(id, name, rank)

fun Genre.toDto() = MovieGenreCacheDto(id, title, rank)

fun MovieGenreDto.toEntity() = Genre(
    id = id,
    title = name,
    rank = 0
)

fun MovieWithRecentlyViewedAt.toEntity() = Movie(
    id = movie.id,
    name = movie.title,
    overview = movie.overview,
    rating = movie.voteAverage,
    duration = movie.duration,
    posterUrl = movie.posterPath?.let {
        if (it.contains(BASE_IMAGE_URL))
            it
        else BASE_IMAGE_URL + it
    }.orEmpty(),
    backdropUrl = movie.backdropPath?.let {
        if (it.contains(BASE_IMAGE_URL))
            it else
            BASE_IMAGE_URL + it
    }.orEmpty(),
    youtubeVideoId = movie.youtubeVideoId.orEmpty(),
    genresID = movie.genresID,
    popularity = movie.popularity,
    userRating = null,
    recentViewedAt = recentViewedAt,
    releaseYear = movie.releaseDate?.let { LocalDate.parse(it) }
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
        popularity = popularity,
        userRating = null,
        recentViewedAt = null,
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
    releaseDate = releaseYear.orEmptyString(),
    duration = duration,
    popularity = popularity,
)

fun MovieDto.toEntity(
    recentViewedAt: Long? = null,
) = Movie(
    id = id,
    name = title.orEmpty(),
    overview = overview.orEmpty(),
    rating = voteAverage.orZero(),
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
    popularity = popularity.orZero(),
    userRating = userRating
)