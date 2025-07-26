package com.giraffe.designsystem.uimodel

data class Poster(
    val id: Int,
    val name: String,
    val imageUri: String,
    val rating: Float,
    val genres: String? = null,
    val time: String? = null,
    val date: String? = null,
    val mediaTypeOfPoster: String? = null
)