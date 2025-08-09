package com.giraffe.media.series

import com.giraffe.media.dto.ReviewDto
import com.giraffe.media.entity.Genre
import com.giraffe.media.mapper.toEntity
import com.giraffe.media.movie.datasource.remote.dto.RatingRequest
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
    override suspend fun getByName(name: String, page: Int) = SafeCall {
        seriesRemoteDataSource.getSeriesByName(name, page).map(SeriesDto::toEntity)
    }

    override suspend fun getGenres(): List<Genre> = SafeCall {
        seriesLocalDateSource.getCachedGenres()
            .map(SeriesGenreCacheDto::toEntity)
            .ifEmpty {
                seriesRemoteDataSource.getGenres()
                    .map(GenreDto::toEntity)
                    .also { addGenres(it) }
            }
    }

    override suspend fun getRecentlyViewed() = SafeCall {
        seriesLocalDateSource.getRecentSeries().map { seriesList ->
            seriesList.map { series ->
                series.toEntity()
            }
        }
    }

    override suspend fun addRecentlyViewed(series: Series) = SafeCall {
        seriesLocalDateSource.insertRecentSeries(series.id)
    }

    override suspend fun clearRecentlyViewed() = SafeCall {
        seriesLocalDateSource.clearRecentSeries()
    }

    override suspend fun clearAllExceptRecentlyViewed() = SafeCall {
        seriesLocalDateSource.clearAllSeriesExceptRecentlyViewed()
    }

    override suspend fun clearAll() = SafeCall {
        seriesLocalDateSource.clearAllSeries()
    }

    override suspend fun getDetails(seriesId: Int): Series = SafeCall {
        withContext(Dispatchers.IO) {
            val youtubeVideoId = async { seriesRemoteDataSource.getSeriesTrailerUrl(seriesId) }
            val seriesDetails = async { seriesRemoteDataSource.getSeriesDetails(seriesId) }
            seriesDetails.await().youtubeVideoId = youtubeVideoId.await()
            seriesDetails.await().toEntity()
        }
    }

    override suspend fun getSeasons(seriesId: Int): List<Season> = SafeCall {
        seriesRemoteDataSource.getSeriesDetails(seriesId).toSeasonEntity()
    }

    override suspend fun getByGenreId(genreId: Int, page: Int) = SafeCall {
        seriesRemoteDataSource.getSeriesByGenre(genreId, page).map { it.toEntity() }
    }

    override suspend fun getGenresByIds(genreIDs: List<Int>) = SafeCall {
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

    override suspend fun addGenres(genres: List<Genre>) = SafeCall {
        seriesLocalDateSource.insertGenres(genres.map(Genre::toCacheDto))
    }

    override suspend fun getPopular(page: Int, limit: Int) = SafeCall {
        seriesLocalDateSource.getPopularitySeries(limit).map { it.toEntity() }.ifEmpty {
            seriesRemoteDataSource.getPopularitySeries(page).take(limit)
                .map(SeriesDto::toEntity)
                .also { addPopular(it) }
        }
    }

    override suspend fun addPopular(series: List<Series>) = SafeCall {
        seriesLocalDateSource.insertPopularitySeries(series.map { it.toCacheDto() })
    }

    override suspend fun getRecentlyReleased(page: Int, limit: Int) = SafeCall {
        if (page > 1) {
            seriesRemoteDataSource.getRecentlyReleasedSeries(page).take(limit)
                .map(SeriesDto::toEntity)
        } else {
            seriesLocalDateSource.getRecentlyReleasedSeries(limit).map { it.toEntity() }.ifEmpty {
                seriesRemoteDataSource.getRecentlyReleasedSeries(page).take(limit)
                    .map(SeriesDto::toEntity)
                    .also { addRecentlyReleased(it) }
            }
        }
    }

    override suspend fun addRecentlyReleased(series: List<Series>) = SafeCall {
        seriesLocalDateSource.insertRecentlyReleasedSeries(series.map { it.toCacheDto() })
    }

    override suspend fun getTopRated(page: Int, limit: Int) = SafeCall {
        if (page > 1) {
            seriesRemoteDataSource.getTopRatedSeries(page).take(limit)
                .map(SeriesDto::toEntity)
        } else {
            seriesLocalDateSource.getTopRatedSeries(limit).map { it.toEntity() }.ifEmpty {
                seriesRemoteDataSource.getTopRatedSeries(page).take(limit)
                    .map(SeriesDto::toEntity)
                    .also { addTopRated(it) }
            }
        }
    }

    override suspend fun addTopRated(series: List<Series>) = SafeCall {
        seriesLocalDateSource.insertTopRatedSeries(series.map { it.toCacheDto() })
    }

    override suspend fun addRating(seriesId: Int, rating: Float) = SafeCall {
        val requestBody = RatingRequest(value = rating)
        seriesRemoteDataSource.addRating(seriesId, requestBody)
    }

    override suspend fun deleteById(seriesId: Int) = SafeCall {
        seriesLocalDateSource.deleteSeriesById(seriesId)
    }

    override suspend fun getReviews(seriesId: Int, page: Int) = SafeCall {
        seriesRemoteDataSource.getSeriesReviews(seriesId, page).map(ReviewDto::toEntity)
    }

    override suspend fun getRecommended(seriesId: Int, page: Int, limit: Int) = SafeCall {
        if (page > 1) {
            seriesRemoteDataSource.getSeriesRecommendations(seriesId, page).take(limit)
                .map(SeriesDto::toEntity)
        } else {
            seriesLocalDateSource.getRecommendedSeries(limit).map { it.toEntity() }.ifEmpty {
                seriesRemoteDataSource.getSeriesRecommendations(seriesId, page).take(limit)
                    .map(SeriesDto::toEntity)
                    .also { addRecommended(it) }
            }
        }
    }

    override suspend fun addRecommended(series: List<Series>) = SafeCall {
        seriesLocalDateSource.insertRecommendedSeries(series.map { it.toCacheDto() })
    }

    override suspend fun getUserRated(accountId: Int) = SafeCall {
        seriesRemoteDataSource.getRatedSeries(accountId).map(SeriesDto::toEntity)
    }

    override suspend fun deleteRating(seriesId: Int) = SafeCall {
        seriesRemoteDataSource.deleteSeriesRating(seriesId)
    }
}