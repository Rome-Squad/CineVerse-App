package com.giraffe.media.series.entity


data class Series(
    val id: Int,
    val name: String,
    val overview: String,
    val rating: Float,
    val posterUrl: String,
    val backdropUrl: String,
    val genreIDs: List<Int> = emptyList(),
    val releaseYear: String,
    val seasons: List<Season> = emptyList(),
    val youtubeVideoId: String = "",
    val popularity: Double = 0.0
)