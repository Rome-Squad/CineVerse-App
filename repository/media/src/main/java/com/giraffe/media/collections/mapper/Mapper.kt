package com.giraffe.media.collections.mapper

import com.giraffe.media.collections.datasource.remote.dto.CollectionDto
import com.giraffe.media.collections.datasource.remote.dto.CollectionTypeString
import com.giraffe.media.collections.entity.Collection
import com.giraffe.media.collections.entity.CollectionType

fun CollectionDto.toEntity() = Collection(
    id = id,
    name = name,
    description = description,
    type = type.toCollectionType()
)

fun Collection.toDto() = CollectionDto(
    id = id,
    name = name,
    description = description,
    type = type.name
)

fun CollectionTypeString.toCollectionType() = when (this) {
    "movie" -> CollectionType.MOVIE
    "tv" -> CollectionType.SERIES
    else -> CollectionType.MOVIE
}