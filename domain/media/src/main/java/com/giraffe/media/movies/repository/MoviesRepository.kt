package com.giraffe.media.movies.repository

import com.giraffe.media.entity.Review
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.entity.Genre

interface MoviesRepository {

    suspend fun searchMovieByName(movieName: String): List<Movie>

    suspend fun getMovieGenres(genreIds: List<Int>): List<Genre>

    suspend fun getMoviesGenres(): List<Genre>

    suspend fun getMoviesByGenre(genreId: Int): List<Movie>

    suspend fun insertMovies(movie: List<Movie>)

    suspend fun insertGenres(genres: List<Genre>)

    suspend fun setMovieRecent(
        movie: Movie,
        isRecent: Boolean
    )

    suspend fun clearCache()

    suspend fun getRecentlyMovies(): List<Movie>

    suspend fun clearRecentlyMovies()

    suspend fun getMovieDetails(movieId: Int): Movie

    suspend fun getRecommendedMovie(movieId: Int, page: Int): List<Movie>

    suspend fun getMovieReviews(movieId: Int): List<Review>

    suspend fun addRating(movieId: Int, ratingValue: Float)

    suspend fun getUserMovieRating(movieId: Int): Float


}
