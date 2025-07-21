package com.giraffe.home

enum class MediaType {
    MOVIE,
    SERIES
}

enum class CollectionClickType {
    YOUR_COLLECTION,
    FEATURED
}

data class HomeUiModel(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val rating: Float,
    val mediaType: MediaType
)

data class YourCollectionUiModel(
    val id: Int,
    val title: String,
    val numberOfItems: Int
)

data class FeaturedCollectionUiModel(
    val id: Int,
    val title: String,
    val backgroundImageUrl: String
)
data class PopularMediaUiModel(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val genres: List<String>,
    val rating: Float ,
    val mediaType: MediaType
)