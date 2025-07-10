package com.giraffe.series.entity


data class Series (
    val id: Int,
    val name: String,
    val overview: String,
    val rating: Float,
    val duration: String,
    val posterUrl: String,
    val genreIDs: List<Int>,
    val releaseYear: Int,
    val seasons: List<Season> = emptyList(),
)