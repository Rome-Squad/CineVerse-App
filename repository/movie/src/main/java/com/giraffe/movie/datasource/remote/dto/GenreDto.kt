package com.giraffe.movie.datasource.remote.dto

import com.giraffe.movies.entity.MovieGenre


data class GenreDTO(
    val id: Int,
    val name: String
)
fun GenreDTO.toMovieGenre(): MovieGenre {
    return MovieGenre(
        id = id,
        title = name
    )
}