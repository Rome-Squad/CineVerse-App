package com.giraffe.series.mapper

import com.giraffe.series.datasource.remote.response.seriesdetails.SeriesDetailsResponse
import com.giraffe.series.entity.Season
import com.giraffe.series.entity.Series
import com.giraffe.series.entity.SeriesDetails
import com.giraffe.series.entity.SeriesGenre
import com.giraffe.series.model.CachedSeasonDto
import com.giraffe.series.model.CachedSeriesDto
import com.giraffe.series.model.CachedSeriesGenreDto
import com.giraffe.series.model.GenreDto
import com.giraffe.series.model.SeasonDto
import com.giraffe.series.model.SeriesDto
import kotlinx.datetime.LocalDate

fun CachedSeriesDto.toEntity(
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
        posterUrl = posterPath,
        seasonNumber = seasonNumber,
        releaseYear = airDate,
        episodeCount = episodeCount
    )
}

fun SeriesDetailsResponse.toEntity(): SeriesDetails {
    return SeriesDetails(
        id = id,
        posterUrl = posterPath,
        name = name,
        genreIDs = genres.map { it.toEntity() },
        rating = voteAverage.toFloat(),
        releaseYear = firstAirDate.let { LocalDate.parse(it) },
        overview = overview,
        seasons = seasons.map { it.toEntity() },
    )
}

//fun ReviewItemDto.toEntity(): Review {
//    return Review(
//        id = id.toInt(),
//        userImageUrl = im,
//        name = TODO(),
//        userName = TODO(),
//        review = TODO(),
//        rating = TODO(),
//        releaseYear = TODO()
//    )
//}


