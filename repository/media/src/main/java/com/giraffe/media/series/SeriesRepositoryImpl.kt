package com.giraffe.media.series

import com.giraffe.media.dto.ReviewDto
import com.giraffe.media.entity.Genre
import com.giraffe.media.mapper.toEntity
import com.giraffe.media.movie.mapper.toEntity
import com.giraffe.media.movies.entity.Movie
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
    private val seriesLocalDateSource: SeriesLocalDateSource
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
                    .also { addGenres(it) }
            }
    }

    override suspend fun getRecentSeries() = SafeCall {
        seriesLocalDateSource.getRecentSeries().map { seriesList ->
            seriesList.map { series ->
                series.toEntity()
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
        seriesLocalDateSource.getGenresByIDs(genreIDs).map { it.toEntity() }
            .ifEmpty {
                seriesRemoteDataSource.getGenres().filter { it.id in genreIDs }
                    .map(GenreDto::toEntity)
                    .also { addGenres(it) }
            }
    }

    override suspend fun addGenres(genres: List<Genre>) {
        seriesLocalDateSource.insertGenres(genres.map(Genre::toCacheDto))
    }

    override suspend fun getPopularitySeries(page: Int, limit: Int) = SafeCall {
        seriesLocalDateSource.getPopularitySeries(limit).map { it.toEntity() }.ifEmpty {
            seriesRemoteDataSource.getPopularitySeries(page).take(limit)
                .map(SeriesDto::toEntity)
                .also { series ->
                    seriesLocalDateSource.insertPopularitySeries(series.map { it.toCacheDto() })
                }
        }
    }

    override suspend fun getRecentlyReleasedSeries(page: Int, limit: Int) = SafeCall {
        if (page > 1) {
            seriesRemoteDataSource.getRecentlyReleasedSeries(page).take(limit)
                .map(SeriesDto::toEntity)
        } else {
            seriesLocalDateSource.getRecentlyReleasedSeries(limit).map { it.toEntity() }.ifEmpty {
                seriesRemoteDataSource.getRecentlyReleasedSeries(page).take(limit)
                    .map(SeriesDto::toEntity)
                    .also { series ->
                        seriesLocalDateSource.insertRecentlyReleasedSeries(series.map { it.toCacheDto() })
                    }
            }
        }
    }

    override suspend fun getTopRatedSeries(page: Int, limit: Int) = SafeCall {
        if (page > 1) {
            seriesRemoteDataSource.getTopRatedSeries(page).take(limit)
                .map(SeriesDto::toEntity)
        } else {
            seriesLocalDateSource.getTopRatedSeries(limit).map { it.toEntity() }.ifEmpty {
                seriesRemoteDataSource.getTopRatedSeries(page).take(limit)
                    .map(SeriesDto::toEntity)
                    .also { series ->
                        seriesLocalDateSource.insertTopRatedSeries(series.map { it.toCacheDto() })
                    }
            }
        }
    }

    override suspend fun deleteSeriesById(seriesId: Int) {
        seriesLocalDateSource.deleteSeriesById(seriesId)
    }

    override suspend fun getSeriesReviews(seriesId: Int, page: Int) = SafeCall {
        seriesRemoteDataSource.getSeriesReviews(seriesId, page).map(ReviewDto::toEntity)
    }

    override suspend fun getRecommendedSeries(seriesId: Int, page: Int, limit: Int) = SafeCall {
        if (page > 1) {
            seriesRemoteDataSource.getSeriesRecommendations(seriesId, page).take(limit)
                .map(SeriesDto::toEntity)
        } else {
            seriesLocalDateSource.getRecommendedSeries(limit).map { it.toEntity() }.ifEmpty {
                seriesRemoteDataSource.getSeriesRecommendations(seriesId, page).take(limit)
                    .map(SeriesDto::toEntity)
                    .also { series ->
                        seriesLocalDateSource.insertRecommendedSeries(series.map { it.toCacheDto() })
                    }
            }
        }
    }

    override suspend fun getRatedSeries(accountId: Int): Map<Float, Series> = SafeCall {
        seriesRemoteDataSource.getRatedSeries(accountId)
            .filter { it.userRating != null }
            .associate { it.userRating!! to it.toEntity() }
    }

    override suspend fun deleteSeriesRating(seriesId: Int) = SafeCall {
        seriesRemoteDataSource.deleteSeriesRating(seriesId)
    }
}