package com.giraffe.series.repository

import com.giraffe.series.entity.Series
import com.giraffe.series.entity.SeriesGenre


interface SeriesRepository {
    suspend fun searchSeriesByName(seriesName: String, page: Int = 1): Result<List<Series>>
    suspend fun searchSeriesByGenre(genreId: Int, page: Int = 1): Result<List<Series>>
    suspend fun getSeriesGenres(): Result<List<SeriesGenre>>
}