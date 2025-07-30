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
    posterUrl = posterUrl,
    genreIDs = genresID,
    releaseYear = releaseYear,
    seasons = seasons
)

fun Series.toCacheDto() = SeriesCacheDto(
    id = id,
    name = name,
    overview = overview,
    rate = rating,
    posterUrl = posterUrl,
    genresID = genreIDs,
    releaseYear = releaseYear,
)


fun SeasonCacheDto.toEntity() = Season(
    id = id,
    overview = overview,
    rating = rate,
    posterUrl = posterUrl,
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
    name = name,
    overview = overview,
    rating = voteAverage.toFloat(),
    posterUrl = BASE_IMAGE_URL + posterPath,
    genreIDs = genreIds,
    releaseYear = firstAirDate.toFormattedDate(),
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
    posterUrl = BASE_IMAGE_URL + posterPath,
    seasonNumber = seasonNumber,
    releaseYear = airDate ?: "",
    episodeCount = episodeCount
)

fun SeriesDetailsDto.toEntity() = Series(
    id = id,
    posterUrl = BASE_IMAGE_URL + posterPath,
    name = name,
    genreIDs = genres.map { it.id },
    rating = voteAverage.toFloat(),
    releaseYear = firstAirDate.toFormattedDate(),
    overview = overview,
    seasons = seasons.map { it.toEntity() }
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
        posterUrl = BASE_IMAGE_URL + it.posterPath,
        rating = it.voteAverage,
        releaseYear = it.airDate ?: "",
        overview = it.overview,
        episodeCount = it.episodeCount,
        seasonNumber = it.seasonNumber
    )
}