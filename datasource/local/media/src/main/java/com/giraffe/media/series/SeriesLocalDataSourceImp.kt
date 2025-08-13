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
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class SeriesLocalDataSourceImp @Inject constructor(
    private val seriesDao: SeriesDao,
) : SeriesLocalDateSource {

    override suspend fun getGenres(): List<SeriesGenreCacheDto> = safeCall {
        seriesDao.getGenres()
    }

    override suspend fun insertGenres(genres: List<SeriesGenreCacheDto>) = safeCall {
        seriesDao.upsertGenres(genres)
    }

    override suspend fun incrementInteractionCountForGenres(genreIds: List<Int>) = safeCall {
        if (genreIds.isNotEmpty()) {
            seriesDao.incrementInteractionCountForGenres(genreIds)
        }
    }

    override suspend fun getGenresByIDs(genreIds: List<Int>) = safeCall {
        seriesDao.getGenresByIds(genreIds)
    }

    override suspend fun clearGenres() = safeCall {
        seriesDao.clearGenres()
    }

    override suspend fun clearSeries() = safeCall {
        seriesDao.clearSeriesExceptRecentViewed()
        seriesDao.clearPopularSeriesTable()
        seriesDao.clearRecentlyReleasedSeriesTable()
        seriesDao.clearTopRatedSeriesTable()
        seriesDao.clearMatchesYourVibeSeriesTable()
    }

    override suspend fun insertPopularitySeries(series: List<SeriesCacheDto>) = safeCall {
        insertSeries(series)
        seriesDao.upsertPopularSeriesIDs(series.map(SeriesCacheDto::toPopularSeriesCacheDto))
    }

    override suspend fun getPopularitySeries(limit: Int) = safeCall {
        seriesDao.getPopularitySeries(limit)
    }

    override suspend fun insertRecentlyReleasedSeries(series: List<SeriesCacheDto>) = safeCall {
        insertSeries(series)
        seriesDao.upsertRecentlyReleasedSeriesIDs(series.map(SeriesCacheDto::toRecentlyReleasedSeriesCacheDto))
    }

    override suspend fun getRecentlyReleasedSeries(limit: Int) = safeCall {
        seriesDao.getRecentlyReleasedSeries(limit)
    }

    override suspend fun insertTopRatedSeries(series: List<SeriesCacheDto>) = safeCall {
        insertSeries(series)
        seriesDao.upsertTopRatedSeriesIDs(series.map(SeriesCacheDto::toTopRatedSeriesCacheDto))
    }

    override suspend fun getTopRatedSeries(limit: Int) = safeCall {
        seriesDao.getTopRatedSeries(limit)
    }

    override suspend fun deleteSeriesFromHistoryById(seriesId: Int) = safeCall {
        seriesDao.deleteSeriesFromHistoryById(seriesId)
    }

    override suspend fun getTopGenreCount() = safeCall {
        seriesDao.getTopGenreCount()
    }

    override suspend fun insertMatchesYourVibe(series: List<SeriesCacheDto>) = safeCall {
        insertSeries(series)
        seriesDao.upsertMatchesYourVibeSeries(series.map(SeriesCacheDto::toMatchesYourVibeSeriesCacheDto))
    }

    override suspend fun getMatchesYourVibe(limit: Int) = safeCall {
        seriesDao.getMatchesYourVibeSeries(limit)
    }


    override fun getRecentSeries() = safeFlow {
        seriesDao.getRecentSeries().onStart { seriesDao.syncRecentViewedTime() }
    }


    override suspend fun insertRecentViewedSeries(series: SeriesCacheDto) = safeCall {
        insertSeries(listOf(series))
        seriesDao.upsertRecentViewedSeries(series.toRecentViewedSeriesCacheDto())
    }


    override suspend fun clearRecentSeries() = safeCall {
        seriesDao.clearRecentSeries()
    }

    private suspend fun insertSeries(series: List<SeriesCacheDto>) = safeCall {
        seriesDao.upsertSeries(series)
    }
}