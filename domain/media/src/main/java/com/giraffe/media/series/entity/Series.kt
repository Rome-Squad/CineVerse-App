package com.giraffe.media.series.entity


data class Series (
    val id: Int,
    val name: String,
    val overview: String,
    val rating: Float,
    val posterUrl: String,
    val genreIDs: List<Int>,
    val releaseYear: String,
    val seasons: List<Season> = emptyList(),
)