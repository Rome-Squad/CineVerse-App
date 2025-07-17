package com.giraffe.media.series.usecase

import com.giraffe.media.series.entity.Season
import com.giraffe.media.series.entity.Series
import com.giraffe.media.entity.Review

fun fakeSeries(id: Int, name: String) = Series(
    id = id,
    name = name,
    overview = "",
    rating = 0f,
    posterUrl = "",
    genreIDs = emptyList(),
    releaseYear = "",
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

fun fakeSeriesReview(id: String, name: String, review: String) = Review(
    id = id,
    authorImageUrl = "",
    authorName = name,
    authorUserName = "",
    content = review,
    rating = 1,
    createdAt = null
)