package com.giraffe.media.series

import com.giraffe.media.series.datasource.local.SeriesLocalDateSource
import com.giraffe.media.series.datasource.remote.SeriesRemoteDataSource
import com.giraffe.media.series.datasource.remote.dto.SeriesDto
import com.giraffe.media.series.entity.Season
import com.giraffe.media.series.entity.Series
import com.giraffe.media.entity.Genre
import com.giraffe.media.series.mapper.toDto
import com.giraffe.media.entity.Review
import com.giraffe.media.series.mapper.toEntity
import com.giraffe.media.series.mapper.toSeasonEntity
import com.giraffe.media.series.repository.SeriesRepository
import com.giraffe.media.utils.SafeCall

class SeriesRepositoryImpl(
    private val remote: SeriesRemoteDataSource,
    private val local: SeriesLocalDateSource
) : SeriesRepository {
    override suspend fun searchSeriesByName(seriesName: String) = SafeCall {
        val cached = local.getCachedSeriesForName(seriesName)
        if (cached.isNotEmpty()) {
            cached.map { dto ->
                val seasons = local.getSeasonsForSeries(dto.id).map { it.toEntity() }
                dto.toEntity(seasons)
            }
        } else {
            val remoteSeries = remote.getSeriesByName(seriesName)
            val cachedSeries = remoteSeries.map { it.toDto() }

            local.saveSearchResult(
                seriesList = cachedSeries
            )

            remoteSeries.map { it.toEntity() }
        }
    }

    override suspend fun getSeriesGenres(): List<Genre> = SafeCall {

        val cachedGenres = local.getCachedGenres()
        if (cachedGenres.isNotEmpty()) {
            cachedGenres.map { it.toEntity() }
        } else {
            val remoteGenres = remote.getGenres().map { it.toEntity() }
            local.saveGenres(remoteGenres.map { it.toDto() })
            remoteGenres
        }
    }

    override suspend fun getRecentSeries(): List<Series> = SafeCall {
        local.getRecentSeries().map { dto ->
            val seasons = local.getSeasonsForSeries(dto.id).map { it.toEntity() }
            dto.toEntity(seasons)
        }
    }

    override suspend fun storeRecentSeries(series: Series) = SafeCall {
        local.storeRecentSeries(series.id)
    }

    override suspend fun clearRecentSeries() = SafeCall {
        local.clearRecentSeries()
    }


    override suspend fun getSeriesDetails(seriesId: Int): Series = SafeCall {
        remote.getSeriesDetails(seriesId).toEntity()
    }

    override suspend fun getSeasonOfSeries(seriesId: Int): List<Season> = SafeCall {
        remote.getSeriesDetails(seriesId).toSeasonEntity()
    }

    override suspend fun getSeriesReviews(seriesId: Int): List<Review> = SafeCall {
        remote.getSeriesReviews(seriesId).toEntity()
    }

    override suspend fun getRecommendedSeries(seriesId: Long, page: Int): List<Series> {
        return SafeCall {
            remote.getSeriesRecommendations(seriesId, page)
                .map(SeriesDto::toEntity)
        }
    }
}
