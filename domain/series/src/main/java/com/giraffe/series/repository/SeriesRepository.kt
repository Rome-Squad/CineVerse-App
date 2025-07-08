package com.giraffe.series.repository

import com.giraffe.series.entity.Series
import com.giraffe.series.entity.SeriesGenre


interface SeriesRepository {
    suspend fun getSeriesByName(seriesName: String): Result<List<Series>>
    suspend fun getSeriesByGenre(genreId: Int): Result<List<Series>>
    suspend fun getSeriesGenres(): Result<List<SeriesGenre>>
}