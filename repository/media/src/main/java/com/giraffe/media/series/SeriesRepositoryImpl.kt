package com.giraffe.media.series

import com.giraffe.media.entity.Genre
import com.giraffe.media.exception.NoInternetDataException
import com.giraffe.media.exception.UserNotLoggedInException
import com.giraffe.media.movie.datasource.remote.dto.RatingRequest
import com.giraffe.media.series.datasource.local.SeriesLocalDateSource
import com.giraffe.media.series.datasource.local.cacheDto.SeriesGenreCacheDto
import com.giraffe.media.series.datasource.remote.SeriesRemoteDataSource
import com.giraffe.media.series.datasource.remote.dto.GenreDto
import com.giraffe.media.series.datasource.remote.dto.ReviewDto
import com.giraffe.media.series.datasource.remote.dto.SeriesDto
import com.giraffe.media.series.entity.Season
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.mapper.toCacheDto
import com.giraffe.media.series.mapper.toEntity
import com.giraffe.media.series.mapper.toSeasonEntity
import com.giraffe.media.series.repository.SeriesRepository
import com.giraffe.media.utils.SafeCall
import com.giraffe.user.SessionManager

class SeriesRepositoryImpl(
    private val remote: SeriesRemoteDataSource,
    private val local: SeriesLocalDateSource,
    private val sessionManager: SessionManager
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
            val cachedSeries = remoteSeries.map { it.toCacheDto() }

            local.saveSearchResult(
                seriesList = cachedSeries
            )

            remoteSeries.map { it.toEntity() }
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

    override suspend fun getSeriesByGenre(genreId: Int) = SafeCall {
        remote.getSeriesByGenre(genreId).map { it.toEntity() }
    }

    override suspend fun getSeriesGenresByIds(genreIDs: List<Int>) = SafeCall {
        if (genreIDs.isNotEmpty()) {
            local.incrementInteractionCountForGenres(genreIDs)
        }
        local.getCachedGenres().filter { it.id in genreIDs }.map { it.toEntity() }.ifEmpty {
            remote.getGenres().filter { it.id in genreIDs }.map(GenreDto::toEntity)
        }
    }

    override suspend fun addSeriesRating(seriesId: Int, ratingValue: Float) = SafeCall{
        val sessionId = getSessionId()
        if (sessionId.isBlank()) {
            throw UserNotLoggedInException()
        }
        val requestBody = RatingRequest(value = ratingValue)
        remote.addRating(seriesId, sessionId, requestBody)
    }

    override suspend fun getUserSeriesRating(seriesId: Int): Float = SafeCall{
        val sessionId = getSessionId()
        remote.getSeriesRating(seriesId, sessionId)
    }

    override suspend fun deleteSeriesRating(seriesId: Int) {
        val sessionId = getSessionId()
        if (sessionId.isBlank()) {
            throw UserNotLoggedInException()
        }
        remote.deleteSeriesRating(seriesId, sessionId)
    }

    override suspend fun getSeriesReviews(seriesId: Int, page: Int) = SafeCall {
        remote.getSeriesReviews(seriesId, page).map(ReviewDto::toEntity)
    }

    override suspend fun getRecommendedSeries(seriesId: Long, page: Int): List<Series> {
        return SafeCall {
            remote.getSeriesRecommendations(seriesId, page)
                .map(SeriesDto::toEntity)
        }
    }

    private suspend fun getSessionId() = SafeCall {
        sessionManager.createGuestSessionId() ?: throw NoInternetDataException()
    }
}