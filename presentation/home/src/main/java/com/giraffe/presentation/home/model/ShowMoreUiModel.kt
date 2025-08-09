package com.giraffe.presentation.home.model

data class PosterUi(
    val id: Int,
    val name: String,
    val imageUri: String,
    val rating: Float,
    val genres: String? = null,
    val time: String? = null,
    val date: String? = null,
    val mediaType: MediaType
)