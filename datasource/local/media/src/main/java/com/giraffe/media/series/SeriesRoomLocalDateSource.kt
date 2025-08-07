package com.giraffe.media.series

import com.giraffe.media.series.dao.SeriesDao
import com.giraffe.media.series.datasource.local.SeriesLocalDateSource
import com.giraffe.media.series.datasource.local.cacheDto.SeriesCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.SeriesGenreCacheDto
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
        upsertWithMerge(series) { old, new ->
            new.copy(
                isRecentViewed = old.isRecentViewed,
                recentViewedAt = old.recentViewedAt,
                isRecentlyReleased = old.isRecentlyReleased,
                isRecommended = old.isRecommended,
                isTopRated = old.isTopRated,
                isPopularity = true
            )
        }
    }

    override suspend fun getPopularitySeries(limit: Int) = safeCall {
        seriesDao.getPopularitySeries(limit)
    }

    override suspend fun insertRecentlyReleasedSeries(series: List<SeriesCacheDto>) = safeCall {
        upsertWithMerge(series) { old, new ->
            new.copy(
                isRecentViewed = old.isRecentViewed,
                recentViewedAt = old.recentViewedAt,
                isPopularity = old.isPopularity,
                isRecommended = old.isRecommended,
                isTopRated = old.isTopRated,
                isRecentlyReleased = true
            )
        }
    }

    override suspend fun getRecentlyReleasedSeries(limit: Int) = safeCall {
        seriesDao.getRecentlyReleasedSeries(limit)
    }

    override suspend fun insertTopRatedSeries(series: List<SeriesCacheDto>) = safeCall {
        upsertWithMerge(series.map { it.copy(isTopRated = true) }) { old, new ->
            new.copy(
                isRecentViewed = old.isRecentViewed,
                recentViewedAt = old.recentViewedAt,
                isPopularity = old.isPopularity,
                isRecommended = old.isRecommended,
                isTopRated = true,
                isRecentlyReleased = old.isRecentlyReleased
            )
        }
    }

    override suspend fun getTopRatedSeries(limit: Int) = safeCall {
        seriesDao.getTopRatedSeries(limit)
    }

    override suspend fun insertRecommendedSeries(series: List<SeriesCacheDto>) = safeCall {
        upsertWithMerge(series) { old, new ->
            new.copy(
                isRecentViewed = old.isRecentViewed,
                recentViewedAt = old.recentViewedAt,
                isPopularity = old.isPopularity,
                isRecommended = true,
                isTopRated = old.isTopRated,
                isRecentlyReleased = old.isRecentlyReleased
            )
        }
    }

    override suspend fun getRecommendedSeries(limit: Int) = safeCall {
        seriesDao.getRecommendedSeries(limit)
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