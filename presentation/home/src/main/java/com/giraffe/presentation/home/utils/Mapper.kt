package com.giraffe.presentation.home.utils

import com.giraffe.media.collections.entity.Collection
import com.giraffe.media.entity.Genre
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
    mediaType = MediaType.MOVIE,
    recentViewedAt = recentViewedAt ?: 0L
)


fun Series.toPoster() = Poster(
    id = id,
    title = name,
    posterUrl = posterUrl,
    rating = rating,
    mediaType = MediaType.SERIES,
    recentViewedAt = recentViewedAt ?: 0L
)


fun Series.toShowMorePoster(genres: List<String>) = PosterMedia(
    id = id,
    name = name,
    imageUri = posterUrl,
    rating = rating,
    date = releaseYear.formatDate(),
    mediaType = MediaType.SERIES,
    genres = genres,
    recentViewedAt = recentViewedAt
)


fun Movie.toShowMorePoster(genres: List<String>) = PosterMedia(
    id = id,
    name = name,
    imageUri = posterUrl,
    rating = rating,
    date = releaseYear.formatDate(),
    mediaType = MediaType.MOVIE,
    genres = genres,
    time = duration.formatDuration(),
    recentViewedAt = recentViewedAt
)


fun Movie.toPopularMediaUi(genres: List<Genre>) = PopularMediaUi(
    id = id,
    title = name,
    posterUrl = posterUrl,
    backdropUrl = backdropUrl,
    genres = genres
        .filter { genre -> genre.id in genresID }
        .map { genre -> genre.title },
    rating = rating,
    mediaType = MediaType.MOVIE
)


fun Series.toPopularMediaUi(genres: List<Genre>) = PopularMediaUi(
    id = id,
    title = name,
    posterUrl = posterUrl,
    backdropUrl = backdropUrl,
    genres = genres
        .filter { genre -> genre.id in genreIDs }
        .map { genre -> genre.title },
    rating = rating,
    mediaType = MediaType.SERIES
)


fun Collection.toUi() = YourCollectionUi(
    id = id,
    title = name,
    numberOfItems = itemsCount
)