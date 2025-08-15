package com.giraffe.media.collections.mapper

import com.giraffe.media.collections.datasource.remote.dto.CollectionDto
import com.giraffe.media.collections.datasource.remote.dto.CollectionItemDto
import com.giraffe.media.collections.datasource.remote.dto.CollectionMediaTypeString
import com.giraffe.media.collections.entity.Collection
import com.giraffe.media.collections.entity.CollectionMediaType
import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.utils.BASE_IMAGE_URL
import com.giraffe.media.utils.orZero
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
    name = title.orEmpty(),
    overview = description.orEmpty(),
    rating = rating.orZero(),
    duration = null,
    posterUrl = posterPath?.let {
        if (it.contains(BASE_IMAGE_URL))
            it
        else BASE_IMAGE_URL + it
    }.orEmpty(),
    backdropUrl = backdropPath?.let {
        if (it.contains(BASE_IMAGE_URL))
            it
        else BASE_IMAGE_URL + it
    }.orEmpty(),
    youtubeVideoId = "",
    genresID = genreIds,
    releaseYear = releaseDate?.let { LocalDate.parse(it) },
    recentViewedAt = null,
    popularity = 0f,
    userRating = null
)

fun CollectionMediaTypeString.toCollectionType() = when (this) {
    "movie" -> CollectionMediaType.MOVIE
    "tv" -> CollectionMediaType.SERIES
    else -> CollectionMediaType.MOVIE
}