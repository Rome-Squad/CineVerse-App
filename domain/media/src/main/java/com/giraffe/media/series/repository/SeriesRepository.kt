package com.giraffe.media.series.repository

import com.giraffe.media.entity.Genre
import com.giraffe.media.entity.Review
import com.giraffe.media.series.entity.Season
import com.giraffe.media.series.entity.Series
import kotlinx.coroutines.flow.Flow


interface SeriesRepository {
    suspend fun searchSeriesByName(seriesName: String, page: Int): List<Series>
    suspend fun addRecentSeries(series: Series)
    suspend fun getSeriesGenres(): List<Genre>
    suspend fun getRecentSeries(): Flow<List<Series>>
    suspend fun clearRecentSeries()
    suspend fun getSeriesDetails(seriesId: Int): Series
    suspend fun getRecommendedSeries(seriesId: Int, page: Int, limit: Int): List<Series>
    suspend fun getSeriesReviews(seriesId: Int, page: Int = 1): List<Review>
    suspend fun getSeasonOfSeries(seriesId: Int): List<Season>
    suspend fun getSeriesByGenre(genreId: Int, page: Int): List<Series>
    suspend fun getSeriesGenresByIds(genreIDs: List<Int>): List<Genre>
    suspend fun addGenres(genres: List<Genre>)
    suspend fun getPopularitySeries(page: Int, limit: Int): List<Series>
    suspend fun getRecentlyReleasedSeries(page: Int, limit: Int): List<Series>
    suspend fun getTopRatedSeries(page: Int, limit: Int): List<Series>
}