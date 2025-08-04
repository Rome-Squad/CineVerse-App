package com.giraffe.media.movies.entity

import kotlinx.datetime.LocalDate


data class Movie(
    val id: Int,
    val title: String,
    val description: String,
    val rating: Float,
    val duration: Int?,
    val posterUrl: String?,
    val backdropUrl: String?,
    val genresID: List<Int>,
    val releaseYear: LocalDate?,
    val youtubeVideoId: String?,
    val recentViewedAt: Long?,
    val recentReleasedAt: Long?,
    val upcomingAt: Long?,
    val popularity: Float
)