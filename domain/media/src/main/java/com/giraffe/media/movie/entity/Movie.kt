package com.giraffe.media.movie.entity

import kotlinx.datetime.LocalDate


data class Movie(
    val id: Int,
    val name: String,
    val overview: String,
    val rating: Float,
    val posterUrl: String,
    val backdropUrl: String,
    val genresID: List<Int>,
    val releaseYear: LocalDate?,
    val duration: Int?,
    val youtubeVideoId: String,
    val popularity: Float,
    val userRating: Float?,
    val recentViewedAt: Long?,
)