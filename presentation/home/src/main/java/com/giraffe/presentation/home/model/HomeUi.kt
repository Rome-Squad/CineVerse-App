package com.giraffe.presentation.home.model

import androidx.compose.runtime.Stable

enum class MediaType {
    MOVIE,
    SERIES
}

@Stable
data class HomeUi(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val rating: Float,
    val mediaType: MediaType
)

data class YourCollectionUi(
    val id: Int,
    val title: String,
    val numberOfItems: Int
)

data class FeaturedCollectionUi(
    val id: Int,
    val title: String,
    val backgroundImageUrl: String
)

@Stable
data class PopularMediaUi(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val backdropUrl: String = "",
    val genres: List<String>,
    val rating: Float,
    val mediaType: MediaType
)