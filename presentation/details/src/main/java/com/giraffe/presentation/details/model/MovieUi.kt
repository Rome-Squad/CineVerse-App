package com.giraffe.presentation.details.model

data class MovieUi(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val rating: Float = 0.0f,
    val duration: String? = null,
    val genresID: List<Int> = emptyList(),
    val genres: List<String> = emptyList(),
    val posterUrl: String? = null,
    val backdropUrl: String? = null,
    val releaseYear: String? = null,
    val youtubeVideoId: String = ""
)

