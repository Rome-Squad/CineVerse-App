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

fun Genre.toCacheDto() = MovieGenreCacheDto(
    id = id,
    name = title,
    rank = rank
)

fun MovieGenreDto.toEntity() = Genre(
    id = id,
    title = name,
    rank = 0
)

fun MovieWithRecentlyViewedAt.toEntity() = Movie(
    id = movie.id,
    name = movie.name,
    overview = movie.overview,
    rating = movie.rating,
    duration = movie.duration,
    posterUrl = movie.posterUrl?.let {
        if (it.contains(BASE_IMAGE_URL))
            it
        else BASE_IMAGE_URL + it
    }.orEmpty(),
    backdropUrl = movie.backdropUrl?.let {
        if (it.contains(BASE_IMAGE_URL))
            it else
            BASE_IMAGE_URL + it
    }.orEmpty(),
    youtubeVideoId = movie.youtubeVideoId.orEmpty(),
    genresID = movie.genresID,
    popularity = movie.popularity,
    userRating = null,
    recentViewedAt = recentViewedAt,
    releaseYear = movie.releaseYear?.let { LocalDate.parse(it) }
)

fun MovieCacheDto.toEntity() =
    Movie(
        id = id,
        name = name,
        overview = overview,
        rating = rating,
        duration = duration,
        posterUrl = posterUrl?.let {
            if (it.contains(BASE_IMAGE_URL))
                it
            else BASE_IMAGE_URL + it
        }.orEmpty(),
        backdropUrl = backdropUrl?.let {
            if (it.contains(BASE_IMAGE_URL))
                it else
                BASE_IMAGE_URL + it
        }.orEmpty(),
        youtubeVideoId = youtubeVideoId.orEmpty(),
        genresID = genresID,
        popularity = popularity,
        userRating = null,
        recentViewedAt = null,
        releaseYear = releaseYear?.let { LocalDate.parse(it) }
    )

fun Movie.toCacheDto() = MovieCacheDto(
    id = id,
    name = name,
    overview = overview,
    rating = rating,
    posterUrl = posterUrl.let {
        if (it.contains(BASE_IMAGE_URL)) it else BASE_IMAGE_URL + it
    },
    backdropUrl = backdropUrl.let {
        if (it.contains(BASE_IMAGE_URL))
            it else
            BASE_IMAGE_URL + it
    },
    youtubeVideoId = youtubeVideoId,
    genresID = genresID,
    releaseYear = releaseYear.orEmptyString(),
    duration = duration,
    popularity = popularity,
)

fun MovieDto.toCacheDto() = MovieCacheDto(
    id = id,
    name = title.orEmpty(),
    overview = overview.orEmpty(),
    rating = voteAverage.orZero(),
    posterUrl = posterPath?.let {
        if (it.contains(BASE_IMAGE_URL)) it else BASE_IMAGE_URL + it
    },
    backdropUrl = backdropPath?.let {
        if (it.contains(BASE_IMAGE_URL))
            it else
            BASE_IMAGE_URL + it
    },
    youtubeVideoId = youtubeVideoId,
    genresID = genresID.ifEmpty { genres.map { it.id } },
    releaseYear = releaseDate,
    duration = runtime,
    popularity = popularity.orZero(),
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
    releaseYear = releaseDate?.let { if (it.isEmpty() || it.isBlank()) null else LocalDate.parse(it) },
    youtubeVideoId = youtubeVideoId.orEmpty(),
    recentViewedAt = recentViewedAt,
    popularity = popularity.orZero(),
    userRating = userRating
)