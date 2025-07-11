package com.giraffe.series.datasource.local

import com.giraffe.series.model.CachedSeasonDto
import com.giraffe.series.model.CachedSeriesDto
import com.giraffe.series.model.CachedSeriesGenreDto

interface SeriesLocalDateSource {

    suspend fun getCachedSeriesForName(name: String): List<CachedSeriesDto>

    suspend fun saveSearchResult(
        seriesList: List<CachedSeriesDto>
    )

    suspend fun getCachedGenres(): List<CachedSeriesGenreDto>
    suspend fun saveGenres(genres: List<CachedSeriesGenreDto>)

    suspend fun getRecentSeries(): List<CachedSeriesDto>
    suspend fun storeRecentSeries(seriesId: Int)
    suspend fun clearRecentSeries()

    suspend fun getSeasonsForSeries(seriesId: Int): List<CachedSeasonDto>

    suspend fun clearAllData()
}
