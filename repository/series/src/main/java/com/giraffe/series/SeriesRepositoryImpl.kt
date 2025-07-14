package com.giraffe.series

import com.giraffe.series.datasource.local.SeriesLocalDateSource
import com.giraffe.series.datasource.remote.SeriesRemoteDataSource
import com.giraffe.series.entity.Review
import com.giraffe.series.entity.Season
import com.giraffe.series.entity.Series
import com.giraffe.series.entity.SeriesGenre
import com.giraffe.series.mapper.toCachedDto
import com.giraffe.series.mapper.toEntity
import com.giraffe.series.model.SeriesDto
import com.giraffe.series.mapper.toSeasonEntity
import com.giraffe.series.mapper.toSeriesEntity
import com.giraffe.series.mapper.toSeriesReviewsEntity
import com.giraffe.series.repository.SeriesRepository
import com.giraffe.series.utils.safeCall

class SeriesRepositoryImpl(
    private val remote: SeriesRemoteDataSource,
    private val local: SeriesLocalDateSource
) : SeriesRepository {
    override suspend fun searchSeriesByName(seriesName: String) = safeCall {
        val cached = local.getCachedSeriesForName(seriesName)
        if (cached.isNotEmpty()) {
            cached.map { dto ->
                val seasons = local.getSeasonsForSeries(dto.id).map { it.toSeriesEntity() }
                dto.toSeriesEntity(seasons)
            }
        } else {
            val remoteSeries = remote.getSeriesByName(seriesName)
            val cachedSeries = remoteSeries.map { it.toCachedDto() }

            local.saveSearchResult(
                seriesList = cachedSeries
            )

            remoteSeries.map { it.toSeriesEntity() }
        }
    }

    override suspend fun getSeriesGenres(): List<SeriesGenre> = safeCall {
        val cachedGenres = local.getCachedGenres()
        if (cachedGenres.isNotEmpty()) {
            cachedGenres.map { it.toSeriesEntity() }
        } else {
            val remoteGenres = remote.getGenres().map { it.toSeriesEntity() }
            local.saveGenres(remoteGenres.map { it.toCachedDto() })
            remoteGenres
        }
    }

    override suspend fun getRecentSeries(): List<Series> = safeCall {
        local.getRecentSeries().map { dto ->
            val seasons = local.getSeasonsForSeries(dto.id).map { it.toSeriesEntity() }
            dto.toSeriesEntity(seasons)
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

    override suspend fun getSeriesReviews(seriesId: Int): List<Review> = safeCall {
        remote.getSeriesReviews(seriesId).toSeriesReviewsEntity()
    }

    override suspend fun getRecommendedSeries(seriesId: Long, page: Int): List<Series> {
        return safeCall {
            remote.getSeriesRecommendations(seriesId, page)
                .map(SeriesDto::toEntity)
        }
    }
}
