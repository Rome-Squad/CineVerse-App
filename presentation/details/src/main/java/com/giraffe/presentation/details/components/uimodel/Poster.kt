package com.giraffe.presentation.details.components.uimodel

data class Poster(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val rating: Float,
    val genres: String? = null,
    val time: String? = null,
    val date: String? = null,
    val mediaTypeOfPoster: String? = null
) {
    enum class Type(val value: String) {
        MOVIE("movie"),
        SERIES("series"),
        PERSON("person")
    }
}