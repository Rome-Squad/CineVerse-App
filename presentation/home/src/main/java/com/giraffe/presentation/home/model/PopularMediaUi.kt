package com.giraffe.presentation.home.model


data class PopularMediaUi(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val backdropUrl: String = "",
    val genres: List<String>,
    val rating: Float,
    val mediaType: MediaType
)