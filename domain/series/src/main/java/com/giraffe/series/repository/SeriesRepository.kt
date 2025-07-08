package com.giraffe.series.repository

import com.giraffe.series.entity.SeriesGenre
import com.giraffe.series.entity.Series

interface SeriesRepository {
    suspend fun searchSeriesByName(seriesName: String): List<Series>

    suspend fun getSeriesGenres(): List<SeriesGenre>

    suspend fun getSerisByGenre(genreId: Int): List<Series>
}
