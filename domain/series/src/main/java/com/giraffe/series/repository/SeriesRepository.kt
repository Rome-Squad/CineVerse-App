package com.giraffe.series.repository

import com.giraffe.series.entity.Series
import com.giraffe.series.entity.SeriesDetails
import com.giraffe.series.entity.SeriesGenre


interface SeriesRepository {
    suspend fun searchSeriesByName(seriesName: String): List<Series>
    suspend fun storeRecentSeries(series: Series)
    suspend fun getSeriesGenres(): List<SeriesGenre>
    suspend fun getRecentSeries(): List<Series>
    suspend fun clearRecentSeries()
    suspend fun getSeriesById(seriesId: Int): List<SeriesDetails>
    suspend fun getSeriesDetails(seriesId: Int): SeriesDetails
}