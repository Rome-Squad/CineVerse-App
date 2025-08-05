package com.giraffe.media.collections.mapper

import com.giraffe.media.collections.datasource.remote.dto.CollectionDto
import com.giraffe.media.collections.datasource.remote.dto.CollectionItemDto
import com.giraffe.media.collections.datasource.remote.dto.CollectionMediaTypeString
import com.giraffe.media.collections.entity.Collection
import com.giraffe.media.collections.entity.CollectionMediaType
import com.giraffe.media.movies.entity.Movie
import kotlinx.datetime.LocalDate

fun CollectionDto.toEntity() = Collection(
    id = id,
    name = name,
    description = description,
    itemsCount = itemsCount,
    type = type.toCollectionType()
)

fun Collection.toDto() = CollectionDto(
    id = id,
    name = name,
    description = description,
    type = type.name
)

fun CollectionItemDto.toMovie() = Movie(
    id = id,
    title = title.orEmpty(),
    description = description.orEmpty(),
    rating = rating?.toFloat() ?: 0f,
    duration = null,
    posterUrl = posterPath,
    backdropUrl = backdropPath,
    youtubeVideoId = null,
    genresID = genreIds,
    releaseYear = LocalDate.parse(releaseDate.orEmpty()),
    recentViewedAt = null,
    recentReleasedAt = null,
    upcomingAt = null,
    popularity = 0f
)

fun CollectionMediaTypeString.toCollectionType() = when (this) {
    "movie" -> CollectionMediaType.MOVIE
    "tv" -> CollectionMediaType.SERIES
    else -> CollectionMediaType.MOVIE
}