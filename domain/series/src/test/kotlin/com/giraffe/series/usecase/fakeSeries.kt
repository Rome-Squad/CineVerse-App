package com.giraffe.series.usecase

import com.giraffe.series.entity.Series

fun fakeSeries(id: Int, name: String) = Series(
    id = id,
    name = name,
    overview = "",
    rating = 0f,
    posterUrl = "",
    genreIDs = emptyList(),
    releaseYear = "",
)