package com.giraffe.media.series

import com.giraffe.media.series.dao.SeriesDao
import com.giraffe.media.series.datasource.local.SeriesLocalDateSource
import com.giraffe.media.series.datasource.local.cacheDto.SeriesCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.SeriesGenreCacheDto
import com.giraffe.media.series.mapper.toMatchesYourVibeSeriesCacheDto
import com.giraffe.media.series.mapper.toPopularSeriesCacheDto
import com.giraffe.media.series.mapper.toRecentViewedSeriesCacheDto
import com.giraffe.media.series.mapper.toRecentlyReleasedSeriesCacheDto
import com.giraffe.media.series.mapper.toTopRatedSeriesCacheDto
import com.giraffe.media.util.safeCall
import com.giraffe.media.util.safeFlow
import javax.inject.Inject

class SeriesLocalDataSourceImp @Inject constructor(
    private val seriesDao: SeriesDao,
) : SeriesLocalDateSource {
    override suspend fun addSeries(series: SeriesCacheDto) =
        safeCall { seriesDao.upsertSeries(series) }

    override fun getGenres() =
        safeFlow { seriesDao.getGenres() }

    override suspend fun syncGenres(genres: List<SeriesGenreCacheDto>) {
        safeCall {
            genres.forEach { genre ->
                seriesDao.getGenreById(genre.id)?.let {
                    seriesDao.updateGenreNameOnly(genre.id, genre.name)
                } ?: seriesDao.upsertGenres(genres)
            }
        }

    }

    override suspend fun incrementInteractionCountForGenres(genreIds: List<Int>) {
        safeCall {
            if (genreIds.isNotEmpty()) {
                seriesDao.incrementInteractionCountForGenres(genreIds)
            }
        }
    }

    override fun getGenresByIDs(genreIds: List<Int>) =
        safeFlow { seriesDao.getGenresByIds(genreIds) }

    override suspend fun clearGenres() = safeCall {
        seriesDao.clearGenres()
    }

    override suspend fun insertPopularitySeries(series: List<SeriesCacheDto>) = safeCall {
        insertSeries(series)
        seriesDao.upsertPopularSeriesIDs(series.map(SeriesCacheDto::toPopularSeriesCacheDto))
    }

    override fun getPopularitySeries(limit: Int) =
        safeFlow { seriesDao.getPopularitySeries(limit) }

    override suspend fun insertRecentlyReleasedSeries(series: List<SeriesCacheDto>) = safeCall {
        insertSeries(series)
        seriesDao.upsertRecentlyReleasedSeriesIDs(series.map(SeriesCacheDto::toRecentlyReleasedSeriesCacheDto))
    }

    override fun getRecentlyReleasedSeries(limit: Int) =
        safeFlow { seriesDao.getRecentlyReleasedSeries(limit) }

    override suspend fun insertTopRatedSeries(series: List<SeriesCacheDto>) = safeCall {
        insertSeries(series)
        seriesDao.upsertTopRatedSeriesIDs(series.map(SeriesCacheDto::toTopRatedSeriesCacheDto))
    }

    override fun getTopRatedSeries(limit: Int) =
        safeFlow { seriesDao.getTopRatedSeries(limit) }

    override suspend fun deleteSeriesFromHistoryById(seriesId: Int) = safeCall {
        seriesDao.deleteRecentlyViewedSeriesById(seriesId)
    }

    override suspend fun getTopGenreCount() = safeCall {
        seriesDao.getTopGenreCount()
    }

    override suspend fun insertMatchesYourVibe(series: List<SeriesCacheDto>) = safeCall {
        insertSeries(series)
        seriesDao.upsertMatchesYourVibeSeries(series.map(SeriesCacheDto::toMatchesYourVibeSeriesCacheDto))
    }

    override fun getMatchesYourVibe(limit: Int) =
        safeFlow { seriesDao.getMatchesYourVibeSeries(limit) }

    override fun getRecentlyViewedSeries(page: Int, pageSize: Int) = safeFlow {
        seriesDao.getRecentlyViewedSeries(page, pageSize)
    }

    override suspend fun getRecentlyViewedSeriesIds() =
        safeCall { seriesDao.getRecentlyViewedSeriesIds() }


    override suspend fun insertRecentViewedSeries(series: SeriesCacheDto) = safeCall {
        insertSeries(listOf(series))
        seriesDao.upsertRecentViewedSeries(series.toRecentViewedSeriesCacheDto())
    }


    override suspend fun clearRecentSeries() = safeCall {
        seriesDao.clearRecentlyViewedSeries()
    }


    override suspend fun clearExceptRecentlyViewed() {
        safeCall {
            seriesDao.clearSeriesExceptRecentViewed()
            seriesDao.clearPopularSeriesTable()
            seriesDao.clearRecentlyReleasedSeriesTable()
            seriesDao.clearTopRatedSeriesTable()
            seriesDao.clearMatchesYourVibeSeriesTable()
        }
    }

    override suspend fun clearAll() {
        safeCall {
            clearGenres()
            seriesDao.clearSeriesCache()
            seriesDao.clearPopularSeriesTable()
            seriesDao.clearRecentlyReleasedSeriesTable()
            seriesDao.clearTopRatedSeriesTable()
            seriesDao.clearMatchesYourVibeSeriesTable()
            clearRecentSeries()
        }
    }

    private suspend fun insertSeries(series: List<SeriesCacheDto>) = safeCall {
        seriesDao.upsertSeries(series)
    }
}