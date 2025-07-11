package com.giraffe.series.database

import androidx.room.*
import com.giraffe.series.model.*

import kotlinx.coroutines.flow.Flow


@Dao
interface SeriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeries(series: List<CachedSeriesDto>)

    @Query("SELECT * FROM cached_series")
    fun getAllSeries(): Flow<List<CachedSeriesDto>>

    @Query("SELECT * FROM cached_series WHERE id IN (:ids)")
    suspend fun getSeriesByIds(ids: List<Int>): List<CachedSeriesDto>

    @Query("DELETE FROM cached_series WHERE isRecent = 0")
    suspend fun clearAllSeries()

    @Query("SELECT * FROM cached_series WHERE LOWER(name) LIKE '%' || LOWER(:keyword) || '%'")
    suspend fun getSeriesByKeyword(keyword: String): List<CachedSeriesDto>

    @Query("""
    DELETE FROM cached_series 
    WHERE LOWER(name) LIKE '%' || LOWER(:keyword) || '%' 
    AND isRecent = 0
""")
    suspend fun deleteSeriesByKeyword(keyword: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeasons(seasons: List<CachedSeasonDto>)

    @Query("SELECT * FROM cached_season WHERE seriesId = :seriesId")
    fun getSeasonsForSeries(seriesId: Int): Flow<List<CachedSeasonDto>>


    @Query("DELETE FROM cached_season")
    suspend fun clearAllSeasons()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<CachedSeriesGenreDto>)

    @Query("SELECT * FROM cached_series_genres")
    fun getAllGenres(): Flow<List<CachedSeriesGenreDto>>

    @Query("DELETE FROM cached_series_genres")
    suspend fun clearAllGenres()

    @Query("UPDATE cached_series SET isRecent = 1 WHERE id = :seriesId")
    suspend fun markSeriesAsViewed(seriesId: Int)

    @Query("UPDATE cached_series SET isRecent = 0")
    suspend fun clearRecentSeries()

    @Query("SELECT * FROM cached_series WHERE isRecent = 1")
    suspend fun getRecentSeries(): List<CachedSeriesDto>


}