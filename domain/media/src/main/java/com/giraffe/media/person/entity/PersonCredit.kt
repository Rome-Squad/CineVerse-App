package com.giraffe.media.person.entity


data class PersonCredit(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val voteAverage: Double,
    val mediaType: String?,
    val genreIds: List<Int> = emptyList(),
    val releaseYear: String? = null,
)