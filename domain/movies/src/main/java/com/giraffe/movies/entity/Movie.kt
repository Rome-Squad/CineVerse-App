package com.giraffe.movies.entity

import kotlinx.datetime.LocalDate


data class Movie (
    val id: Int,
    val title: String,
    val description: String,
    val rate: Float,
    val duration: String,
    val posterUrl: String,
    val genresID: List<Int>, // genre id
    val releaseYear: LocalDate
)