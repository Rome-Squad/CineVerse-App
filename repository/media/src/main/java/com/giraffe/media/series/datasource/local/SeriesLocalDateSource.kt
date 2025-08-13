package com.giraffe.media.series.datasource.local

import com.giraffe.media.series.datasource.local.cacheDto.RecentViewedSeriesCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.SeriesCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.SeriesGenreCacheDto
import kotlinx.coroutines.flow.Flow

interface SeriesLocalDateSource {

    suspend fun getGenres(): List<SeriesGenreCacheDto>

    suspend fun insertGenres(genres: List<SeriesGenreCacheDto>)

    suspend fun incrementInteractionCountForGenres(genreIds: List<Int>)

    suspend fun getGenresByIDs(genreIds: List<Int>): List<SeriesGenreCacheDto>

    suspend fun clearGenres()

    fun getRecentSeries(): Flow<List<RecentViewedSeriesCacheDto>>

    suspend fun insertRecentViewedSeries(series: RecentViewedSeriesCacheDto)

    suspend fun clearRecentSeries()

    suspend fun clearSeries()

    suspend fun insertPopularitySeries(series: List<SeriesCacheDto>)

    suspend fun getPopularitySeries(limit: Int): List<SeriesCacheDto>

    suspend fun insertRecentlyReleasedSeries(series: List<SeriesCacheDto>)

    suspend fun getRecentlyReleasedSeries(limit: Int): List<SeriesCacheDto>

    suspend fun insertTopRatedSeries(series: List<SeriesCacheDto>)

    suspend fun getTopRatedSeries(limit: Int): List<SeriesCacheDto>

    suspend fun deleteSeriesById(seriesId: Int)

    suspend fun getTopGenreCount(): SeriesGenreCacheDto?

    suspend fun insertMatchesYourVibe(series: List<SeriesCacheDto>)

    suspend fun getMatchesYourVibe(limit: Int): List<SeriesCacheDto>
}