package com.giraffe.media.series.datasource.local

import com.giraffe.media.series.model.CachedSeasonDto
import com.giraffe.media.series.model.SeriesCacheDto
import com.giraffe.media.series.model.CachedSeriesGenreDto

interface SeriesLocalDateSource {

    suspend fun getCachedSeriesForName(name: String): List<SeriesCacheDto>

    suspend fun saveSearchResult(
        seriesList: List<SeriesCacheDto>
    )

    suspend fun getCachedGenres(): List<CachedSeriesGenreDto>
    suspend fun saveGenres(genres: List<CachedSeriesGenreDto>)

    suspend fun getRecentSeries(): List<SeriesCacheDto>
    suspend fun storeRecentSeries(seriesId: Int)
    suspend fun clearRecentSeries()

    suspend fun getSeasonsForSeries(seriesId: Int): List<CachedSeasonDto>

    suspend fun clearAllData()
}
