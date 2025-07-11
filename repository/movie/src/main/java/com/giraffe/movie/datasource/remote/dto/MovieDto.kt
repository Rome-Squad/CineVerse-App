package com.giraffe.movie.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    val id: Int,
    val title: String,
    val overview: String,

    @SerialName("poster_path")
    val posterPath : String,

    @SerialName("vote_average")
    val voteAverage : Float,

    @SerialName("genre_ids")
    val genresID: List<Int>,

    @SerialName("release_date")
    val releaseDate : String
)
