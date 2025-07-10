package com.giraffe.series.repository

import com.giraffe.series.entity.Series
import com.giraffe.series.entity.SeriesGenre


interface SeriesRepository {
    suspend fun searchSeriesByName(seriesName: String): List<Series>
    suspend fun searchSeriesByGenre(genreId: Int): List<Series>
    suspend fun getSeriesGenres(): List<SeriesGenre>
}