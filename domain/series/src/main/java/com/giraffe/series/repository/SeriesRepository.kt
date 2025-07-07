package com.giraffe.series.repository

import com.giraffe.series.entity.Genre
import com.giraffe.series.entity.Series

interface SeriesRepository {
    suspend fun searchSeriesByName(seriesName: String): List<Series>
    suspend fun getSeriesGenres(): List<Genre>
    suspend fun searchSeriesByNameAndGenre(seriesName: String, genreId: Int)
}