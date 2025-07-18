package com.giraffe.media.movie.mapper

import com.giraffe.media.entity.Genre
import  com.giraffe.media.movie.model.cacheDto.MovieGenreCacheDto
import  com.giraffe.media.movie.model.dto.MovieGenreDto

fun MovieGenreCacheDto.toEntity() = Genre(id, name,count)

fun Genre.toDto() = MovieGenreCacheDto(id, title,rank)

fun MovieGenreDto.toEntity() = Genre(
    id = id,
    title = name,
    rank = 0
)