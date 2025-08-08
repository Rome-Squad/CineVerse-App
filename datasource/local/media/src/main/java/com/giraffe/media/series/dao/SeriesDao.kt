package com.giraffe.media.series.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.giraffe.media.series.datasource.local.cacheDto.SeriesCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.SeriesGenreCacheDto
import com.giraffe.media.utils.DatabaseConstants.SERIES_GENRE_TABLE
import com.giraffe.media.utils.DatabaseConstants.SERIES_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface SeriesDao {
    @Upsert
    suspend fun upsertSeries(series: List<SeriesCacheDto>)

    @Upsert
    suspend fun insertGenres(genres: List<SeriesGenreCacheDto>)


    @Query("SELECT * FROM $SERIES_TABLE WHERE id IN (:ids)")
    suspend fun getSeriesByIds(ids: List<Int>): List<SeriesCacheDto>

    @Query("UPDATE $SERIES_TABLE SET isRecentViewed = 1 AND recentViewedAt = :currentTime WHERE id = :seriesId")
    suspend fun markSeriesAsViewed(seriesId: Int, currentTime: Long)

    @Query("UPDATE $SERIES_TABLE SET isRecentViewed = 0")
    suspend fun clearRecentSeries()

    @Query("SELECT * FROM $SERIES_TABLE WHERE isRecentViewed = 1 ORDER BY recentViewedAt DESC")
    fun getRecentSeries(): Flow<List<SeriesCacheDto>>

    @Query("UPDATE $SERIES_TABLE SET isPopularity = 0 And isRecentlyReleased = 0 And isTopRated = 0 And isRecommended = 0")
    suspend fun resetSeriesCache()

    @Query("SELECT * FROM $SERIES_TABLE WHERE isPopularity = 1 ORDER BY popularity DESC LIMIT :limit")
    fun getPopularitySeries(limit: Int): List<SeriesCacheDto>

    @Query("SELECT * FROM $SERIES_TABLE WHERE isRecentlyReleased = 1 LIMIT :limit")
    fun getRecentlyReleasedSeries(limit: Int): List<SeriesCacheDto>

    @Query("SELECT * FROM $SERIES_TABLE WHERE isTopRated = 1 ORDER BY rate DESC LIMIT :limit")
    fun getTopRatedSeries(limit: Int): List<SeriesCacheDto>

    @Query("SELECT * FROM $SERIES_TABLE WHERE isRecommended = 1 LIMIT :limit")
    fun getRecommendedSeries(limit: Int): List<SeriesCacheDto>

    @Query("DELETE FROM $SERIES_TABLE WHERE id = :seriesId")
    suspend fun deleteSeriesById(seriesId: Int)

    @Query("SELECT * FROM $SERIES_GENRE_TABLE  ORDER BY count DESC")
    suspend fun getAllGenres(): List<SeriesGenreCacheDto>

    @Query("SELECT * FROM $SERIES_GENRE_TABLE  WHERE id IN (:genreIds)")
    suspend fun getGenresByIds(genreIds: List<Int>): List<SeriesGenreCacheDto>

    @Query("UPDATE series_genre SET count = count + 1 WHERE id IN (:genreIds)")
    suspend fun incrementInteractionCountForGenres(genreIds: List<Int>)


    @Query("DELETE FROM $SERIES_TABLE WHERE isRecentViewed = 0")
    suspend fun clearAllSeriesExceptRecentlyViewed()

    @Query("DELETE FROM $SERIES_TABLE")
    suspend fun clearAllSeries()

    @Query("DELETE FROM $SERIES_GENRE_TABLE")
    suspend fun clearAllGenres()
}