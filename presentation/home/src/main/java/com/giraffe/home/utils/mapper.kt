package com.giraffe.home.utils

import com.giraffe.home.*
import com.giraffe.media.home.entity.FeaturedCollection
import com.giraffe.media.home.entity.YourCollection
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.series.entity.Series


fun Movie.toHomeUiModel(): HomeUiModel {
    return HomeUiModel(
        id = id,
        title = title,
        posterUrl = posterUrl ?: "",
        rating = rating,
        mediaType = MediaType.MOVIE
    )
}

fun Series.toHomeUiModel(): HomeUiModel {
    return HomeUiModel(
        id = id,
        title = name,
        posterUrl = posterUrl,
        rating = rating,
        mediaType = MediaType.SERIES
    )
}


fun Movie.toPopularMediaUiModel(genres: List<String>): PopularMediaUiModel {
    return PopularMediaUiModel(
        id = id,
        title = title,
        posterUrl = posterUrl ?: "",
        genres = genres,
        rating = rating,
        mediaType = MediaType.MOVIE
    )
}

fun Series.toPopularMediaUiModel(genres: List<String>): PopularMediaUiModel {
    return PopularMediaUiModel(
        id = id,
        title = name,
        posterUrl = posterUrl,
        genres = genres,
        rating = rating,
        mediaType = MediaType.SERIES
    )
}


fun YourCollection.toUiModel(): YourCollectionUiModel {
    return YourCollectionUiModel(
        id = id,
        title = title,
        numberOfItems = numberOfShows.toIntOrNull() ?: 0
    )
}

fun FeaturedCollection.toUiModel(): FeaturedCollectionUiModel {
    return FeaturedCollectionUiModel(
        id = id,
        title = title,
        backgroundImageUrl = imageUrl
    )
}