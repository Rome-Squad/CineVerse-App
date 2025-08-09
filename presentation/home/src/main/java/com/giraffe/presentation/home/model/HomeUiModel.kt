package com.giraffe.presentation.home.model

import androidx.compose.runtime.Stable

enum class MediaType {
    MOVIE,
    SERIES
}

@Stable
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

@Stable
data class PopularMediaUiModel(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val backdropUrl: String = "",
    val genres: List<String>,
    val rating: Float,
    val mediaType: MediaType
)