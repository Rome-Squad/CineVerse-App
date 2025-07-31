package com.giraffe.media.series.datasource.local

import com.giraffe.media.series.datasource.local.cacheDto.SeasonCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.SeriesCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.SeriesGenreCacheDto
import kotlinx.coroutines.flow.Flow

interface SeriesLocalDateSource {
    suspend fun getCachedSeriesForName(name: String, page: Int): List<SeriesCacheDto>
    suspend fun saveSearchResult(seriesList: List<SeriesCacheDto>)
    suspend fun getCachedGenres(): List<SeriesGenreCacheDto>
    suspend fun saveGenres(genres: List<SeriesGenreCacheDto>)
    suspend fun getRecentSeries(): Flow<List<SeriesCacheDto>>
    suspend fun storeRecentSeries(seriesId: Int)
    suspend fun clearRecentSeries()
    suspend fun getSeasonsForSeries(seriesId: Int): List<SeasonCacheDto>
    suspend fun clearAllData()
    suspend fun incrementInteractionCountForGenres(genreIds: List<Int>)
    suspend fun getCachedGenresByIds(genreIds: List<Int>): List<SeriesGenreCacheDto>
}