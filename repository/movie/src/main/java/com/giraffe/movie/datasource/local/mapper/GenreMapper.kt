package com.giraffe.movie.datasource.local.mapper

import com.giraffe.movie.datasource.local.cacheDto.MovieGenreCacheDto
import com.giraffe.movies.entity.MovieGenre

fun MovieGenreCacheDto.toMovieGenre() = MovieGenre(id, name)

fun MovieGenre.toMovieGenreDto() = MovieGenreCacheDto(id, title)