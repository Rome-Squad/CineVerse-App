package com.giraffe.media.movie.mapper

import com.giraffe.media.entity.Genre
import com.giraffe.media.movie.datasource.local.cacheDto.MovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.MovieGenreCacheDto
import com.giraffe.media.movie.datasource.remote.dto.MovieDto
import com.giraffe.media.movie.datasource.remote.dto.MovieGenreDto
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.utils.BASE_IMAGE_URL
import kotlinx.datetime.LocalDate

fun MovieGenreCacheDto.toEntity() = Genre(id, name, count)

fun Genre.toDto() = MovieGenreCacheDto(id, title, rank)

fun MovieGenreDto.toEntity() = Genre(
    id = id,
    title = name,
    rank = 0
)

fun MovieCacheDto.toEntity() =
    Movie(
        id = id,
        title = title,
        description = overview,
        rating = voteAverage,
        duration = duration,
        posterUrl = posterPath?.let {
            if (it.contains(BASE_IMAGE_URL))
                it
            else BASE_IMAGE_URL + it
        },
        backdropUrl = backdropPath?.let {
            if (it.contains(BASE_IMAGE_URL))
                it else
                BASE_IMAGE_URL + it
        },
        youtubeVideoId = youtubeVideoId.orEmpty(),
        genresID = genresID,
        recentViewedAt = recentViewedAt,
        recentReleasedAt = recentReleasedAt,
        upcomingAt = upcomingAt,
        popularity = popularity,
        releaseYear = if (releaseDate.isNullOrEmpty())
            null
        else
            LocalDate.parse(releaseDate)
    )

fun Movie.toCacheDto() = MovieCacheDto(
    id = id,
    title = title,
    overview = description,
    voteAverage = rating,
    posterPath = posterUrl?.let {
        if (it.contains(BASE_IMAGE_URL)) it else BASE_IMAGE_URL + it
    },
    backdropPath = backdropUrl?.let {
        if (it.contains(BASE_IMAGE_URL))
            it else
            BASE_IMAGE_URL + it
    },
    youtubeVideoId = youtubeVideoId.orEmpty(),
    genresID = genresID,
    releaseDate = releaseYear?.toString(),
    duration = duration,
    popularity = popularity,
//    recentViewedAt = recentViewedAt,
//    recentReleasedAt = recentReleasedAt,
//    upcomingAt = upcomingAt,
)

fun MovieDto.toEntity(
    recentViewedAt: Long? = null,
    recentReleasedAt: Long? = null,
    upcomingAt: Long? = null
) = Movie(
    id = id,
    title = title.orEmpty(),
    description = overview.orEmpty(),
    rating = voteAverage ?: 0f,
    duration = runtime,
    posterUrl = posterPath?.let {
        if (it.contains(BASE_IMAGE_URL))
            it else
            BASE_IMAGE_URL + it
    },
    backdropUrl = backdropPath?.let {
        if (it.contains(BASE_IMAGE_URL))
            it else
            BASE_IMAGE_URL + it
    },
    genresID = genresID.ifEmpty { genres.map { it.id } },
    releaseYear = if (releaseDate.isNullOrEmpty()) null else LocalDate.parse(releaseDate),
    youtubeVideoId = youtubeVideoId.orEmpty(),
    recentViewedAt = recentViewedAt,
    recentReleasedAt = recentReleasedAt,
    upcomingAt = upcomingAt,
    popularity = popularity ?: 0f
)