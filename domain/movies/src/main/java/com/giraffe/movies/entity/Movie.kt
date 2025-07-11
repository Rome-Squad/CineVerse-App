package com.giraffe.movies.entity

import kotlinx.datetime.LocalDate


data class Movie (
    val id: Int,
    val title: String,
    val description: String,
    val rating: Float,
    val duration: Int?,
    val posterUrl: String?,
    val genresID: List<Int>,
    val releaseYear: LocalDate?
)