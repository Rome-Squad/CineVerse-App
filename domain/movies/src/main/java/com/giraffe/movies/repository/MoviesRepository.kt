package com.giraffe.movies.repository

import com.giraffe.movies.entity.MovieGenre
import com.giraffe.movies.entity.Movie

interface MoviesRepository {

    suspend fun searchMovieByName(movieName: String): List<Movie>

    suspend fun getMovieGenres(): List<MovieGenre>

    suspend fun getMoviesByGenre(genreId: Int): List<Movie>

    suspend fun insertMovies(movie:List<Movie>)

    suspend fun insertGenres(genres:List<MovieGenre>)

    suspend fun setMovieRecent(
        movie: Movie,
        isRecent: Boolean
    )
    suspend fun clearCache()
}
