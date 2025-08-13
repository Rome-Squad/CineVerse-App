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
import com.giraffe.media.utils.safeCall
import com.giraffe.media.utils.safeFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SeriesRepositoryImpl @Inject constructor(
    private val seriesRemoteDataSource: SeriesRemoteDataSource,
    private val seriesLocalDateSource: SeriesLocalDateSource
) : SeriesRepository {
    override suspend fun getByName(name: String, page: Int) = safeCall {
        seriesRemoteDataSource.getSeriesByName(name, page).map(SeriesDto::toEntity)
    }

    override suspend fun getGenres(): List<Genre> = safeCall {
        seriesLocalDateSource.getGenres()
            .map(SeriesGenreCacheDto::toEntity)
            .ifEmpty {
                seriesRemoteDataSource.getGenres()
                    .map(GenreDto::toEntity)
                    .also { addGenres(it) }
            }
    }

    override fun getRecentlyViewed() = safeFlow {
        seriesLocalDateSource.getRecentSeries().map { seriesList ->
            seriesList.map { series -> series.toEntity() }
        }
    }

    override suspend fun addRecentlyViewed(series: Series) = safeCall {
        if (series.genreIDs.isNotEmpty()) {
            incrementGenreCount(series.genreIDs)
        }
        seriesLocalDateSource.insertRecentViewedSeries(series.toCacheDto())
    }

    private suspend fun incrementGenreCount(genreIds: List<Int>) {
        seriesLocalDateSource.incrementInteractionCountForGenres(genreIds)
    }

    override suspend fun clearRecentlyViewed() = safeCall {
        seriesLocalDateSource.clearRecentSeries()
    }

    override suspend fun clearAll() = safeCall {
        seriesLocalDateSource.clearSeries()
    }

    override suspend fun getDetails(seriesId: Int): Series = safeCall {
        withContext(Dispatchers.IO) {
            val youtubeVideoId = async { seriesRemoteDataSource.getSeriesTrailerUrl(seriesId) }
            val seriesDetails = async { seriesRemoteDataSource.getSeriesDetails(seriesId) }
            seriesDetails.await().youtubeVideoId = youtubeVideoId.await()
            seriesDetails.await().toEntity()
        }
    }

    override suspend fun getSeasons(seriesId: Int): List<Season> = safeCall {
        seriesRemoteDataSource.getSeriesDetails(seriesId).toSeasonEntity()
    }

    override suspend fun getByGenreId(genreId: Int, page: Int) = safeCall {
        seriesRemoteDataSource.getSeriesByGenre(genreId, page).map { it.toEntity() }
    }

    override suspend fun getGenresByIds(genreIDs: List<Int>) = safeCall {
        seriesLocalDateSource.getGenresByIDs(genreIDs).map { it.toEntity() }
            .ifEmpty {
                seriesRemoteDataSource.getGenres().filter { it.id in genreIDs }
                    .map(GenreDto::toEntity)
                    .also { addGenres(it) }
            }
    }

    private suspend fun addGenres(genres: List<Genre>) = safeCall {
        seriesLocalDateSource.insertGenres(genres.map(Genre::toCacheDto))
    }

    override suspend fun getPopular(page: Int, limit: Int) = safeCall {
        seriesLocalDateSource.getPopularitySeries(limit).map { it.toEntity() }.ifEmpty {
            seriesRemoteDataSource.getPopularitySeries(page).take(limit)
                .map(SeriesDto::toEntity)
                .also { addPopular(it) }
        }
    }

    private suspend fun addPopular(series: List<Series>) = safeCall {
        seriesLocalDateSource.insertPopularitySeries(series.map { it.toCacheDto() })
    }

    override suspend fun getRecentlyReleased(page: Int, limit: Int) = safeCall {
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

    private suspend fun addRecentlyReleased(series: List<Series>) = safeCall {
        seriesLocalDateSource.insertRecentlyReleasedSeries(series.map { it.toCacheDto() })
    }

    override suspend fun getTopRated(page: Int, limit: Int) = safeCall {
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

    private suspend fun addTopRated(series: List<Series>) = safeCall {
        seriesLocalDateSource.insertTopRatedSeries(series.map { it.toCacheDto() })
    }

    override suspend fun getMatchesYourVibe(page: Int, limit: Int) = safeCall {
        val topGenreCount = getTopGenreCount()
        if (topGenreCount != null) {
            if (page > 1) {
                seriesRemoteDataSource.getSeriesByGenre(genreId = topGenreCount.id, page = page)
                    .take(limit)
                    .map(SeriesDto::toEntity)
            } else {
                seriesLocalDateSource.getMatchesYourVibe(limit).map { it.toEntity() }.ifEmpty {
                    seriesRemoteDataSource.getSeriesByGenre(
                        genreId = topGenreCount.id,
                        page = page
                    )
                        .take(limit)
                        .map(SeriesDto::toEntity)
                        .also { addMatchesYourVibe(it) }
                }
            }
        } else emptyList()
    }

    override suspend fun addRating(seriesId: Int, rating: Float) = safeCall {
        val requestBody = RatingRequest(value = rating)
        seriesRemoteDataSource.addRating(seriesId, requestBody)
    }

    override suspend fun deleteById(seriesId: Int) = safeCall {
        seriesLocalDateSource.deleteSeriesFromHistoryById(seriesId)
    }

    override suspend fun getReviews(seriesId: Int, page: Int) = safeCall {
        seriesRemoteDataSource.getSeriesReviews(seriesId, page).map(ReviewDto::toEntity)
    }

    override suspend fun getRecommended(seriesId: Int, page: Int) = safeCall {
        seriesRemoteDataSource.getSeriesRecommendations(seriesId, page).map(SeriesDto::toEntity)
    }


    override suspend fun getUserRated(accountId: Int) = safeCall {
        seriesRemoteDataSource.getRatedSeries(accountId).map(SeriesDto::toEntity)
    }

    override suspend fun deleteRating(seriesId: Int) = safeCall {
        seriesRemoteDataSource.deleteSeriesRating(seriesId)
    }

    override suspend fun getTopGenreCount() = safeCall {
        seriesLocalDateSource.getTopGenreCount()?.toEntity()
    }

    private suspend fun addMatchesYourVibe(series: List<Series>) = safeCall {
        seriesLocalDateSource.insertMatchesYourVibe(series.map { it.toCacheDto() })
    }
}