package com.giraffe.media.series.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.giraffe.media.series.model.CachedSeasonDto
import com.giraffe.media.series.model.CachedSeriesDto
import com.giraffe.media.series.model.CachedSeriesGenreDto
import com.giraffe.media.series.utils.DatabaseConstants.GENRE_TABLE
import com.giraffe.media.series.utils.DatabaseConstants.SEASON_TABLE
import com.giraffe.media.series.utils.DatabaseConstants.SERIES_TABLE
import kotlinx.coroutines.flow.Flow


@Dao
interface SeriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeries(series: List<CachedSeriesDto>)

    @Query("SELECT * FROM $SERIES_TABLE")
    fun getAllSeries(): Flow<List<CachedSeriesDto>>

    @Query("SELECT * FROM $SERIES_TABLE WHERE id IN (:ids)")
    suspend fun getSeriesByIds(ids: List<Int>): List<CachedSeriesDto>

    @Query("DELETE FROM $SERIES_TABLE WHERE isRecent = 0")
    suspend fun clearAllSeries()

    @Query("SELECT * FROM $SERIES_TABLE WHERE LOWER(name) LIKE '%' || LOWER(:keyword) || '%'")
    suspend fun getSeriesByKeyword(keyword: String): List<CachedSeriesDto>

    @Query("""
        DELETE FROM $SERIES_TABLE 
        WHERE LOWER(name) LIKE '%' || LOWER(:keyword) || '%' 
        AND isRecent = 0
    """)
    suspend fun deleteSeriesByKeyword(keyword: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeasons(seasons: List<CachedSeasonDto>)

    @Query("SELECT * FROM $SEASON_TABLE WHERE seriesId = :seriesId")
    fun getSeasonsForSeries(seriesId: Int): List<CachedSeasonDto>

    @Query("DELETE FROM $SEASON_TABLE")
    suspend fun clearAllSeasons()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<CachedSeriesGenreDto>)

    @Query("SELECT * FROM $GENRE_TABLE")
    fun getAllGenres(): List<CachedSeriesGenreDto>

    @Query("DELETE FROM $GENRE_TABLE")
    suspend fun clearAllGenres()

    @Query("UPDATE $SERIES_TABLE SET isRecent = 1 WHERE id = :seriesId")
    suspend fun markSeriesAsViewed(seriesId: Int)

    @Query("UPDATE $SERIES_TABLE SET isRecent = 0")
    suspend fun clearRecentSeries()

    @Query("SELECT * FROM $SERIES_TABLE WHERE isRecent = 1")
    suspend fun getRecentSeries(): List<CachedSeriesDto>

    @Query(
        """
    DELETE FROM $SERIES_TABLE 
    WHERE isRecent = 0 
    AND cachedAt <= :currentTime - 3600000
"""
    )
    suspend fun clearSeriesCache(currentTime: Long)
}