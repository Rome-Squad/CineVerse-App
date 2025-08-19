package com.giraffe.presentation.home.model


data class Poster(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val rating: Float,
    val mediaType: MediaType,
    val recentViewedAt: Long,
)