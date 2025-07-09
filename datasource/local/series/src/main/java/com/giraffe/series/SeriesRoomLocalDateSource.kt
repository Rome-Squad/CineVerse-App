package com.giraffe.series

import com.giraffe.series.database.SearchCacheDao
import com.giraffe.series.database.SeriesDao
import com.giraffe.series.dto.SearchCacheEntity
import com.giraffe.series.dto.SeasonEntity
import com.giraffe.series.dto.SeriesEntity
import com.giraffe.series.dto.SeriesFullData
import com.giraffe.series.dto.SeriesGenreEntity
import kotlinx.coroutines.flow.first

class SeriesRoomLocalDateSource(
    private val seriesDao: SeriesDao,
    private val cacheDao: SearchCacheDao
) :SeriesLocalDateSource {

    override suspend fun saveSearchResult(
        keyword: String,
        seriesList: List<SeriesEntity>,
        seasons: List<SeasonEntity>,
        genres: List<SeriesGenreEntity>
    ) {
        val now = System.currentTimeMillis()

        if (seriesList.isNotEmpty()) {
            seriesDao.insertSeries(seriesList)
        }

        if (seasons.isNotEmpty()) {
            seriesDao.insertSeasons(seasons)
        }

        if (genres.isNotEmpty()) {
            seriesDao.insertGenres(genres)
        }

        cacheDao.insertSearchCache(
            SearchCacheEntity(
                keyword = keyword,
                seriesIds = seriesList.map { it.id },
                timestamp = now
            )
        )
    }



    override suspend fun getCachedSeriesForKeyword(keyword: String): List<SeriesFullData>? {
        val cache = cacheDao.getCacheForKeyword(keyword) ?: return null
        val now = System.currentTimeMillis()
        val isValid = now - cache.timestamp <= 60 * 60 * 1000

        return if (isValid) {
            getSeriesFullDataFromCache(cache.seriesIds)
        } else {
            invalidateCache(keyword, cache.seriesIds)
            null
        }
    }

    override suspend fun getCachedGenres(): List<SeriesGenreEntity> {
        return seriesDao.getAllGenres().first()
    }

    override suspend fun saveGenres(genres: List<SeriesGenreEntity>) {
        seriesDao.insertGenres(genres)
    }


    private suspend fun getSeriesFullDataFromCache(seriesIds: List<Int>): List<SeriesFullData> {
        val seriesList = seriesDao.getSeriesByIds(seriesIds)
        val allGenres = seriesDao.getAllGenres().first()

        return seriesList.map { series ->
            val seasons = seriesDao.getSeasonsForSeries(series.id).first()
            val matchedGenres = allGenres.filter { it.id in series.genresID }

            SeriesFullData(
                series = series,
                seasons = seasons,
                genres = matchedGenres
            )
        }
    }
    private suspend fun invalidateCache(keyword: String, expiredSeriesIds: List<Int>) {
        cacheDao.deleteCacheForKeyword(keyword)

        val allCaches = cacheDao.getAllCaches()
        val stillUsedIds = allCaches.flatMap { it.seriesIds }.toSet()
        val toDelete = expiredSeriesIds.filterNot { it in stillUsedIds }

        seriesDao.deleteSeriesByIds(toDelete)
        seriesDao.deleteSeasonsBySeriesIds(toDelete)
        cleanupUnusedGenres()
    }

    private suspend fun cleanupUnusedGenres() {
        val allSeries = seriesDao.getAllSeries().first()
        val usedGenreIds = allSeries.flatMap { it.genresID }.toSet()
        val allGenres = seriesDao.getAllGenres().first()
        val unusedGenres = allGenres.filterNot { it.id in usedGenreIds }

        if (unusedGenres.isNotEmpty()) {
            seriesDao.deleteGenresByIds(unusedGenres.map { it.id })
        }
    }



    override suspend fun clearAllData() {
        seriesDao.clearAllSeries()
        seriesDao.clearAllSeasons()
        seriesDao.clearAllGenres()
        cacheDao.clearAll()
    }

}
