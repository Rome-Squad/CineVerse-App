package com.giraffe.movies.repository

import com.giraffe.movies.entity.AccountStates
import com.giraffe.movies.entity.MovieGenre
import com.giraffe.movies.entity.Movie
import com.giraffe.movies.entity.MovieReview

interface MoviesRepository {

    suspend fun searchMovieByName(movieName: String): List<Movie>

    suspend fun getMovieGenres(genreIds : List<Int>): List<MovieGenre>

    suspend fun getMoviesGenres() : List<MovieGenre>

    suspend fun getMoviesByGenre(genreId: Int): List<Movie>

    suspend fun insertMovies(movie:List<Movie>)

    suspend fun insertGenres(genres:List<MovieGenre>)

    suspend fun setMovieRecent(
        movie: Movie,
        isRecent: Boolean
    )
    suspend fun clearCache()

    suspend fun getRecentlyMovies() : List<Movie>

    suspend fun clearRecentlyMovies()

    suspend fun getMovieDetails(movieId : Int) : Movie

    suspend fun getMovieReviews(movieId : Int) : List<MovieReview>

    suspend fun createGuestSession(): String

    suspend fun addRating(movieId: Int, ratingValue: Float)

    suspend fun getAccountStates(movieId: Int): AccountStates

}
