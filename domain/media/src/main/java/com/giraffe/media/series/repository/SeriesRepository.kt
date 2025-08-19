package com.giraffe.media.series.repository

import com.giraffe.media.entity.Genre
import com.giraffe.media.entity.Review
import com.giraffe.media.series.entity.Season
import com.giraffe.media.series.entity.Series
import kotlinx.coroutines.flow.Flow

interface SeriesRepository {

    suspend fun getByName(name: String, page: Int): List<Series>

    suspend fun getByGenreId(genreId: Int, page: Int): List<Series>

    suspend fun getByGenreIds(genreIds: List<Int>, page: Int): List<Series>

    suspend fun getByKeywordsId(keywords: Int, page: Int): List<Series>

    suspend fun getBySort(sortBy: String, page: Int): List<Series>

    fun observeGenres(): Flow<List<Genre>>

    suspend fun getGenres(): List<Genre>

    fun getGenresByIds(genreIds: List<Int>): Flow<List<Genre>>

    suspend fun getDetails(seriesId: Int): Series

    suspend fun getSeasons(seriesId: Int): List<Season>

    suspend fun getRecommended(seriesId: Int, page: Int): List<Series>

    suspend fun getReviews(seriesId: Int, page: Int = 1): List<Review>

    fun observePopular(limit: Int): Flow<List<Series>>

    fun observeRecentlyReleased(limit: Int): Flow<List<Series>>

    suspend fun getRecentlyReleased(page: Int, limit: Int): List<Series>

    fun observeTopRated(limit: Int): Flow<List<Series>>

    suspend fun getTopRated(page: Int, limit: Int): List<Series>

    suspend fun getUserRated(accountId: Int): List<Series>

    suspend fun addRating(seriesId: Int, rating: Float)

    suspend fun deleteRating(seriesId: Int)

    suspend fun clearAllSeriesExceptRecentlyViewed()

    suspend fun clearAll()

    suspend fun deleteById(seriesId: Int)

    fun observeRecentlyViewed(page: Int, pageSize: Int): Flow<List<Series>>

    suspend fun syncRecentlyViewedSeries()

    suspend fun clearRecentlyViewed()

    fun observeMatchesYourVibe(limit: Int): Flow<List<Series>>

    suspend fun getMatchesYourVibe(page: Int, limit: Int): List<Series>

    suspend fun getTopGenreCount(): Genre?

    suspend fun getUserRating(seriesId: Int): Float?
}