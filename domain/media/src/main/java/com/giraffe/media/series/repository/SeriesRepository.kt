package com.giraffe.media.series.repository

import com.giraffe.media.series.entity.Season
import com.giraffe.media.series.entity.Series
import com.giraffe.media.entity.Review
import com.giraffe.media.entity.Genre


interface SeriesRepository {
    suspend fun searchSeriesByName(seriesName: String): List<Series>
    suspend fun storeRecentSeries(series: Series)
    suspend fun getSeriesGenres(): List<Genre>
    suspend fun getRecentSeries(): List<Series>
    suspend fun clearRecentSeries()
    suspend fun getSeriesDetails(seriesId: Int): Series
    suspend fun getRecommendedSeries(seriesId: Long, page: Int): List<Series>
    suspend fun getSeriesReviews(seriesId: Int): List<Review>
    suspend fun getSeasonOfSeries(seriesId: Int): List<Season>
}