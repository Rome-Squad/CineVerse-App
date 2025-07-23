package com.giraffe.media.series

import com.giraffe.media.series.dao.SeriesDao
import com.giraffe.media.series.datasource.local.SeriesLocalDateSource
import com.giraffe.media.series.datasource.local.cacheDto.SeasonCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.SeriesCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.SeriesGenreCacheDto
import com.giraffe.media.util.safeCall
import com.giraffe.media.utils.SafeCall

class SeriesRoomLocalDateSource(
    private val seriesDao: SeriesDao,
) : SeriesLocalDateSource {

    override suspend fun saveSearchResult(
        seriesList: List<SeriesCacheDto>
    ) = safeCall {
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


    override suspend fun getCachedSeriesForName(name: String): List<SeriesCacheDto> = safeCall {
        seriesDao.getSeriesByKeyword(name)
    }


    override suspend fun getCachedGenres(): List<SeriesGenreCacheDto> = safeCall {
        seriesDao.getAllGenres()
    }


    override suspend fun saveGenres(genres: List<SeriesGenreCacheDto>) = safeCall {
        seriesDao.insertGenres(genres)
    }


    override suspend fun clearAllData() = safeCall {
        seriesDao.clearAllSeries()
        seriesDao.clearAllSeasons()
        seriesDao.clearAllGenres()
    }

    override suspend fun incrementInteractionCountForGenres(genreIds: List<Int>) {
        seriesDao.incrementInteractionCountForGenres(genreIds)
    }

    override suspend fun getCachedGenresByIds(genreIds: List<Int>): List<SeriesGenreCacheDto> =
        SafeCall {
            seriesDao.getAllGenres().filter { it.id in genreIds }
        }

    override suspend fun getRecentSeries(): List<SeriesCacheDto> = safeCall {
        seriesDao.getRecentSeries()
    }


    override suspend fun storeRecentSeries(seriesId: Int) = safeCall {
        seriesDao.markSeriesAsViewed(seriesId)
    }


    override suspend fun clearRecentSeries() = safeCall {
        seriesDao.clearRecentSeries()
    }

    override suspend fun getSeasonsForSeries(seriesId: Int): List<SeasonCacheDto> = safeCall {
        seriesDao.getSeasonsForSeries(seriesId)
    }
}