package com.giraffe.series.datasource.local

import com.giraffe.series.model.CachedSeasonDto
import com.giraffe.series.model.CachedSeriesDto
import com.giraffe.series.model.SeriesFullData
import com.giraffe.series.model.CachedSeriesGenreDto

interface SeriesLocalDateSource {

    suspend fun getCachedSeriesForName(name: String): List<SeriesFullData>

    suspend fun saveSearchResult(
        name: String,
        seriesList: List<CachedSeriesDto>,
        seasons: List<CachedSeasonDto> = emptyList(),
        genres: List<CachedSeriesGenreDto> = emptyList()
    )

    suspend fun clearAllData()

    suspend fun getCachedGenres(): List<CachedSeriesGenreDto>
    suspend fun saveGenres(genres: List<CachedSeriesGenreDto>)

    suspend fun getCachedSeriesByGenre(genreId: Int): List<SeriesFullData>
    suspend fun getRecentSeries(): List<CachedSeriesDto>
    suspend fun storeRecentSeries(seriesId: Int)
    suspend fun clearRecentSeries()
    suspend fun getSeasonsForSeries(seriesId: Int): List<CachedSeasonDto>
}

