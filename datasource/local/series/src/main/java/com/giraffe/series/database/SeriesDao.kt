package com.giraffe.series.database

import androidx.room.*
import com.giraffe.series.dto.*

import kotlinx.coroutines.flow.Flow


@Dao
interface SeriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeries(series: List<SeriesEntity>)

    @Query("SELECT * FROM series")
    fun getAllSeries(): Flow<List<SeriesEntity>>

    @Query("DELETE FROM series")
    suspend fun clearAllSeries()

    @Query("DELETE FROM series WHERE id IN (:ids)")
    suspend fun deleteSeriesByIds(ids: List<Int>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeasons(seasons: List<SeasonEntity>)

    @Query("SELECT * FROM seasons WHERE seriesId = :seriesId")
    fun getSeasonsForSeries(seriesId: Int): Flow<List<SeasonEntity>>

    @Query("DELETE FROM seasons")
    suspend fun clearAllSeasons()

    @Query("DELETE FROM seasons WHERE seriesId IN (:seriesIds)")
    suspend fun deleteSeasonsBySeriesIds(seriesIds: List<Int>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<SeriesGenreEntity>)

    @Query("SELECT * FROM series_genres")
    fun getAllGenres(): Flow<List<SeriesGenreEntity>>

    @Query("DELETE FROM series_genres")
    suspend fun clearAllGenres()

    @Query("DELETE FROM series_genres WHERE id IN (:ids)")
    suspend fun deleteGenresByIds(ids: List<Int>)

    @Query("SELECT * FROM series WHERE id IN (:ids)")
    suspend fun getSeriesByIds(ids: List<Int>): List<SeriesEntity>
}

