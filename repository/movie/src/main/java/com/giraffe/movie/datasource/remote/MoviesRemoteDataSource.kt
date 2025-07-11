package com.giraffe.movie.datasource.remote

import com.giraffe.movie.datasource.remote.dto.MovieDetailsDto
import com.giraffe.movie.datasource.remote.dto.MovieDto
import com.giraffe.movie.datasource.remote.dto.MovieGenreDto

interface MoviesRemoteDataSource {

    suspend fun getMovieById(
        movieId: Int
    ): MovieDetailsDto

    suspend fun getMoviesByName(movieName: String): List<MovieDto>

    suspend fun getMovieGenres(): List<MovieGenreDto>

    suspend fun getMoviesByGenre(genreId: Int): List<MovieDto>
}