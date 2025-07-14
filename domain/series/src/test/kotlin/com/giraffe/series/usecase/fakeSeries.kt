package com.giraffe.series.usecase

import com.giraffe.series.entity.Season
import com.giraffe.series.entity.Series
import com.giraffe.series.entity.SeriesDetails

fun fakeSeries(id: Int, name: String) = Series(
    id = id,
    name = name,
    overview = "",
    rating = 0f,
    posterUrl = "",
    genreIDs = emptyList(),
    releaseYear = "",
)

fun fakeSeriesDetails(id: Int, name: String) = SeriesDetails(
    id = id,
    name = name,
    overview = "",
    rating = 0f,
    posterUrl = "",
    genres = emptyList(),
    releaseYear = null,
)

fun fakeSeason(id: Int, name: String) = Season(
    id = id,
    name = name,
    overview = "",
    rating = 0f,
    posterUrl = "",
    seasonNumber = 1,
    releaseYear = "",
    episodeCount = 1
)