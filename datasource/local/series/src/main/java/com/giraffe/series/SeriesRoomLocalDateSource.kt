package com.giraffe.series

import com.giraffe.series.database.searchHistoryDao
import com.giraffe.series.database.SeriesDao
import com.giraffe.series.datasource.local.SeriesLocalDateSource
import com.giraffe.series.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class SeriesRoomLocalDateSource(
    private val seriesDao: SeriesDao,
    private val searchHistoryDao: searchHistoryDao
) : SeriesLocalDateSource {

    override suspend fun saveSearchResult(
        name: String,
        seriesList: List<CachedSeriesDto>,
        seasons: List<CachedSeasonDto>,
        genres: List<CachedSeriesGenreDto>
    ) = withContext(Dispatchers.IO) {
        val now = System.currentTimeMillis()

        val existingSeries = seriesDao.getSeriesByIds(seriesList.map { it.id })
        val isRecentMap = existingSeries.associateBy({ it.id }, { it.isRecent })

        val mergedSeries = seriesList.map { remote ->
            val wasRecent = isRecentMap[remote.id] ?: false
            remote.copy(isRecent = wasRecent)
        }

        if (mergedSeries.isNotEmpty()) {
            seriesDao.insertSeries(mergedSeries)
        }

        if (seasons.isNotEmpty()) {
            seriesDao.insertSeasons(seasons)
        }

        if (genres.isNotEmpty()) {
            seriesDao.insertGenres(genres)
        }

        searchHistoryDao.insertSearchCache(
            SearchCacheDto(
                keyword = name,
                lastSearchedTime = now
            )
        )
    }

    override suspend fun getCachedSeriesForName(name: String): List<SeriesFullData> = withContext(Dispatchers.IO) {
        val cache = searchHistoryDao.getCacheForKeyword(name)
        val now = System.currentTimeMillis()

        if (cache != null && now - cache.lastSearchedTime > CACHE_VALIDITY_DURATION_MS) {
            searchHistoryDao.deleteCacheForKeyword(name)
            return@withContext emptyList()
        }

        val seriesList = seriesDao.getSeriesByKeyword(name)
        if (seriesList.isEmpty()) return@withContext emptyList()

        val allGenres = seriesDao.getAllGenres()

        seriesList.map { series ->
            val seasons = seriesDao.getSeasonsForSeries(series.id)
            val matchedGenres = allGenres.filter { it.id in series.genresID }

            SeriesFullData(
                series = series,
                seasons = seasons,
                genres = matchedGenres
            )
        }
    }


    override suspend fun getCachedSeriesByGenre(genreId: Int): List<SeriesFullData> = withContext(Dispatchers.IO) {
        val allSeries = seriesDao.getAllSeries().first()
        val matchedSeries = allSeries.filter { genreId in it.genresID }
        if (matchedSeries.isEmpty()) return@withContext emptyList()

        val allGenres = seriesDao.getAllGenres()

        matchedSeries.map { series ->
            val seasons = seriesDao.getSeasonsForSeries(series.id)
            val seriesGenres = allGenres.filter { it.id in series.genresID }
            SeriesFullData(series, seasons, seriesGenres)
        }
    }

    override suspend fun getCachedGenres(): List<CachedSeriesGenreDto> = withContext(Dispatchers.IO) {
        val cache = searchHistoryDao.getCacheForKeyword(GENRE_CACHE_KEY)
        val now = System.currentTimeMillis()
        val isValid = cache != null && (now - cache.lastSearchedTime <= CACHE_VALIDITY_DURATION_MS)

        if (isValid) {
            seriesDao.getAllGenres()
        } else {
            emptyList()
        }
    }

    override suspend fun saveGenres(genres: List<CachedSeriesGenreDto>) = withContext(Dispatchers.IO) {
        seriesDao.insertGenres(genres)
        searchHistoryDao.insertSearchCache(
            SearchCacheDto(
                keyword = GENRE_CACHE_KEY,
                lastSearchedTime = System.currentTimeMillis()
            )
        )
    }

    override suspend fun clearAllData() = withContext(Dispatchers.IO) {
        seriesDao.clearAllSeries()
        seriesDao.clearAllSeasons()
        seriesDao.clearAllGenres()
        searchHistoryDao.clearAll()
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

    companion object {
        internal const val CACHE_VALIDITY_DURATION_MS = 60 * 60 * 1000
        private const val GENRE_CACHE_KEY = "genres"
    }
}
