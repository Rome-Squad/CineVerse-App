package com.giraffe.movie.datasource.local

import com.giraffe.movie.datasource.local.cacheDto.MovieDto
import com.giraffe.movie.datasource.local.cacheDto.MovieGenreDto

interface MoviesLocalDataSource {
    suspend fun getMovieById(
        movieId: Int
    ): MovieDto

    suspend fun insertMovies(movies: List<MovieDto>)

    suspend fun insertMovieGenres(movies: List<MovieGenreDto>)

    suspend fun getMoviesByName(movieName: String): List<MovieDto>

    suspend fun getMoviesByGenre(genreId: Int): List<MovieDto>

    suspend fun getMovieGenres(): List<MovieGenreDto>

    suspend fun getMovieGenreById(genreId: Int): MovieGenreDto

    suspend fun getMovieGenresById(ids : List<Int>) : List<MovieGenreDto>

    suspend fun clearCache()

}