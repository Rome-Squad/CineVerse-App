package com.giraffe.media.movies.repository

import com.giraffe.media.entity.Genre
import com.giraffe.media.entity.Review
import com.giraffe.media.movies.entity.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun searchMovieByName(movieName: String, page: Int): List<Movie>

    suspend fun getMovieGenres(genreIds: List<Int>): List<Genre>

    suspend fun getMoviesGenres(): List<Genre>

    suspend fun getMoviesByGenre(genreId: Int, page: Int): List<Movie>

    suspend fun addMovies(movie: List<Movie>)

    suspend fun addGenres(genres: List<Genre>)

    suspend fun setMovieRecent(
        movie: Movie,
        isRecent: Boolean
    )

    suspend fun clearCache()

    fun getRecentlyMovies(): Flow<List<Movie>>

    suspend fun clearRecentlyMovies()

    suspend fun getMovieDetails(movieId: Int): Movie

    suspend fun getRecommendedMovie(movieId: Int, page: Int): List<Movie>

    suspend fun getMovieReviews(movieId: Int): List<Review>

    suspend fun addRating(movieId: Int, ratingValue: Float)

    suspend fun getUserMovieRating(movieId: Int): Float

    suspend fun getPopularityMovies(page: Int): List<Movie>

    suspend fun getRecentlyReleasedMovies(page: Int): List<Movie>

    suspend fun getUpcomingMovies(page: Int): List<Movie>
    suspend fun deleteMovieById(movieId: Int)

    suspend fun getRatedMovies(accountId: Int): Map<Float, Movie>
    suspend fun deleteMovieRating(movieId: Int)
}
