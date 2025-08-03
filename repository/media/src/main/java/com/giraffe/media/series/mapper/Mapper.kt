package com.giraffe.media.series.mapper

import com.giraffe.media.entity.Genre
import com.giraffe.media.series.datasource.local.cacheDto.SeasonCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.SeriesCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.SeriesGenreCacheDto
import com.giraffe.media.series.datasource.remote.dto.GenreDto
import com.giraffe.media.series.datasource.remote.dto.SeasonDto
import com.giraffe.media.series.datasource.remote.dto.SeriesDetailsDto
import com.giraffe.media.series.datasource.remote.dto.SeriesDto
import com.giraffe.media.series.entity.Season
import com.giraffe.media.series.entity.Series
import com.giraffe.media.utils.BASE_IMAGE_URL
import com.giraffe.media.utils.toFormattedDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

fun SeriesCacheDto.toEntity(
    seasons: List<Season> = emptyList(),
) = Series(
    id = id,
    name = name,
    overview = overview,
    rating = rate,
    posterUrl = posterUrl.let {
        if (it.contains(BASE_IMAGE_URL))
            it
        else BASE_IMAGE_URL + it
    },
    backdropUrl = backdropUrl.let {
        if (it.contains(BASE_IMAGE_URL))
            it
        else BASE_IMAGE_URL + it
    },
    genreIDs = genresID,
    releaseYear = releaseYear,
    seasons = seasons
)

fun Series.toCacheDto() = SeriesCacheDto(
    id = id,
    name = name,
    overview = overview,
    rate = rating,
    posterUrl = posterUrl.let {
        if (it.contains(BASE_IMAGE_URL))
            it
        else BASE_IMAGE_URL + it
    },
    backdropUrl = backdropUrl.let {
        if (it.contains(BASE_IMAGE_URL))
            it
        else BASE_IMAGE_URL + it
    },
    genresID = genreIDs,
    releaseYear = releaseYear,
)


fun SeasonCacheDto.toEntity() = Season(
    id = id,
    overview = overview,
    rating = rate,
    posterUrl = posterUrl.let {
        if (it.contains(BASE_IMAGE_URL))
            it
        else BASE_IMAGE_URL + it
    },
    seasonNumber = seasonNumber,
    releaseYear = releaseYear,
    episodeCount = numberOfEpisodes
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

fun SeriesDto.toEntity() = Series(
    id = id,
    name = name ?: "",
    overview = overview ?: "",
    rating = voteAverage?.toFloat() ?: 0f,
    posterUrl = posterPath?.let {
        if (it.contains(BASE_IMAGE_URL))
            it
        else BASE_IMAGE_URL + it
    }.orEmpty(),
    backdropUrl = backdropPath?.let {
        if (it.contains(BASE_IMAGE_URL))
            it
        else BASE_IMAGE_URL + it
    }.orEmpty(),
    genreIDs = genreIds,
    releaseYear = firstAirDate?.toFormattedDate() ?: "",
    seasons = emptyList()
)

fun GenreDto.toEntity() = Genre(
    id = id,
    title = name,
    rank = 0
)

fun SeasonDto.toEntity() = Season(
    id = id,
    overview = overview,
    rating = voteAverage,
    posterUrl = posterPath?.let {
        if (it.contains(BASE_IMAGE_URL))
            it
        else BASE_IMAGE_URL + it
    }.orEmpty(),
    seasonNumber = seasonNumber,
    releaseYear = airDate.orEmpty(),
    episodeCount = episodeCount
)

fun SeriesDetailsDto.toEntity() = Series(
    id = id,
    posterUrl = posterPath?.let {
        if (it.contains(BASE_IMAGE_URL))
            it
        else BASE_IMAGE_URL + it
    }.orEmpty(),
    backdropUrl = backdropPath?.let {
        if (it.contains(BASE_IMAGE_URL))
            it
        else BASE_IMAGE_URL + it
    }.orEmpty(),
    name = name ?: "",
    genreIDs = genres.map { it.id },
    rating = voteAverage?.toFloat() ?: 0f,
    releaseYear = firstAirDate?.toFormattedDate() ?: "",
    overview = overview ?: "",
    seasons = seasons.map { it.toEntity() },
    youtubeVideoId = youtubeVideoId.orEmpty()
)

@OptIn(ExperimentalTime::class)
fun parseData(dateString: String): LocalDateTime? {
    return try {
        val instant = Instant.parse(dateString)
        instant.toLocalDateTime(TimeZone.UTC)
    } catch (_: Exception) {
        null
    }
}


fun SeriesDetailsDto.toSeasonEntity() = seasons.map {
    Season(
        id = it.id,
        posterUrl = it.posterPath?.let { url ->
            if (url.contains(BASE_IMAGE_URL))
                url
            else BASE_IMAGE_URL + url
        }.orEmpty(),
        rating = it.voteAverage,
        releaseYear = it.airDate.orEmpty(),
        overview = it.overview,
        episodeCount = it.episodeCount,
        seasonNumber = it.seasonNumber
    )
}