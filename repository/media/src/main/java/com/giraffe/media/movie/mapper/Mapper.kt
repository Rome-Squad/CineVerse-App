package com.giraffe.media.movie.mapper

import com.giraffe.media.entity.Genre
import com.giraffe.media.entity.Review
import com.giraffe.media.movie.datasource.local.cacheDto.MovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.MovieGenreCacheDto
import com.giraffe.media.movie.datasource.remote.dto.MovieDto
import com.giraffe.media.movie.datasource.remote.dto.MovieGenreDto
import com.giraffe.media.movie.datasource.remote.dto.MovieReviewDto
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.utils.BASE_IMAGE_URL
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

fun MovieGenreCacheDto.toEntity() = Genre(id, name, count)

fun Genre.toDto() = MovieGenreCacheDto(id, title, rank)

fun MovieGenreDto.toEntity() = Genre(
    id = id,
    title = name,
    rank = 0
)

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

fun Movie.toCacheDto() = MovieCacheDto(
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


@OptIn(ExperimentalTime::class)
fun MovieReviewDto.toEntity(): Review {
    return Review(
        id = this.id,
        content = this.content,
        createdAt = try {
            val instant = Instant.parse(this.createdAt)
            instant.toLocalDateTime(TimeZone.UTC)
        } catch (e: Exception) {
            null
        },

        authorName = (this.authorDetails.name?.takeIf { it.isNotBlank() }
            ?: this.author).toString(),
        authorUserName = this.authorDetails.username,
        authorImageUrl = BASE_IMAGE_URL + this.authorDetails.avatarPath,
        rating = (this.authorDetails.rating ?: 0f).toInt()
    )
}