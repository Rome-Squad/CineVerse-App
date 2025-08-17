package com.giraffe.presentation.home.utils

import com.giraffe.media.collections.entity.Collection
import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.series.entity.Series
import com.giraffe.presentation.home.model.MediaType
import com.giraffe.presentation.home.model.PopularMediaUi
import com.giraffe.presentation.home.model.Poster
import com.giraffe.presentation.home.model.PosterMedia
import com.giraffe.presentation.home.model.YourCollectionUi


fun Movie.toPoster() = Poster(
    id = id,
    title = name,
    posterUrl = posterUrl.orEmpty(),
    rating = rating,
    mediaType = MediaType.MOVIE
)


fun Series.toPoster() = Poster(
    id = id,
    title = name,
    posterUrl = posterUrl,
    rating = rating,
    mediaType = MediaType.SERIES
)


fun Series.toShowMorePoster(genres: List<String>) = PosterMedia(
    id = id,
    name = name,
    imageUri = posterUrl,
    rating = rating,
    date = releaseYear.orEmpty(),
    mediaType = MediaType.SERIES,
    genres = genres,
    recentViewedAt = recentViewedAt?.toLong()
)


fun Movie.toShowMorePoster(genres: List<String>) = PosterMedia(
    id = id,
    name = name,
    imageUri = posterUrl,
    rating = rating,
    date = releaseYear.orEmpty(),
    mediaType = MediaType.MOVIE,
    genres = genres,
    time = duration.toString(),
    recentViewedAt = recentViewedAt
)


fun Movie.toPopularMediaUi(genres: List<String>) = PopularMediaUi(
    id = id,
    title = name,
    posterUrl = posterUrl,
    backdropUrl = backdropUrl,
    genres = genres,
    rating = rating,
    mediaType = MediaType.MOVIE
)


fun Series.toPopularMediaUi(genres: List<String>) = PopularMediaUi(
    id = id,
    title = name,
    posterUrl = posterUrl,
    backdropUrl = backdropUrl,
    genres = genres,
    rating = rating,
    mediaType = MediaType.SERIES
)


fun Collection.toUi() = YourCollectionUi(
    id = id,
    title = name,
    numberOfItems = itemsCount
)