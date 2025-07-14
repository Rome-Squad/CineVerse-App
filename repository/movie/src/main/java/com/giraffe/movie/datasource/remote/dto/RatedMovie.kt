package com.giraffe.movie.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RatedMoviesResponse(
    val page: Int,
    val results: List<RatedMovie>
)

@Serializable
data class RatedMovie(
    val id: Int,
    val title: String,
    val rating: Double
)
