package com.giraffe.media.series

import com.giraffe.media.series.datasource.local.SeriesLocalDateSource
import com.giraffe.media.series.datasource.remote.SeriesRemoteDataSource
import com.giraffe.media.series.datasource.remote.dto.SeriesDto
import com.giraffe.media.series.entity.Season
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.entity.SeriesGenre
import com.giraffe.media.series.entity.SeriesReview
import com.giraffe.media.series.mapper.toCachedDto
import com.giraffe.media.series.mapper.toEntity
import com.giraffe.media.series.mapper.toSeasonEntity
import com.giraffe.media.series.mapper.toSeriesEntity
import com.giraffe.media.series.mapper.toSeriesReviewsEntity
import com.giraffe.media.series.repository.SeriesRepository
import com.giraffe.media.series.utils.safeCall

class SeriesRepositoryImpl(
    private val remote: SeriesRemoteDataSource,
    private val local: SeriesLocalDateSource
) : SeriesRepository {
    override suspend fun searchSeriesByName(seriesName: String) = safeCall {
        val cached = local.getCachedSeriesForName(seriesName)
        if (cached.isNotEmpty()) {
            cached.map { dto ->
                val seasons = local.getSeasonsForSeries(dto.id).map { it.toEntity() }
                dto.toEntity(seasons)
            }
        } else {
            val remoteSeries = remote.getSeriesByName(seriesName)
            val cachedSeries = remoteSeries.map { it.toCachedDto() }

            local.saveSearchResult(
                seriesList = cachedSeries
            )

            remoteSeries.map { it.toEntity() }
        }
    }

    override suspend fun getSeriesGenres(): List<SeriesGenre> = safeCall {
        val cachedGenres = local.getCachedGenres()
        if (cachedGenres.isNotEmpty()) {
            cachedGenres.map { it.toEntity() }
        } else {
            val remoteGenres = remote.getGenres().map { it.toEntity() }
            local.saveGenres(remoteGenres.map { it.toCachedDto() })
            remoteGenres
        }
    }

    override suspend fun getRecentSeries(): List<Series> = safeCall {
        local.getRecentSeries().map { dto ->
            val seasons = local.getSeasonsForSeries(dto.id).map { it.toEntity() }
            dto.toEntity(seasons)
        }
    }

    override suspend fun storeRecentSeries(series: Series) = safeCall {
        local.storeRecentSeries(series.id)
    }

    override suspend fun clearRecentSeries() = safeCall {
        local.clearRecentSeries()
    }


    override suspend fun getSeriesDetails(seriesId: Int): Series = safeCall {
        remote.getSeriesDetails(seriesId).toSeriesEntity()
    }

    override suspend fun getSeasonOfSeries(seriesId: Int): List<Season> = safeCall {
        remote.getSeriesDetails(seriesId).toSeasonEntity()
    }

    override suspend fun getSeriesReviews(seriesId: Int): List<SeriesReview> = safeCall {
        remote.getSeriesReviews(seriesId).toSeriesReviewsEntity()
    }

    override suspend fun getRecommendedSeries(seriesId: Long, page: Int): List<Series> {
        return safeCall {
            remote.getSeriesRecommendations(seriesId, page)
                .map(SeriesDto::toEntity)
        }
    }
}
