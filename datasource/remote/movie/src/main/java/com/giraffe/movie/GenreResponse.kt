package com.giraffe.movie

import com.giraffe.movies.entity.MovieGenre


data class GenreResponse(
    val genres: List<MovieGenreDTO>
)

data class MovieGenreDTO(
    val id: Int,
    val name: String
)
fun MovieGenreDTO.toMovieGenre(): MovieGenre {
    return MovieGenre(
        id = id,
        title = name
    )
}