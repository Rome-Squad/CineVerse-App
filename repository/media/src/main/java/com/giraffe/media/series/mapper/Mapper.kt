package com.giraffe.media.series.mapper

import com.giraffe.media.entity.Genre
import com.giraffe.media.series.datasource.local.cacheDto.SeriesCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.SeriesGenreCacheDto
import com.giraffe.media.series.datasource.remote.dto.GenreDto
import com.giraffe.media.series.datasource.remote.dto.SeasonDto
import com.giraffe.media.series.datasource.remote.dto.SeriesDetailsDto
import com.giraffe.media.series.datasource.remote.dto.SeriesDto
import com.giraffe.media.series.entity.Season
import com.giraffe.media.series.entity.Series
import com.giraffe.media.utils.BASE_IMAGE_URL
import com.giraffe.media.utils.orEmpty
import kotlinx.datetime.LocalDate

// region series cache
fun SeriesCacheDto.toEntity() = Series(
    id = id,
    name = name,
    overview = overview,
    rating = rate,
    posterUrl = posterUrl,
    backdropUrl = backdropUrl,
    genreIDs = genresID,
    releaseYear = releaseYear?.let { LocalDate.parse(it) },
    popularity = popularity.orEmpty(),
    youtubeVideoId = youtubeVideoId,
    userRating = userRating,
    recentViewedAt = recentViewedAt?.toULong(),
    seasons = emptyList()
)

fun Series.toCacheDto() = SeriesCacheDto(
    id = id,
    name = name,
    overview = overview,
    rate = rating,
    posterUrl = posterUrl,
    backdropUrl = backdropUrl,
    genresID = genreIDs,
    releaseYear = releaseYear.toString(),
    popularity = popularity,
    youtubeVideoId = youtubeVideoId,
    userRating = userRating,
)
// endregion

// region Series Genre
fun GenreDto.toEntity() = Genre(
    id = id,
    title = name,
    rank = 0
)

fun SeriesGenreCacheDto.toEntity() = Genre(
    id = id,
    title = name,
    rank = count
)

fun Genre.toCacheDto() = SeriesGenreCacheDto(
    id = id,
    name = title,
    count = rank
)
// endregion

// region DTO to Entity
fun SeriesDto.toEntity() = Series(
    id = id,
    name = name.orEmpty(),
    overview = overview.orEmpty(),
    rating = voteAverage?.toFloat().orEmpty(),
    posterUrl = posterUrl?.let {
        if (it.contains(BASE_IMAGE_URL))
            it
        else BASE_IMAGE_URL + it
    }.orEmpty(),
    backdropUrl = backdropUrl?.let {
        if (it.contains(BASE_IMAGE_URL))
            it
        else BASE_IMAGE_URL + it
    }.orEmpty(),
    genreIDs = genreIds,
    releaseYear = releaseYear?.let { LocalDate.parse(it) },
    popularity = popularity?.toFloat().orEmpty(),
    userRating = userRating,
    youtubeVideoId = null,
    recentViewedAt = null,
    seasons = emptyList()
)

fun SeasonDto.toEntity() = Season(
    id = id,
    overview = overview,
    rating = voteAverage,
    posterUrl = posterUrl?.let {
        if (it.contains(BASE_IMAGE_URL))
            it
        else BASE_IMAGE_URL + it
    }.orEmpty(),
    seasonNumber = seasonNumber,
    episodeCount = episodeCount,
    releaseYear = releaseYear?.let { LocalDate.parse(it) }
)

fun SeriesDetailsDto.toEntity() = Series(
    id = id,
    name = name.orEmpty(),
    genreIDs = genres.map { it.id },
    rating = voteAverage?.toFloat().orEmpty(),
    overview = overview.orEmpty(),
    posterUrl = posterUrl?.let {
        if (it.contains(BASE_IMAGE_URL))
            it
        else BASE_IMAGE_URL + it
    }.orEmpty(),
    backdropUrl = backdropUrl?.let {
        if (it.contains(BASE_IMAGE_URL))
            it
        else BASE_IMAGE_URL + it
    }.orEmpty(),
    seasons = seasons.map { it.toEntity() },
    releaseYear = releaseYear?.let { LocalDate.parse(it) },
    youtubeVideoId = youtubeVideoId.orEmpty(),
    popularity = popularity?.toFloat().orEmpty(),
    userRating = userRating,
    recentViewedAt = null
)

fun SeriesDetailsDto.toSeasonEntity() = seasons.map {
    Season(
        id = it.id,
        posterUrl = it.posterUrl?.let { url ->
            if (url.contains(BASE_IMAGE_URL))
                url
            else BASE_IMAGE_URL + url
        }.orEmpty(),
        rating = it.voteAverage,
        releaseYear = it.releaseYear?.let { LocalDate.parse(it) },
        overview = it.overview,
        episodeCount = it.episodeCount,
        seasonNumber = it.seasonNumber
    )
}
// endregion