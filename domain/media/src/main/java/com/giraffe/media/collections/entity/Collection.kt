package com.giraffe.media.collections.entity

data class Collection(
    val id: Int,
    val name: String,
    val description: String,
    val type: CollectionType = CollectionType.MOVIE

)

enum class CollectionType(
    val collectionTypeName: String
) {
    MOVIE("movie"),
    SERIES("tv")
}
