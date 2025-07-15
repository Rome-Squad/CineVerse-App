package com.giraffe.media.movie.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsDto(
    val id: Int,
    val genres: List<MovieGenreDto>,
    val overview: String,
    @SerialName("poster_path") val posterPath: String,
    @SerialName("release_date") val releaseDate: String,
    val runtime: Int,
    val title: String,
    @SerialName("vote_average") val voteAverage: Float,
)