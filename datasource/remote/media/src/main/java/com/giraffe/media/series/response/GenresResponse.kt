package com.giraffe.media.series.response

import com.giraffe.media.series.datasource.remote.dto.GenreDto

data class GenresResponse(
    val genres: List<GenreDto>
)