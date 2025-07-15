package com.giraffe.movie.mapper

import com.giraffe.media.movies.entity.MovieGenre
import com.giraffe.movie.datasource.local.cacheDto.MovieGenreCacheDto
import com.giraffe.movie.datasource.remote.dto.MovieGenreDto

fun MovieGenreCacheDto.toEntity() = MovieGenre(id, name)

fun MovieGenre.toMovieGenreDto() = MovieGenreCacheDto(id, title)

fun MovieGenreDto.toEntity() = MovieGenre(
    id = id,
    title = name
)

fun MovieGenre.toDto() = MovieGenreDto(id, title)