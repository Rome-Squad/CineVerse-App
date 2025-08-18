package com.giraffe.media.series

import com.giraffe.media.dto.ReviewDto
import com.giraffe.media.entity.Genre
import com.giraffe.media.mapper.toEntity
import com.giraffe.media.movie.datasource.remote.dto.RatingRequest
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
import com.giraffe.media.utils.safeCall
import com.giraffe.media.utils.safeFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SeriesRepositoryImpl @Inject constructor(
    private val seriesRemote: SeriesRemoteDataSource,
    private val seriesLocal: SeriesLocalDateSource
) : SeriesRepository {
    override suspend fun getByName(name: String, page: Int) = safeCall {
        seriesRemote.getSeriesByName(name, page).map(SeriesDto::toEntity)
    }

    override suspend fun getDetails(seriesId: Int): Series = safeCall {
        withContext(Dispatchers.IO) {
            val youtubeVideoId = async { seriesRemote.getSeriesTrailerUrl(seriesId) }
            val seriesDetails = async { seriesRemote.getSeriesDetails(seriesId) }.await()
            seriesDetails.youtubeVideoId = youtubeVideoId.await()
            val series = seriesDetails.toEntity()
            addRecentlyViewed(series)
            series
        }
    }

    override suspend fun getSeasons(seriesId: Int): List<Season> = safeCall {
        seriesRemote.getSeriesDetails(seriesId).toSeasonEntity()
    }

    override suspend fun getByGenreId(genreId: Int, page: Int) = safeCall {
        seriesRemote.getSeriesByGenre(genreId, page).map { it.toEntity() }
    }

    override suspend fun getByGenreIds(genreIds: List<Int>, page: Int) = safeCall {
        seriesRemote.getSeriesByGenreIds(
            genreIds = genreIds,
            page = page
        ).map(SeriesDto::toEntity)
    }

    override suspend fun getByKeywordsId(keywords: Int, page: Int) = safeCall {
        seriesRemote.getSeriesByKeywordsId(
            keywords = keywords,
            page = page
        ).map(SeriesDto::toEntity)
    }

    override suspend fun getBySort(sortBy: String, page: Int) = safeCall {
        seriesRemote.getSeriesBySort(
            sortBy = sortBy,
            page = page
        ).map(SeriesDto::toEntity)
    }

    override suspend fun getRecommended(seriesId: Int, page: Int) = safeCall {
        seriesRemote.getSeriesRecommendations(seriesId, page).map(SeriesDto::toEntity)
    }

    // region Rete
    override suspend fun addRating(seriesId: Int, rating: Float) = safeCall {
        val requestBody = RatingRequest(value = rating)
        seriesRemote.addRating(seriesId, requestBody)
    }

    override suspend fun getUserRated(accountId: Int) = safeCall {
        seriesRemote.getRatedSeries(accountId).map(SeriesDto::toEntity)
    }

    override suspend fun getUserRating(seriesId: Int): Float? = safeCall {
        seriesRemote.getUserSeriesRating(seriesId)
    }

    override suspend fun getReviews(seriesId: Int, page: Int) = safeCall {
        seriesRemote.getSeriesReviews(seriesId, page).map(ReviewDto::toEntity)
    }

    override suspend fun deleteRating(seriesId: Int) = safeCall {
        seriesRemote.deleteSeriesRating(seriesId)
    }
    // endregion

    // region Genres
    private suspend fun incrementGenreCount(genreIds: List<Int>) {
        seriesLocal.incrementInteractionCountForGenres(genreIds)
    }

    override fun observeGenres(): Flow<List<Genre>> {
        return safeFlow {
            seriesLocal.getGenres()
                .map { it.map(SeriesGenreCacheDto::toEntity) }
                .onEach {
                    it.ifEmpty {
                        getGenres()
                    }
                }
        }
    }

    override suspend fun getGenres(): List<Genre> {
        return safeCall {
            seriesRemote.getGenres()
                .map(GenreDto::toEntity)
                .also { addGenres(it) }
        }
    }

    private suspend fun addGenres(genres: List<Genre>) = safeCall {
        seriesLocal.syncGenres(genres.map(Genre::toCacheDto))
    }

    override fun getGenresByIds(genreIds: List<Int>): Flow<List<Genre>> {
        if (genreIds.isEmpty()) return flowOf(emptyList())
        return safeFlow {
            seriesLocal.getGenresByIDs(genreIds)
                .map { it.map(SeriesGenreCacheDto::toEntity) }
                .onEach {
                    getGenres().filter { it.id in genreIds }
                }
        }
    }

    override suspend fun getTopGenreCount() =
        safeCall { seriesLocal.getTopGenreCount()?.toEntity() }
    // endregion

    // region Popular
    override fun observePopular(limit: Int): Flow<List<Series>> {
        return safeFlow {
            seriesLocal.getPopularitySeries(limit)
                .map { it.map(SeriesCacheDto::toEntity) }
                .onEach {
                    it.ifEmpty {
                        getRemotePopular(page = 1, limit = limit)
                            .also { series ->
                                addPopular(series)
                            }
                    }
                }
        }
    }

    private suspend fun getRemotePopular(page: Int, limit: Int): List<Series> {
        return seriesRemote.getPopularitySeries(page)
            .take(limit)
            .map(SeriesDto::toEntity)
    }

    private suspend fun addPopular(series: List<Series>) = safeCall {
        seriesLocal.insertPopularitySeries(series.map { it.toCacheDto() })
    }
    // endregion

    // region Recently Released
    override fun observeRecentlyReleased(limit: Int): Flow<List<Series>> {
        return safeFlow {
            seriesLocal.getRecentlyReleasedSeries(limit)
                .map { it.map(SeriesCacheDto::toEntity) }
                .onEach {
                    it.ifEmpty {
                        getRecentlyReleased(page = 1, limit = limit)
                            .also { series ->
                                addRecentlyReleased(series)
                            }
                    }
                }
        }
    }

    override suspend fun getRecentlyReleased(page: Int, limit: Int): List<Series> {
        return safeCall {
            seriesRemote.getRecentlyReleasedSeries(page)
                .take(limit)
                .map(SeriesDto::toEntity)
        }
    }

    private suspend fun addRecentlyReleased(series: List<Series>) {
        if (series.isEmpty()) return
        seriesLocal.insertRecentlyReleasedSeries(series.map(Series::toCacheDto))
    }
    // endregion

    // region Top Rated
    override fun observeTopRated(limit: Int): Flow<List<Series>> {
        return safeFlow {
            seriesLocal.getTopRatedSeries(limit)
                .map { it.map(SeriesCacheDto::toEntity) }
                .onEach {
                    it.ifEmpty {
                        getTopRated(page = 1, limit = limit)
                            .also { series ->
                                addTopRated(series)
                            }
                    }
                }
        }
    }

    override suspend fun getTopRated(page: Int, limit: Int): List<Series> {
        return safeCall {
            seriesRemote.getTopRatedSeries(page)
                .take(limit)
                .map(SeriesDto::toEntity)
        }
    }

    private suspend fun addTopRated(series: List<Series>) {
        if (series.isEmpty()) return
        safeCall { seriesLocal.insertTopRatedSeries(series.map(Series::toCacheDto)) }
    }
    // endregion

    // region Matches Your Vibe
    override fun observeMatchesYourVibe(limit: Int): Flow<List<Series>> {
        return safeFlow {
            seriesLocal.getMatchesYourVibe(limit)
                .map { it.map(SeriesCacheDto::toEntity) }
                .onEach {
                    it.ifEmpty {
                        getMatchesYourVibe(page = 1, limit = limit)
                            .also { series ->
                                addMatchesYourVibe(series)
                            }
                    }
                }
        }
    }

    override suspend fun getMatchesYourVibe(page: Int, limit: Int): List<Series> {
        return safeCall {
            getTopGenreCount()?.let { topGenre ->
                getByGenreId(topGenre.id, page).take(limit)
            } ?: emptyList()
        }
    }

    private suspend fun addMatchesYourVibe(series: List<Series>) {
        if (series.isEmpty()) return
        safeCall { seriesLocal.insertMatchesYourVibe(series.map(Series::toCacheDto)) }
    }
    // endregion

    // region Recently Viewed
    override fun observeRecentlyViewed(page: Int, pageSize: Int) = safeFlow {
        seriesLocal.getRecentlyViewedSeries(page, pageSize).map { seriesList ->
            seriesList.map { series -> series.toEntity() }
        }
    }

    override suspend fun syncRecentlyViewedSeries() {
        safeCall {
            seriesLocal.getAllRecentlyViewedSeries().forEach {
                seriesRemote.getSeriesDetails(it.id)
                    .toCacheDto()
                    .also { series ->
                        seriesLocal.addSeries(series)
                    }
            }
        }
    }

    private suspend fun addRecentlyViewed(series: Series) {
        incrementGenreCount(series.genreIDs)
        seriesLocal.insertRecentViewedSeries(series.toCacheDto())
    }

    override suspend fun deleteById(seriesId: Int) = safeCall {
        seriesLocal.deleteSeriesFromHistoryById(seriesId)
    }

    override suspend fun clearRecentlyViewed() =
        safeCall { seriesLocal.clearRecentSeries() }
    // endregion

    override suspend fun clearExceptRecentlyViewed() =
        safeCall { seriesLocal.clearExceptRecentlyViewed() }

    override suspend fun clearAll() =
        safeCall { seriesLocal.clearAll() }
}
