package com.giraffe.presentation.details.model

data class MovieUi(
    val id: Int = 0,
    val name: String = "",
    val overview: String = "",
    val rating: Float = 0.0f,
    val duration: String = "",
    val genresID: List<Int> = emptyList(),
    val genres: List<String> = emptyList(),
    val posterUrl: String? = null,
    val backdropUrl: String? = null,
    val releaseYear: String = "",
    val youtubeVideoId: String = ""
)

