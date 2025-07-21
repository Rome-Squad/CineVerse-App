package com.giraffe.media.movie.datasource.remote.dto


data class RatedMoviesResponse(
    val page: Int,
    val results: List<RatedMovie>
)

data class RatedMovie(
    val id: Int,
    val title: String,
    val rating: Double
)
