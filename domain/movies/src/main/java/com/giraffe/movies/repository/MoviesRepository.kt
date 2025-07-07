package com.giraffe.movies.repository

import com.giraffe.movies.entity.Genre
import com.giraffe.movies.entity.Movie

interface MoviesRepository {
    suspend fun searchMovieByName(movieName: String): List<Movie>
    suspend fun getMovieGenres(): List<Genre>
    suspend fun searchMovieByNameAndGenre(movieName: String, genreId: Int)
}