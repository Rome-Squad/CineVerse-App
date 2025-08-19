package com.giraffe.media.series.datasource.local

import com.giraffe.media.series.datasource.local.cacheDto.SeriesCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.SeriesGenreCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.SeriesWithRecentlyViewedAt
import kotlinx.coroutines.flow.Flow

interface SeriesLocalDateSource {
    suspend fun addSeries(series: SeriesCacheDto)

    fun getGenres(): Flow<List<SeriesGenreCacheDto>>

    suspend fun syncGenres(genres: List<SeriesGenreCacheDto>)

    suspend fun incrementInteractionCountForGenres(genreIds: List<Int>)

    fun getGenresByIDs(genreIds: List<Int>): Flow<List<SeriesGenreCacheDto>>

    suspend fun clearGenres()

    fun getRecentlyViewedSeries(page: Int, pageSize: Int): Flow<List<SeriesWithRecentlyViewedAt>>

    suspend fun getRecentlyViewedSeriesIds(): List<Int>

    suspend fun insertRecentViewedSeries(series: SeriesCacheDto)

    suspend fun clearRecentSeries()

    suspend fun clearAllSeriesExceptRecentlyViewed()

    suspend fun clearAll()

    suspend fun insertPopularitySeries(series: List<SeriesCacheDto>)

    fun getPopularitySeries(limit: Int): Flow<List<SeriesCacheDto>>

    suspend fun insertRecentlyReleasedSeries(series: List<SeriesCacheDto>)

    fun getRecentlyReleasedSeries(limit: Int): Flow<List<SeriesCacheDto>>

    suspend fun insertTopRatedSeries(series: List<SeriesCacheDto>)

    fun getTopRatedSeries(limit: Int): Flow<List<SeriesCacheDto>>

    suspend fun deleteSeriesFromHistoryById(seriesId: Int)

    suspend fun getTopGenreCount(): SeriesGenreCacheDto?

    suspend fun insertMatchesYourVibe(series: List<SeriesCacheDto>)

    fun getMatchesYourVibe(limit: Int): Flow<List<SeriesCacheDto>>
}