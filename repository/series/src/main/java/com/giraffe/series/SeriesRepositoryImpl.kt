package com.giraffe.series

import com.giraffe.series.datasource.local.SeriesLocalDateSource
import com.giraffe.series.datasource.remote.SeriesRemoteDataSource
import com.giraffe.series.entity.Series
import com.giraffe.series.entity.SeriesDetails
import com.giraffe.series.entity.SeriesGenre
import com.giraffe.series.mapper.toCachedDto
import com.giraffe.series.mapper.toEntity
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

    override suspend fun getSeriesById(seriesId: Int): List<SeriesDetails> {
        TODO("Not yet implemented")
    }
}
