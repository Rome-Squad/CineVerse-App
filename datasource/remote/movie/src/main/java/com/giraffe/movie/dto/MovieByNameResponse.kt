package com.giraffe.movie.dto

import com.giraffe.movies.entity.Movie
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName

data class MovieByNameResponse(
    val page: Int,
    val results: List<MovieDTO>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)

data class MovieDTO(
    val adult: Boolean,
    @SerialName("backdrop_path") val backdropPath: String,
    @SerialName("genre_ids") val genreIds: List<Int>,
    val id: Int,
    @SerialName("original_language") val originalLanguage: String,
    @SerialName("original_title") val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerialName("poster_path") val posterPath: String,
    @SerialName("release_date") val releaseDate: String,
    val title: String,
    val video: Boolean,
    @SerialName("vote_average") val voteAverage: Float,
    @SerialName("vote_count") val voteCount: Int
)

fun MovieDTO.toMovie(durationInMins: Int): Movie {
    return Movie(
        id = id,
        title = title,
        description = overview,
        rate = voteAverage,
        duration = getDurationInHours(durationInMins),
        posterUrl = posterPath,
        genresID = genreIds,
        releaseYear = LocalDate.parse(releaseDate)
    )
}

private fun getDurationInHours(duration: Int): String {
    return "${duration / 60} h ${duration % 60} mins"
}