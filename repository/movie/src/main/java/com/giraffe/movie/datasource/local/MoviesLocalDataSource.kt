package com.giraffe.movie.datasource.local

import com.giraffe.movies.entity.Movie
import com.giraffe.movies.entity.MovieGenre

interface MoviesLocalDataSource {
    suspend fun getMovieById(
        movieId: Int
    ): Movie

    suspend fun getMovieByName(movieName: String): List<Movie>

    suspend fun getMovieGenres(): List<MovieGenre>

    suspend fun getMoviesByGenre(genreId: Int): List<Movie>
}