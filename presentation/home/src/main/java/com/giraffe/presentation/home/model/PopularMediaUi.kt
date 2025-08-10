package com.giraffe.presentation.home.model

import androidx.compose.runtime.Stable

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