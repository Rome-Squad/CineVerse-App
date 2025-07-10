package com.giraffe.series.mapper

import com.giraffe.series.entity.*
import com.giraffe.series.model.*

fun CachedSeriesDto.toEntity(
    seasons: List<Season>,
): Series {
    return Series(
        id = id,
        name = name,
        overview = overview,
        rating = rate,
        duration = duration,
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

fun Series.toCachedDto(): CachedSeriesDto {
    return CachedSeriesDto(
        id = id,
        name = name,
        overview = overview,
        rate = rating,
        duration = duration,
        posterUrl = posterUrl,
        genresID = genreIDs,
        releaseYear = releaseYear
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