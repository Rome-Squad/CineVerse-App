package com.giraffe.movie.response

import com.giraffe.movie.datasource.remote.dto.MovieGenreDto


data class GenreResponse(
    val genres: List<MovieGenreDto>
)