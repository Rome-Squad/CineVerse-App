package com.giraffe.designsystem.uimodel

data class PosterMovie(
    val title: String,
    val imageUri: String,
    val rating: Float,
    val genres: String? = null,
    val time: String? = null,
    val date: String? = null
)