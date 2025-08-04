package com.giraffe.media.series.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.giraffe.media.series.datasource.local.cacheDto.SeasonCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.SeriesCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.SeriesGenreCacheDto
import com.giraffe.media.utils.DatabaseConstants.SEASON_TABLE
import com.giraffe.media.utils.DatabaseConstants.SERIES_GENRE_TABLE
import com.giraffe.media.utils.DatabaseConstants.SERIES_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface SeriesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSeries(series: List<SeriesCacheDto>)

    @Upsert
    suspend fun updateSeries(series: SeriesCacheDto)

    @Query("SELECT * FROM $SERIES_TABLE")
    fun getAllSeries(): Flow<List<SeriesCacheDto>>

    @Query("SELECT * FROM $SERIES_TABLE WHERE id IN (:ids)")
    suspend fun getSeriesByIds(ids: List<Int>): List<SeriesCacheDto>

    @Query("DELETE FROM $SERIES_TABLE WHERE isRecent = 0")
    suspend fun clearAllSeries()

    @Query("SELECT * FROM $SERIES_TABLE WHERE LOWER(name) LIKE '%' || LOWER(:keyword) || '%' AND page = :page")
    suspend fun getSeriesByKeyword(keyword: String, page: Int): List<SeriesCacheDto>

    @Query(
        """
        DELETE FROM $SERIES_TABLE 
        WHERE LOWER(name) LIKE '%' || LOWER(:keyword) || '%' 
        AND isRecent = 0
    """
    )
    suspend fun deleteSeriesByKeyword(keyword: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeasons(seasons: List<SeasonCacheDto>)

    @Query("SELECT * FROM $SEASON_TABLE WHERE seriesId = :seriesId")
    fun getSeasonsForSeries(seriesId: Int): List<SeasonCacheDto>

    @Query("DELETE FROM $SEASON_TABLE")
    suspend fun clearAllSeasons()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<SeriesGenreCacheDto>)

    @Query("SELECT * FROM $SERIES_GENRE_TABLE  ORDER BY count DESC")
    suspend fun getAllGenres(): List<SeriesGenreCacheDto>

    @Query("SELECT * FROM $SERIES_GENRE_TABLE  WHERE id IN (:genreIds)")
    suspend fun getGenresByIds(genreIds: List<Int>): List<SeriesGenreCacheDto>

    @Query("DELETE FROM $SERIES_GENRE_TABLE")
    suspend fun clearAllGenres()

    @Query("UPDATE $SERIES_TABLE SET isRecent = 1 WHERE id = :seriesId")
    suspend fun markSeriesAsViewed(seriesId: Int)

    @Query("UPDATE $SERIES_TABLE SET isRecent = 0")
    suspend fun clearRecentSeries()

    @Query("SELECT * FROM $SERIES_TABLE WHERE isRecent = 1")
    fun getRecentSeries(): Flow<List<SeriesCacheDto>>

    @Query(
        """
    DELETE FROM $SERIES_TABLE 
    WHERE isRecent = 0 
    AND cachedAt <= :currentTime - 3600000
"""
    )
    suspend fun clearSeriesCache(currentTime: Long)

    @Query("UPDATE series_genre SET count = count + 1 WHERE id IN (:genreIds)")
    suspend fun incrementInteractionCountForGenres(genreIds: List<Int>)

    @Query("SELECT * FROM $SERIES_TABLE WHERE isPopularity = 1 ORDER BY popularity DESC LIMIT :limit")
    fun getPopularitySeries(limit: Int): List<SeriesCacheDto>

    @Query("SELECT * FROM $SERIES_TABLE ORDER BY recentlyReleased DESC LIMIT :limit")
    fun getRecentlyReleasedSeries(limit: Int): List<SeriesCacheDto>

    @Query("SELECT * FROM $SERIES_TABLE WHERE isTopRated = 1 ORDER BY rate DESC LIMIT :limit")
    fun getTopRatedSeries(limit: Int): List<SeriesCacheDto>

    @Upsert
    suspend fun upsertSeries(series: List<SeriesCacheDto>)
}