package com.giraffe.media.movie.repository

import com.giraffe.media.entity.Genre
import com.giraffe.media.entity.Review
import com.giraffe.media.movie.entity.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun addRating(movieId: Int, ratingValue: Float)

    suspend fun searchMovieByName(movieName: String, page: Int): List<Movie>

    suspend fun getMovieGenresByIds(genreIds: List<Int>): List<Genre>

    suspend fun getMoviesGenres(): List<Genre>

    suspend fun getMoviesByGenre(genreId: Int, page: Int): List<Movie>

    fun getRecentlyViewedMovies(): Flow<List<Movie>>

    suspend fun getMovieDetails(movieId: Int): Movie

    suspend fun getRecommendedMovie(movieId: Int, page: Int, limit: Int): List<Movie>

    suspend fun getMovieReviews(
        movieId: Int,
        page: Int
    ): List<Review>

    suspend fun getUserMovieRating(movieId: Int): Float

    suspend fun getPopularityMovies(page: Int, limit: Int): List<Movie>

    suspend fun getRecentlyReleasedMovies(page: Int, limit: Int): List<Movie>

    suspend fun getUpcomingMovies(page: Int, limit: Int): List<Movie>

    suspend fun deleteMovieById(movieId: Int)

    suspend fun getRatedMovies(accountId: Int): List<Movie>

    suspend fun deleteMovieRating(movieId: Int)

    suspend fun clearMovieCache()

    suspend fun clearRecentlyViewedMovies()

    suspend fun clearMovieCacheWithOutRecentViewed()

    suspend fun clearMovieGenres()
}
