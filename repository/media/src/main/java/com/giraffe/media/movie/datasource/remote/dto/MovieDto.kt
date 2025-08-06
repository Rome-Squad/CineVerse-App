package com.giraffe.media.movie.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    val id: Int,
    val title: String? = null,
    val overview: String? = null,
    @SerialName("genre_ids")
    val genresID: List<Int> = emptyList(),
    val genres: List<MovieGenreDto> = emptyList(),
    @SerialName("poster_path")
    val posterPath : String? = null,
    @SerialName("vote_average")
    val voteAverage : Float? = null,
    val userRating: Float? = null,
    @SerialName("release_date")
    val releaseDate : String? = null,
    @SerialName("backdrop_path")
    val backdropPath : String? = null,
    val runtime: Int? = null,
    var youtubeVideoId: String? = null
)