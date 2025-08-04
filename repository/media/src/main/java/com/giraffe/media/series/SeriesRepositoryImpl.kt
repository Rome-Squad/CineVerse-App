package com.giraffe.media.series

import com.giraffe.media.dto.ReviewDto
import com.giraffe.media.entity.Genre
import com.giraffe.media.mapper.toEntity
import com.giraffe.media.series.datasource.local.SeriesLocalDateSource
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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SeriesRepositoryImpl @Inject constructor(
    private val seriesRemoteDataSource: SeriesRemoteDataSource,
    private val seriesLocalDateSource: SeriesLocalDateSource,
) : SeriesRepository {
    override suspend fun searchSeriesByName(seriesName: String, page: Int) = SafeCall {
        seriesRemoteDataSource.getSeriesByName(seriesName, page).map(SeriesDto::toEntity)
    }

    override suspend fun getSeriesGenres(): List<Genre> = SafeCall {
        seriesLocalDateSource.getCachedGenres()
            .map(SeriesGenreCacheDto::toEntity)
            .ifEmpty {
                seriesRemoteDataSource.getGenres()
                    .map(GenreDto::toEntity)
                    .also { seriesLocalDateSource.saveGenres(it.map(Genre::toCacheDto)) }
            }
    }

    override suspend fun getRecentSeries() = SafeCall {
        seriesLocalDateSource.getRecentSeries().map { seriesList ->
            seriesList.map { series ->
                val seasons =
                    seriesLocalDateSource.getSeasonsForSeries(series.id).map { it.toEntity() }
                series.toEntity(seasons)
            }
        }
    }

    override suspend fun addRecentSeries(series: Series) = SafeCall {
        seriesLocalDateSource.insertRecentSeries(series.id)
    }

    override suspend fun clearRecentSeries() = SafeCall {
        seriesLocalDateSource.clearRecentSeries()
    }

    override suspend fun getSeriesDetails(seriesId: Int): Series = SafeCall {
        withContext(Dispatchers.IO) {
            val youtubeVideoId = async { seriesRemoteDataSource.getSeriesTrailerUrl(seriesId) }
            val seriesDetails = async { seriesRemoteDataSource.getSeriesDetails(seriesId) }
            seriesDetails.await().youtubeVideoId = youtubeVideoId.await()
            seriesDetails.await().toEntity()
        }
    }

    override suspend fun getSeasonOfSeries(seriesId: Int): List<Season> = SafeCall {
        seriesRemoteDataSource.getSeriesDetails(seriesId).toSeasonEntity()
    }

    override suspend fun getSeriesByGenre(genreId: Int, page: Int) = SafeCall {
        seriesRemoteDataSource.getSeriesByGenre(genreId, page).map { it.toEntity() }
    }

    override suspend fun getSeriesGenresByIds(genreIDs: List<Int>) = SafeCall {
        if (genreIDs.isNotEmpty()) {
            seriesLocalDateSource.incrementInteractionCountForGenres(genreIDs)
        }
        seriesLocalDateSource.getCachedGenres().filter { it.id in genreIDs }.map { it.toEntity() }
            .ifEmpty {
                seriesRemoteDataSource.getGenres().filter { it.id in genreIDs }
                    .map(GenreDto::toEntity)
            }
    }

    override suspend fun getPopularitySeries(page: Int, limit: Int) = SafeCall {
        seriesLocalDateSource.getPopularitySeries(limit).map { it.toEntity() }.ifEmpty {
            val seriesRemoteResult = seriesRemoteDataSource.getPopularitySeries(page).take(limit)
                .map(SeriesDto::toEntity)
            seriesLocalDateSource.insertSeries(seriesRemoteResult.map { it.toCacheDto() })
            seriesRemoteResult
        }
    }

    override suspend fun getRecentlyReleasedSeries(page: Int, limit: Int): List<Series> = SafeCall {
        if (page > 1) {
            seriesRemoteDataSource.getRecentlyReleasedSeries(page).take(limit)
                .map(SeriesDto::toEntity)
        } else {
            seriesLocalDateSource.getRecentlyReleasedSeries(limit).map { it.toEntity() }.ifEmpty {
                val seriesRemoteResult =
                    seriesRemoteDataSource.getRecentlyReleasedSeries(page).take(limit)
                        .map(SeriesDto::toEntity)
                seriesLocalDateSource.insertRecentlyReleasedSeries(seriesRemoteResult.map { it.toCacheDto() })
                seriesRemoteResult
            }
        }
    }

    override suspend fun getTopRatedSeries(page: Int): List<Series> =
        SafeCall { seriesRemoteDataSource.getTopRatedSeries(page).map(SeriesDto::toEntity) }

    override suspend fun getSeriesReviews(seriesId: Int, page: Int) = SafeCall {
        seriesRemoteDataSource.getSeriesReviews(seriesId, page).map(ReviewDto::toEntity)
    }

    override suspend fun getRecommendedSeries(seriesId: Int, page: Int): List<Series> {
        return SafeCall {
            seriesRemoteDataSource.getSeriesRecommendations(seriesId, page)
                .map(SeriesDto::toEntity)
        }
    }
}