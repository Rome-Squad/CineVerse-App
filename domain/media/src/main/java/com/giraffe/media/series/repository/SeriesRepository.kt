package com.giraffe.media.series.repository

import com.giraffe.media.entity.Genre
import com.giraffe.media.entity.Review
import com.giraffe.media.series.entity.Season
import com.giraffe.media.series.entity.Series
import kotlinx.coroutines.flow.Flow

interface SeriesRepository {

    suspend fun getByName(name: String, page: Int): List<Series>

    suspend fun getByGenreId(genreId: Int, page: Int): List<Series>

    suspend fun getSeriesByGenreIds(genreIds: List<Int>, page: Int): List<Series>

    suspend fun getSeriesByKeywordsId(keywords: String, page: Int): List<Series>

    suspend fun getSeriesBySort(sortBy: String, page: Int): List<Series>

    suspend fun getGenres(): List<Genre>

    suspend fun getGenresByIds(genreIDs: List<Int>): List<Genre>

    suspend fun getDetails(seriesId: Int): Series

    suspend fun getSeasons(seriesId: Int): List<Season>

    suspend fun getRecommended(seriesId: Int, page: Int): List<Series>

    suspend fun getReviews(seriesId: Int, page: Int = 1): List<Review>

    suspend fun getPopular(page: Int, limit: Int): List<Series>

    suspend fun getRecentlyReleased(page: Int, limit: Int): List<Series>

    suspend fun getTopRated(page: Int, limit: Int): List<Series>

    suspend fun getUserRated(accountId: Int): List<Series>

    suspend fun addRating(seriesId: Int, rating: Float)

    suspend fun deleteRating(seriesId: Int)

    suspend fun clearAll()

    suspend fun deleteById(seriesId: Int)

    fun getRecentlyViewed(page: Int, pageSize: Int): Flow<List<Series>>

    suspend fun clearRecentlyViewed()

    suspend fun addRecentlyViewed(series: Series)

    suspend fun getMatchesYourVibe(page: Int, limit: Int): List<Series>

    suspend fun getTopGenreCount(): Genre?

    suspend fun getUserRating(seriesId: Int): Float?
}