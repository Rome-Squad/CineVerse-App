package com.giraffe.media.series.mapper

import com.giraffe.media.series.datasource.remote.dto.ReviewDto
import com.giraffe.media.series.datasource.remote.dto.SeasonDto
import com.giraffe.media.series.datasource.remote.dto.SeriesDetailsDto
import com.giraffe.media.series.datasource.remote.dto.SeriesDto
import com.giraffe.media.series.entity.Season
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.entity.SeriesGenre
import com.giraffe.media.entity.Review
import com.giraffe.media.series.model.CachedSeasonDto
import com.giraffe.media.series.model.SeriesCacheDto
import com.giraffe.media.series.model.CachedSeriesGenreDto
import com.giraffe.media.series.model.GenreDto
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

fun SeriesCacheDto.toEntity(
    seasons: List<Season>,
): Series {
    return Series(
        id = id,
        name = name,
        overview = overview,
        rating = rate,
        posterUrl = posterUrl,
        genreIDs = genresID,
        releaseYear = releaseYear,
        seasons = seasons
    )
}

fun CachedSeasonDto.toEntity(): Season {
    return Season(
        id = id,
        name = name,
        overview = overview,
        rating = rate,
        posterUrl = posterUrl,
        seasonNumber = seasonNumber,
        releaseYear = releaseYear,
        episodeCount = numberOfEpisodes
    )
}

fun CachedSeriesGenreDto.toEntity(): SeriesGenre {
    return SeriesGenre(
        id = id,
        name = name
    )
}

fun Series.toCachedDto(): SeriesCacheDto {
    return SeriesCacheDto(
        id = id,
        name = name,
        overview = overview,
        rate = rating,
        posterUrl = posterUrl,
        genresID = genreIDs,
        releaseYear = releaseYear,
    )
}

fun Season.toCachedDto(seriesId: Int): CachedSeasonDto {
    return CachedSeasonDto(
        id = id,
        seriesId = seriesId,
        name = name,
        overview = overview,
        rate = rating,
        posterUrl = posterUrl,
        seasonNumber = seasonNumber,
        releaseYear = releaseYear,
        numberOfEpisodes = episodeCount
    )
}

fun SeriesGenre.toCachedDto(): CachedSeriesGenreDto {
    return CachedSeriesGenreDto(
        id = id,
        name = name
    )
}

fun SeriesDto.toCachedDto(): SeriesCacheDto {
    return SeriesCacheDto(
        id = id,
        name = name,
        overview = overview,
        rate = voteAverage.toFloat(),
        posterUrl = posterPath.orEmpty(),
        genresID = genreIds,
        releaseYear = firstAirDate,
    )
}

fun SeriesDto.toEntity(): Series {
    return Series(
        id = id,
        name = name,
        overview = overview,
        rating = voteAverage.toFloat(),
        posterUrl = posterPath.orEmpty(),
        genreIDs = genreIds,
        releaseYear = firstAirDate,
        seasons = emptyList()
    )
}

fun GenreDto.toEntity(): SeriesGenre {
    return SeriesGenre(
        id = id,
        name = name
    )
}

fun GenreDto.toCachedDto(): CachedSeriesGenreDto {
    return CachedSeriesGenreDto(
        id = id,
        name = name
    )
}

fun SeasonDto.toEntity(): Season {
    return Season(
        id = id,
        name = name,
        overview = overview,
        rating = voteAverage,
        posterUrl = posterPath.toString(),
        seasonNumber = seasonNumber,
        releaseYear = airDate?.toString() ?: "",
        episodeCount = episodeCount
    )
}

fun SeriesDetailsDto.toSeriesEntity(): Series {
    return Series(
        id = id,
        posterUrl = posterPath,
        name = name,
        genreIDs = genres.map { it.id },
        rating = voteAverage.toFloat(),
        releaseYear = firstAirDate,
        overview = overview,
        seasons = seasons.map { it.toEntity() }
    )
}

fun List<ReviewDto>.toSeriesReviewsEntity(): List<Review> {
    return map { item ->
        Review(
            id = item.id,
            authorImageUrl = item.authorDetails.avatarPath,
            authorName = item.authorDetails.name,
            authorUserName = item.authorDetails.username,
            content = item.content,
            rating = item.authorDetails.rating.toInt(),
            createdAt = parseData(item.createdAt)
        )
    }
}

@OptIn(ExperimentalTime::class)
fun parseData(dateString: String): LocalDateTime? {
    return try {
        val instant = Instant.parse(dateString)
        instant.toLocalDateTime(TimeZone.UTC)
    } catch (_: Exception) {
        null
    }
}




fun SeriesDetailsDto.toSeasonEntity(): List<Season> {
    return seasons.map {
        Season(
            id = it.id,
            posterUrl = it.posterPath.toString(),
            name = it.name,
            rating = it.voteAverage,
            releaseYear = it.airDate ?: "",
            overview = it.overview,
            episodeCount = it.episodeCount,
            seasonNumber = it.seasonNumber
        )
    }
}