package com.giraffe.series

import com.giraffe.series.datasource.local.SeriesLocalDateSource
import com.giraffe.series.datasource.remote.SeriesRemoteDataSource
import com.giraffe.series.entity.Series
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
        local.getCachedSeriesForName(seriesName)
            ?.map {
                val seasons = it.seasons.map { s -> s.toEntity() }
                it.series.toEntity(seasons)
            }.orEmpty()
            .ifEmpty {
                remote.getSeriesByName(seriesName)
                    .map { it.toEntity() }
                    .also { seriesList ->
                        val cachedSeries = seriesList.map { it.toCachedDto() }
                        local.saveSearchResult(
                            name = seriesName,
                            seriesList = cachedSeries,
                            seasons = emptyList(),
                            genres = emptyList()
                        )
                    }
            }
    }

    override suspend fun getSeriesGenres(): List<SeriesGenre> = safeCall {
        local.getCachedGenres()
            .map { it.toEntity() }
            .ifEmpty {
                val remoteGenres = remote.getGenres().map { it.toEntity() }
                val cachedGenres = remoteGenres.map { it.toCachedDto() }

                local.saveGenres(cachedGenres)
                remoteGenres
            }
    }

    override suspend fun getRecentSeries(): List<Series> = safeCall {
        val recent = local.getRecentSeries()
        val recentSeriesList = recent.map { series ->
            val seasons = local.getSeasonsForSeries(series.id)
            series.toEntity(seasons.map { it.toEntity() })
        }
        recentSeriesList
    }

    override suspend fun storeRecentSeries(series: Series) = safeCall {
        local.storeRecentSeries(series.id)
    }

    override suspend fun clearRecentSeries() = safeCall {
        local.clearRecentSeries()
    }

}