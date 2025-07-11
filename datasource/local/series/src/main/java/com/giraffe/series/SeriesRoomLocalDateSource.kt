package com.giraffe.series

import com.giraffe.series.database.SeriesDao
import com.giraffe.series.datasource.local.SeriesLocalDateSource
import com.giraffe.series.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class SeriesRoomLocalDateSource(
    private val seriesDao: SeriesDao,
) : SeriesLocalDateSource {

    override suspend fun saveSearchResult(
        seriesList: List<CachedSeriesDto>
    ) = withContext(Dispatchers.IO) {

        val existingSeries = seriesDao.getSeriesByIds(seriesList.map { it.id })
        val isRecentMap = existingSeries.associateBy({ it.id }, { it.isRecent })

        val mergedSeries = seriesList.map { remote ->
            val wasRecent = isRecentMap[remote.id] ?: false
            remote.copy(isRecent = wasRecent)
        }

        if (mergedSeries.isNotEmpty()) {
            seriesDao.insertSeries(mergedSeries)
        }


    }

    override suspend fun getCachedSeriesForName(name: String): List<CachedSeriesDto> = withContext(Dispatchers.IO) {
        seriesDao.getSeriesByKeyword(name)
    }

    override suspend fun getCachedGenres(): List<CachedSeriesGenreDto> = withContext(Dispatchers.IO) {
        seriesDao.getAllGenres()
    }

    override suspend fun saveGenres(genres: List<CachedSeriesGenreDto>) = withContext(Dispatchers.IO) {
        seriesDao.insertGenres(genres)
    }

    override suspend fun clearAllData() = withContext(Dispatchers.IO) {
        seriesDao.clearAllSeries()
        seriesDao.clearAllSeasons()
        seriesDao.clearAllGenres()
    }

    override suspend fun getRecentSeries(): List<CachedSeriesDto> = withContext(Dispatchers.IO) {
        seriesDao.getRecentSeries()
    }

    override suspend fun storeRecentSeries(seriesId: Int) = withContext(Dispatchers.IO) {
        seriesDao.markSeriesAsViewed(seriesId)
    }

    override suspend fun clearRecentSeries() = withContext(Dispatchers.IO) {
        seriesDao.clearRecentSeries()
    }

    override suspend fun getSeasonsForSeries(seriesId: Int): List<CachedSeasonDto> = withContext(Dispatchers.IO) {
        seriesDao.getSeasonsForSeries(seriesId)
    }


}
