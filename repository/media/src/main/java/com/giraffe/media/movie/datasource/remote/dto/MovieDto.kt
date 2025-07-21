package com.giraffe.media.movie.datasource.remote.dto


import com.google.gson.annotations.SerializedName

data class MovieDto(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("genre_ids")
    val genresID: List<Int> = emptyList(),
    val genres: List<MovieGenreDto> = emptyList(),
    @SerializedName("poster_path")
    val posterPath : String? = null,
    @SerializedName("vote_average")
    val voteAverage : Float = 0f,
    @SerializedName("release_date")
    val releaseDate : String? = null,
    val runtime: Int? = null,
)