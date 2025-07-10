package com.giraffe.movie.response

import com.giraffe.movie.datasource.remote.dto.GenreDTO


data class GenreResponse(
    val genres: List<GenreDTO>
)