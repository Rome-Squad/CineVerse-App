package com.giraffe.movie.datasource.remote

import com.giraffe.movie.datasource.remote.dto.GenreDTO
import com.giraffe.movie.datasource.remote.dto.MovieDto

interface MoviesRemoteDataSource {

    suspend fun getMovieById(
        movieId: Int
    ): MovieDto

    suspend fun getMovieByName(movieName: String): List<MovieDto>

    suspend fun getMovieGenres(): List<GenreDTO>

    suspend fun getMoviesByGenre(genreId: Int): List<MovieDto>
}