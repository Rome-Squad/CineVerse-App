package com.giraffe.media.movie.repository

import com.giraffe.media.entity.Genre
import com.giraffe.media.entity.Review
import com.giraffe.media.movie.entity.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun addRating(movieId: Int, rating: Float)

    suspend fun getByName(name: String, page: Int): List<Movie>

    suspend fun getGenresByIds(genreIds: List<Int>): List<Genre>

    suspend fun getGenres(): List<Genre>

    suspend fun getTopGenre(): Genre?

    suspend fun getByGenreId(genreId: Int, page: Int): List<Movie>

    suspend fun getMoviesCollection(
        genreId: Int? = null,
        keywords: String? = null,
        minVote: Float? = null,
        sortBy: String = "popularity.desc",
        page: Int
    ): List<Movie>

    fun getRecentlyViewed(): Flow<List<Movie>>

    suspend fun getDetails(movieId: Int): Movie

    suspend fun getRecommended(movieId: Int, page: Int, limit: Int): List<Movie>

    suspend fun getReviews(movieId: Int, page: Int): List<Review>

    suspend fun getUserRatedById(movieId: Int): Float?

    suspend fun getUserRated(accountId: Int): List<Movie>

    suspend fun getPopular(page: Int, limit: Int): List<Movie>

    suspend fun getRecentlyReleased(page: Int, limit: Int): List<Movie>

    suspend fun getUpcoming(page: Int, limit: Int): List<Movie>

    suspend fun deleteById(movieId: Int)

    suspend fun deleteRating(movieId: Int)

    suspend fun clearAll()

    suspend fun clearRecentlyViewed()

    suspend fun clearExceptRecentlyViewed()

    suspend fun clearGenres()
}
