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
) : SeriesLocalDateSource {

    override suspend fun saveSearchResult(
        name: String,
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
                keyword = name,
                timestamp = now
            )
        )
    }

    override suspend fun getCachedSeriesForName(name: String): List<SeriesFullData>? {
        val cache = cacheDao.getCacheForKeyword(name)
        val now = System.currentTimeMillis()

        if (cache != null && now - cache.timestamp > CACHE_VALIDITY_DURATION_MS) {
            cacheDao.deleteCacheForKeyword(name)
            return null
        }

        val seriesList = seriesDao.getSeriesByKeyword(name)
        if (seriesList.isEmpty()) return null

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

    override suspend fun getCachedSeriesByGenre(genreId: Int): List<SeriesFullData> {
        val allSeries = seriesDao.getAllSeries().first()
        val matchedSeries = allSeries.filter { genreId in it.genresID }
        if (matchedSeries.isEmpty()) return emptyList()

        val allGenres = seriesDao.getAllGenres().first()

        return matchedSeries.map { series ->
            val seasons = seriesDao.getSeasonsForSeries(series.id).first()
            val seriesGenres = allGenres.filter { it.id in series.genresID }
            SeriesFullData(series, seasons, seriesGenres)
        }
    }

    override suspend fun getCachedGenres(): List<SeriesGenreEntity> {
        val cache = cacheDao.getCacheForKeyword(GENRE_CACHE_KEY)
        val now = System.currentTimeMillis()
        val isValid = cache != null && (now - cache.timestamp <= CACHE_VALIDITY_DURATION_MS)

        return if (isValid) {
            seriesDao.getAllGenres().first()
        } else {
            emptyList()
        }
    }

    override suspend fun saveGenres(genres: List<SeriesGenreEntity>) {
        seriesDao.insertGenres(genres)

        cacheDao.insertSearchCache(
            SearchCacheEntity(
                keyword = GENRE_CACHE_KEY,
                timestamp = System.currentTimeMillis()
            )
        )
    }

    override suspend fun clearAllData() {
        seriesDao.clearAllSeries()
        seriesDao.clearAllSeasons()
        seriesDao.clearAllGenres()
        cacheDao.clearAll()
    }

    companion object {
        private const val CACHE_VALIDITY_DURATION_MS = 60 * 60 * 1000
        private const val GENRE_CACHE_KEY = "genres"
    }
}