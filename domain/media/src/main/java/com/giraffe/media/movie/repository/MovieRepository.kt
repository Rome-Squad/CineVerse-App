package com.giraffe.media.movie.repository

import com.giraffe.media.entity.Genre
import com.giraffe.media.entity.Review
import com.giraffe.media.movie.entity.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun addRating(movieId: Int, rating: Float)

    suspend fun getByName(name: String, page: Int): List<Movie>

    fun getLocalGenres(): Flow<List<Genre>>

    suspend fun getRemoteGenres(): List<Genre>

    fun getGenresByIds(genreIds: List<Int>): Flow<List<Genre>>

    suspend fun getTopGenre(): Genre?

    suspend fun clearGenres()

    suspend fun getByGenreId(genreId: Int, page: Int): List<Movie>

    suspend fun getByGenreIds(genreIds: List<Int>, page: Int): List<Movie>

    suspend fun getByKeywordsId(keywords: Int, page: Int): List<Movie>

    suspend fun getBySort(sortBy: String, page: Int): List<Movie>

    suspend fun getDetails(movieId: Int): Movie

    suspend fun getRecommended(movieId: Int, page: Int): List<Movie>

    suspend fun getReviews(movieId: Int, page: Int): List<Review>

    suspend fun getUserRatedById(movieId: Int): Float?

    suspend fun getUserRated(accountId: Int): List<Movie>

    fun getLocalPopular(limit: Int): Flow<List<Movie>>

    fun getLocalRecentlyReleased(limit: Int): Flow<List<Movie>>

    suspend fun getRemoteRecentlyReleased(page: Int, limit: Int): List<Movie>

    fun getLocalUpcoming(limit: Int): Flow<List<Movie>>

    suspend fun getRemoteUpcoming(page: Int, limit: Int): List<Movie>

    fun getLocalMatchesYourVibe(limit: Int): Flow<List<Movie>>

    suspend fun getRemoteMatchesYourVibe(page: Int, limit: Int): List<Movie>

    fun getRecentlyViewed(page: Int, pageSize: Int): Flow<List<Movie>>

    suspend fun syncRecentlyViewedMovies()

    suspend fun clearRecentlyViewed()

    suspend fun deleteRecentlyViewedMovieById(movieId: Int)

    suspend fun deleteRating(movieId: Int)

    suspend fun clearAll()

    suspend fun clearExceptRecentlyViewed()
}
