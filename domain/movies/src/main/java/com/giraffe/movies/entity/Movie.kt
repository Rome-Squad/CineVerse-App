package com.giraffe.movies.entity


data class Movie (
    val id: Int,
    val title: String,
    val description: String,
    val rate: Float,
    val duration: String,
    val posterUrl: String,
    val actorsId: List<Int>,
    val genresID: List<Int>,
    val createdAt: LocalDate,
    val seasons: List<Season>
)

data class Season(
    val id: Int,
    val posterUrl: String,
    val seasonNumber: Int,
    val title: String,
    val description: String,
    val rate: Float,
    createdAt: LocalDate
)