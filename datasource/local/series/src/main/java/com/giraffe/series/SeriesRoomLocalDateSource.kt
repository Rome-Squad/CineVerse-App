package com.giraffe.series

import com.giraffe.series.database.SearchCacheDao
import com.giraffe.series.database.SeriesDao
import com.giraffe.series.datasource.local.SeriesLocalDateSource

import com.giraffe.series.model.*
import kotlinx.coroutines.flow.first

class SeriesRoomLocalDateSource(
    private val seriesDao: SeriesDao,
    private val cacheDao: SearchCacheDao
) : SeriesLocalDateSource {
    override suspend fun saveSearchResult(
        name: String,
        seriesList: List<CachedSeriesDto>,
        seasons: List<CachedSeasonDto>,
        genres: List<CachedSeriesGenreDto>
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
            CachedSearchCacheDto(
                keyword = name,
                lastSearchedTime = now
            )
        )
    }

    override suspend fun getCachedSeriesForName(name: String): List<SeriesFullData>? {
        val cache = cacheDao.getCacheForKeyword(name)
        val now = System.currentTimeMillis()

        if (cache != null && now - cache.lastSearchedTime > CACHE_VALIDITY_DURATION_MS) {
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

    override suspend fun getCachedGenres(): List<CachedSeriesGenreDto> {
        val cache = cacheDao.getCacheForKeyword(GENRE_CACHE_KEY)
        val now = System.currentTimeMillis()
        val isValid = cache != null && (now - cache.lastSearchedTime <= CACHE_VALIDITY_DURATION_MS)

        return if (isValid) {
            seriesDao.getAllGenres().first()
        } else {
            emptyList()
        }
    }

    override suspend fun saveGenres(genres: List<CachedSeriesGenreDto>) {
        seriesDao.insertGenres(genres)

        cacheDao.insertSearchCache(
            CachedSearchCacheDto(
                keyword = GENRE_CACHE_KEY,
                lastSearchedTime = System.currentTimeMillis()
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
        internal  const val CACHE_VALIDITY_DURATION_MS = 60 * 60 * 1000
        private const val GENRE_CACHE_KEY = "genres"
    }

}