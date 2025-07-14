package com.giraffe.series.mapper

import com.giraffe.series.datasource.remote.response.seriesdetails.SeriesDetailsResponse
import com.giraffe.series.datasource.remote.response.seriesdetails.reviews.SeriesReviewsResponse
import com.giraffe.series.entity.Review
import com.giraffe.series.entity.Season
import com.giraffe.series.entity.Series
import com.giraffe.series.entity.SeriesGenre
import com.giraffe.series.model.CachedSeasonDto
import com.giraffe.series.model.CachedSeriesDto
import com.giraffe.series.model.CachedSeriesGenreDto
import com.giraffe.series.model.GenreDto
import com.giraffe.series.model.SeasonDto
import com.giraffe.series.model.SeriesDto
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

fun CachedSeriesDto.toSeriesEntity(
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

fun CachedSeasonDto.toSeriesEntity(): Season {
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

fun CachedSeriesGenreDto.toSeriesEntity(): SeriesGenre {
    return SeriesGenre(
        id = id,
        name = name
    )
}

fun Series.toCachedDto(): CachedSeriesDto {
    return CachedSeriesDto(
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

fun SeriesDto.toCachedDto(): CachedSeriesDto {
    return CachedSeriesDto(
        id = id,
        name = name,
        overview = overview,
        rate = voteAverage.toFloat(),
        posterUrl = posterPath.orEmpty(),
        genresID = genreIds,
        releaseYear = firstAirDate,
    )
}

fun SeriesDto.toSeriesEntity(): Series {
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

fun GenreDto.toSeriesEntity(): SeriesGenre {
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

fun SeasonDto.toSeriesEntity(): Season {
    return Season(
        id = id,
        name = name,
        overview = overview,
        rating = voteAverage,
        posterUrl = posterPath,
        seasonNumber = seasonNumber,
        releaseYear = airDate,
        episodeCount = episodeCount
    )
}

fun SeriesDetailsResponse.toSeriesEntity(): Series {
    return Series(
        id = id,
        posterUrl = posterPath,
        name = name,
        genreIDs = genres.map { it.id },
        rating = voteAverage.toFloat(),
        releaseYear = firstAirDate,
        overview = overview,
        seasons = seasons.map { it.toSeriesEntity() }
    )
}

fun SeriesReviewsResponse.toSeriesReviewsEntity(): List<Review> {
    return results.map { item ->
        Review(
            id = item.id,
            userImageUrl = item.authorDetails.avatarPath,
            name = item.authorDetails.name,
            userName = item.authorDetails.username,
            review = item.content,
            rating = item.authorDetails.rating,
            releaseYear = parseData(item.createdAt)
        )
    }
}

@OptIn(ExperimentalTime::class)
fun parseData(dateString: String): LocalDate? {
    return try {
        val instant = Instant.parse(dateString)
        instant.toLocalDateTime(TimeZone.UTC).date
    } catch (e: Exception) {
        null
    }
}


fun SeriesDetailsResponse.toSeasonEntity(): List<Season> {
    return seasons.map {
        Season(
            id = it.id,
            posterUrl = it.posterPath,
            name = it.name,
            rating = it.voteAverage,
            releaseYear = it.airDate,
            overview = it.overview,
            episodeCount = it.episodeCount,
            seasonNumber = it.seasonNumber
        )
    }
}