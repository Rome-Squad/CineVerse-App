package com.giraffe.media.series

import com.giraffe.media.dto.ReviewDto
import com.giraffe.media.entity.Genre
import com.giraffe.media.explore.datasource.local.LocalExploreDataSource
import com.giraffe.media.explore.datasource.local.cacheDto.SearchKeywordCacheDto
import com.giraffe.media.mapper.toEntity
import com.giraffe.media.series.datasource.local.SeriesLocalDateSource
import com.giraffe.media.series.datasource.local.cacheDto.SeriesCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.SeriesGenreCacheDto
import com.giraffe.media.series.datasource.remote.SeriesRemoteDataSource
import com.giraffe.media.series.datasource.remote.dto.GenreDto
import com.giraffe.media.series.datasource.remote.dto.SeriesDto
import com.giraffe.media.series.entity.Season
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.mapper.toCacheDto
import com.giraffe.media.series.mapper.toEntity
import com.giraffe.media.series.mapper.toSeasonEntity
import com.giraffe.media.series.repository.SeriesRepository
import com.giraffe.media.utils.SafeCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlinx.coroutines.flow.map

class SeriesRepositoryImpl @Inject constructor(
    private val remote: SeriesRemoteDataSource,
    private val local: SeriesLocalDateSource,
    private val localExploreDataSource: LocalExploreDataSource
) : SeriesRepository {
    override suspend fun searchSeriesByName(seriesName: String, page: Int) = SafeCall {
        val keywordData = localExploreDataSource.getSearchKeyword(query = seriesName)
        val isPageCached = keywordData?.seriesPages?.contains(page) == true
        if (isPageCached) {
            local.getCachedSeriesForName(seriesName, page).map(SeriesCacheDto::toEntity)
        } else {
            val remoteSeries = remote.getSeriesByName(seriesName, page).map(SeriesDto::toEntity)
            val updatedKeyword = keywordData?.copy(
                seriesPages = keywordData.seriesPages + page,
                searchedAt = System.currentTimeMillis()
            ) ?: SearchKeywordCacheDto(
                keyword = seriesName,
                seriesPages = listOf(page)
            )
            localExploreDataSource.insertSearchKeyword(updatedKeyword)
            local.saveSearchResult(remoteSeries.map { it.toCacheDto().copy(page = page) })
            remoteSeries
        }
    }

    override suspend fun getSeriesGenres(): List<Genre> = SafeCall {
        local.getCachedGenres()
            .map(SeriesGenreCacheDto::toEntity)
            .ifEmpty {
                remote.getGenres()
                    .map(GenreDto::toEntity)
                    .also { local.saveGenres(it.map(Genre::toCacheDto)) }
            }
    }

    override suspend fun getRecentSeries() = SafeCall {
        local.getRecentSeries().map { seriesList ->
            seriesList.map { series ->
                val seasons = local.getSeasonsForSeries(series.id).map { it.toEntity() }
                series.toEntity(seasons)
            }
        }
    }

    override suspend fun addRecentSeries(series: Series) = SafeCall {
        local.storeRecentSeries(series.id)
    }

    override suspend fun clearRecentSeries() = SafeCall {
        local.clearRecentSeries()
    }

    override suspend fun getSeriesDetails(seriesId: Int): Series = SafeCall {
        withContext(Dispatchers.IO) {
            val youtubeVideoId = async { remote.getSeriesTrailerUrl(seriesId) }
            val seriesDetails = async { remote.getSeriesDetails(seriesId) }
            seriesDetails.await().youtubeVideoId = youtubeVideoId.await()
            seriesDetails.await().toEntity()
        }
    }

    override suspend fun getSeasonOfSeries(seriesId: Int): List<Season> = SafeCall {
        remote.getSeriesDetails(seriesId).toSeasonEntity()
    }

    override suspend fun getSeriesByGenre(genreId: Int, page: Int) = SafeCall {
        remote.getSeriesByGenre(genreId, page).map { it.toEntity() }
    }

    override suspend fun getSeriesGenresByIds(genreIDs: List<Int>) = SafeCall {
        if (genreIDs.isNotEmpty()) {
            local.incrementInteractionCountForGenres(genreIDs)
        }
        local.getCachedGenres().filter { it.id in genreIDs }.map { it.toEntity() }.ifEmpty {
            remote.getGenres().filter { it.id in genreIDs }.map(GenreDto::toEntity)
        }
    }

    override suspend fun getPopularitySeries(page: Int): List<Series> =
        SafeCall { remote.getPopularitySeries(page).map(SeriesDto::toEntity) }

    override suspend fun getRecentlyReleasedSeries(page: Int): List<Series> =
        SafeCall { remote.getRecentlyReleasedSeries(page).map(SeriesDto::toEntity) }

    override suspend fun getTopRatedSeries(page: Int): List<Series> =
        SafeCall { remote.getTopRatedSeries(page).map(SeriesDto::toEntity) }

    override suspend fun getSeriesReviews(seriesId: Int, page: Int) = SafeCall {
        remote.getSeriesReviews(seriesId, page).map(ReviewDto::toEntity)
    }

    override suspend fun getRecommendedSeries(seriesId: Long, page: Int): List<Series> {
        return SafeCall {
            remote.getSeriesRecommendations(seriesId, page)
                .map(SeriesDto::toEntity)
        }
    }
}