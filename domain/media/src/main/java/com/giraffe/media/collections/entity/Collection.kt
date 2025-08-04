package com.giraffe.media.collections.entity

data class Collection(
    val id: Int,
    val name: String,
    val description: String,
    val itemsCount: Int,
    val type: CollectionMediaType = CollectionMediaType.MOVIE

)

enum class CollectionMediaType(
    val collectionTypeName: String
) {
    MOVIE("movie"),
    SERIES("tv")
}
