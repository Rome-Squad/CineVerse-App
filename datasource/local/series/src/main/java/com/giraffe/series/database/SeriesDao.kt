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

    @Query("DELETE FROM cached_series")
    suspend fun clearAllSeries()

    @Query("DELETE FROM cached_series WHERE id IN (:ids)")
    suspend fun deleteSeriesByIds(ids: List<Int>)

    @Query("SELECT * FROM cached_series WHERE LOWER(name) LIKE '%' || LOWER(:keyword) || '%'")
    suspend fun getSeriesByKeyword(keyword: String): List<CachedSeriesDto>

    @Query("DELETE FROM cached_series WHERE LOWER(name) LIKE '%' || LOWER(:keyword) || '%'")
    suspend fun deleteSeriesByKeyword(keyword: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeasons(seasons: List<CachedSeasonDto>)

    @Query("SELECT * FROM cached_season WHERE seriesId = :seriesId")
    fun getSeasonsForSeries(seriesId: Int): Flow<List<CachedSeasonDto>>

    @Query("SELECT * FROM cached_series")
    suspend fun getAllSeriesDirect(): List<CachedSeriesDto>

    @Query("DELETE FROM cached_season")
    suspend fun clearAllSeasons()

    @Query("DELETE FROM cached_season WHERE seriesId IN (:seriesIds)")
    suspend fun deleteSeasonsBySeriesIds(seriesIds: List<Int>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<CachedSeriesGenreDto>)

    @Query("SELECT * FROM cached_series_genres")
    fun getAllGenres(): Flow<List<CachedSeriesGenreDto>>

    @Query("DELETE FROM cached_series_genres")
    suspend fun clearAllGenres()

    @Query("DELETE FROM cached_series_genres WHERE id IN (:ids)")
    suspend fun deleteGenresByIds(ids: List<Int>)
}