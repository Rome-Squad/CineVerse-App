package com.giraffe.media.series

import com.giraffe.media.series.dao.SeriesDao
import com.giraffe.media.series.datasource.local.SeriesLocalDateSource
import com.giraffe.media.series.datasource.local.cacheDto.SeasonCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.SeriesCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.SeriesGenreCacheDto
import com.giraffe.media.util.safeCall
import com.giraffe.media.util.safeFlow
import com.giraffe.media.utils.SafeCall
import javax.inject.Inject

class SeriesRoomLocalDateSource @Inject constructor(
    private val seriesDao: SeriesDao,
) : SeriesLocalDateSource {

    override suspend fun insertSearchResult(
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


    override suspend fun getCachedSeriesForName(name: String, page: Int): List<SeriesCacheDto> =
        safeCall {
            seriesDao.getSeriesByKeyword(name, page)
        }


    override suspend fun getCachedGenres(): List<SeriesGenreCacheDto> = safeCall {
        seriesDao.getAllGenres()
    }


    override suspend fun insertGenres(genres: List<SeriesGenreCacheDto>) = safeCall {
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

    override suspend fun getGenresByIDs(genreIds: List<Int>) = SafeCall {
        seriesDao.getGenresByIds(genreIds)
    }

    override suspend fun insertPopularitySeries(series: List<SeriesCacheDto>) = safeCall {
        upsertWithMerge(series) { old, new ->
            old.copy(popularity = new.popularity)
        }
    }

    override suspend fun getPopularitySeries(limit: Int) = safeCall {
        seriesDao.getPopularitySeries(limit)
    }

    override suspend fun insertRecentlyReleasedSeries(series: List<SeriesCacheDto>) = safeCall {
        upsertWithMerge(series) { old, _ ->
            old.copy(recentlyReleased = System.currentTimeMillis())
        }
    }

    override suspend fun getRecentlyReleasedSeries(limit: Int) = safeCall {
        seriesDao.getRecentlyReleasedSeries(limit)
    }

    override suspend fun insertTopRatedSeries(series: List<SeriesCacheDto>) = safeCall {
        upsertWithMerge(series.map { it.copy(isTopRated = true) }) { old, _ ->
            old.copy(isTopRated = true)
        }
    }

    override suspend fun getTopRatedSeries(limit: Int) = safeCall {
        seriesDao.getTopRatedSeries(limit)
    }

    override suspend fun insertRecommendedSeries(series: List<SeriesCacheDto>) = safeCall {
        upsertWithMerge(series) { old, _ ->
            old.copy(recommended = System.currentTimeMillis())
        }
    }

    override suspend fun getRecommendedSeries(limit: Int) = safeCall {
        seriesDao.getRecommendedSeries(limit)
    }


    override suspend fun getRecentSeries() = safeFlow {
        seriesDao.getRecentSeries()
    }


    override suspend fun insertRecentSeries(seriesId: Int) = safeCall {
        seriesDao.markSeriesAsViewed(seriesId)
    }


    override suspend fun clearRecentSeries() = safeCall {
        seriesDao.clearRecentSeries()
    }

    override suspend fun getSeasonsForSeries(seriesId: Int): List<SeasonCacheDto> = safeCall {
        seriesDao.getSeasonsForSeries(seriesId)
    }

    private suspend fun upsertWithMerge(
        series: List<SeriesCacheDto>,
        merge: (old: SeriesCacheDto, new: SeriesCacheDto) -> SeriesCacheDto
    ) {
        if (series.isEmpty()) return

        val existingMap = seriesDao.getSeriesByIds(series.map { it.id }).associateBy { it.id }

        val merged = series.map { new ->
            val old = existingMap[new.id]
            if (old != null) merge(old, new) else new
        }

        seriesDao.upsertSeries(merged)
    }
}