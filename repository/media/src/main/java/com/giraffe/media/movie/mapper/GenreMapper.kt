package com.giraffe.media.movie.mapper

import com.giraffe.media.movies.entity.MovieGenre
import  com.giraffe.media.movie.model.cacheDto.MovieGenreCacheDto
import  com.giraffe.media.movie.model.dto.MovieGenreDto

fun MovieGenreCacheDto.toEntity() = MovieGenre(id, name,count)

fun MovieGenre.toMovieGenreDto() = MovieGenreCacheDto(id, title,rank)

fun MovieGenreDto.toEntity() = MovieGenre(
    id = id,
    title = name,
    rank = 0
)