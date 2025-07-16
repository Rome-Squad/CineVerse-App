package com.giraffe.media.movie.mapper

import com.giraffe.media.movies.entity.MovieGenre
import  com.giraffe.media.movie.model.cache.MovieGenreCacheDto
import  com.giraffe.media.movie.model.dto.MovieGenreDto

fun MovieGenreCacheDto.toEntity() = MovieGenre(id, name)

fun MovieGenre.toMovieGenreDto() = MovieGenreCacheDto(id, title)

fun MovieGenreDto.toEntity() = MovieGenre(
    id = id,
    title = name
)

fun MovieGenre.toDto() = MovieGenreDto(id, title)