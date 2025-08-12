package com.giraffe.media.series

import com.giraffe.media.series.dao.SeriesDao
import com.giraffe.media.series.datasource.local.SeriesLocalDateSource
import com.giraffe.media.series.datasource.local.cacheDto.PopularSeriesCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.RecentlyReleasedSeriesCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.SeriesCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.SeriesGenreCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.TopRatedSeriesCacheDto
import com.giraffe.media.util.safeCall
import com.giraffe.media.util.safeFlow
import com.giraffe.media.utils.SafeCall
import javax.inject.Inject

class SeriesRoomLocalDateSource @Inject constructor(
    private val seriesDao: SeriesDao,
) : SeriesLocalDateSource {

    override suspend fun getCachedGenres(): List<SeriesGenreCacheDto> = safeCall {
        seriesDao.getAllGenres()
    }


    override suspend fun insertGenres(genres: List<SeriesGenreCacheDto>) = safeCall {
        seriesDao.insertGenres(genres)
    }


    override suspend fun clearAllSeriesExceptRecentlyViewed() = safeCall {
        seriesDao.clearAllSeriesExceptRecentlyViewed()
        seriesDao.clearAllGenres()
    }

    override suspend fun clearAllSeries() {
        seriesDao.clearAllSeries()
        seriesDao.clearAllGenres()
    }

    override suspend fun incrementInteractionCountForGenres(genreIds: List<Int>) {
        seriesDao.incrementInteractionCountForGenres(genreIds)
    }

    override suspend fun getGenresByIDs(genreIds: List<Int>) = SafeCall {
        seriesDao.getGenresByIds(genreIds)
    }

    override suspend fun insertPopularitySeries(series: List<SeriesCacheDto>) = safeCall {
        seriesDao.upsertSeries(series)
        seriesDao.upsertPopularSeriesIDs(series.map { it.toPopularSeriesCacheDto() })
    }

    override suspend fun getPopularitySeries(limit: Int) = safeCall {
        seriesDao.getPopularitySeries(limit)
    }

    override suspend fun insertRecentlyReleasedSeries(series: List<SeriesCacheDto>) = safeCall {
        seriesDao.upsertSeries(series)
        seriesDao.upsertRecentlyReleasedSeriesIDs(series.map { it.toRecentlyReleasedSeriesCacheDto() })
    }

    override suspend fun getRecentlyReleasedSeries(limit: Int) = safeCall {
        seriesDao.getRecentlyReleasedSeries(limit)
    }

    override suspend fun insertTopRatedSeries(series: List<SeriesCacheDto>) = safeCall {
        seriesDao.upsertSeries(series)
        seriesDao.upsertTopRatedSeriesIDs(series.map { it.toTopRatedSeriesCacheDto() })
    }

    override suspend fun getTopRatedSeries(limit: Int) = safeCall {
        seriesDao.getTopRatedSeries(limit)
    }

    override suspend fun deleteSeriesById(seriesId: Int) {
        seriesDao.deleteSeriesById(seriesId)

    }


    override suspend fun getRecentSeries() = safeFlow {
        seriesDao.getRecentSeries()
    }


    override suspend fun insertRecentSeries(seriesId: Int) = safeCall {
        seriesDao.markSeriesAsViewed(
            seriesId = seriesId,
            currentTime = System.currentTimeMillis()
        )
    }


    override suspend fun clearRecentSeries() = safeCall {
        seriesDao.clearRecentSeries()
    }
}

fun SeriesCacheDto.toPopularSeriesCacheDto() = PopularSeriesCacheDto(
    id = id,
)

fun SeriesCacheDto.toRecentlyReleasedSeriesCacheDto() = RecentlyReleasedSeriesCacheDto(
    id = id,
)

fun SeriesCacheDto.toTopRatedSeriesCacheDto() = TopRatedSeriesCacheDto(
    id = id,
)