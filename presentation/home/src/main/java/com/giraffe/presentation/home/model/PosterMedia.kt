package com.giraffe.presentation.home.model

data class PosterMedia(
    val id: Int,
    val name: String,
    val imageUri: String,
    val rating: Float,
    val genres: List<String>,
    val time: String? = null,
    val date: String? = null,
    val mediaType: MediaType,
    val recentViewedAt: Long? = null
)