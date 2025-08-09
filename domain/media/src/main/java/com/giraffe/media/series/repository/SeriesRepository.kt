package com.giraffe.media.series.repository

import com.giraffe.media.entity.Genre
import com.giraffe.media.entity.Review
import com.giraffe.media.series.entity.Season
import com.giraffe.media.series.entity.Series
import kotlinx.coroutines.flow.Flow

interface SeriesRepository {

    suspend fun getByName(name: String, page: Int): List<Series>

    suspend fun getByGenre(genreId: Int, page: Int): List<Series>

    suspend fun getGenres(): List<Genre>

    suspend fun getGenresByIds(genreIDs: List<Int>): List<Genre>

    suspend fun getDetails(seriesId: Int): Series

    suspend fun getSeasons(seriesId: Int): List<Season>

    suspend fun getRecommended(seriesId: Int, page: Int, limit: Int): List<Series>

    suspend fun getReviews(seriesId: Int, page: Int = 1): List<Review>

    suspend fun getPopular(page: Int, limit: Int): List<Series>

    suspend fun getRecentlyReleased(page: Int, limit: Int): List<Series>

    suspend fun getTopRated(page: Int, limit: Int): List<Series>

    suspend fun getUserRated(accountId: Int): List<Series>

    suspend fun addRating(seriesId: Int, rating: Float)

    suspend fun deleteRating(seriesId: Int)

    suspend fun clearAll()

    suspend fun clearAllExceptRecentlyViewed()

    suspend fun deleteById(seriesId: Int)

    suspend fun getRecentlyViewed(): Flow<List<Series>>

    suspend fun clearRecentlyViewed()

    suspend fun addGenres(genres: List<Genre>)

    suspend fun addRecentlyViewed(series: Series)

    suspend fun addRecommended(series: List<Series>)

    suspend fun addPopular(series: List<Series>)

    suspend fun addRecentlyReleased(series: List<Series>)

    suspend fun addTopRated(series: List<Series>)
}