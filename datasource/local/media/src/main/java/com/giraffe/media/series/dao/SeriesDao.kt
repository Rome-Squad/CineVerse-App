package com.giraffe.media.series.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.giraffe.media.series.datasource.local.cacheDto.MatchesYourVibeSeriesCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.PopularSeriesCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.RecentViewedSeriesCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.RecentlyReleasedSeriesCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.SeriesCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.SeriesGenreCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.TopRatedSeriesCacheDto
import com.giraffe.media.utils.DatabaseConstants.MATCHES_YOUR_VIBE_SERIES_TABLE
import com.giraffe.media.utils.DatabaseConstants.POPULAR_SERIES_TABLE
import com.giraffe.media.utils.DatabaseConstants.RECENTLY_RELEASED_SERIES_TABLE
import com.giraffe.media.utils.DatabaseConstants.RECENT_VIEWED_SERIES_TABLE
import com.giraffe.media.utils.DatabaseConstants.SERIES_GENRE_TABLE
import com.giraffe.media.utils.DatabaseConstants.SERIES_TABLE
import com.giraffe.media.utils.DatabaseConstants.TOP_RATED_SERIES_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface SeriesDao {
    @Upsert
    suspend fun upsertSeries(series: List<SeriesCacheDto>)

    @Upsert
    suspend fun upsertPopularSeriesIDs(seriesIDs: List<PopularSeriesCacheDto>)

    @Upsert
    suspend fun upsertRecentlyReleasedSeriesIDs(seriesIDs: List<RecentlyReleasedSeriesCacheDto>)

    @Upsert
    suspend fun upsertTopRatedSeriesIDs(seriesIDs: List<TopRatedSeriesCacheDto>)

    @Upsert
    suspend fun upsertRecentViewedSeries(series: RecentViewedSeriesCacheDto)

    @Upsert
    suspend fun upsertMatchesYourVibeSeries(seriesIDs: List<MatchesYourVibeSeriesCacheDto>)

    @Upsert
    suspend fun upsertGenres(genres: List<SeriesGenreCacheDto>)

    @Query(
        """
            UPDATE $SERIES_TABLE
            SET recentViewedAt = (
                SELECT r.recentViewedAt 
                FROM $RECENT_VIEWED_SERIES_TABLE r
                WHERE r.id = id
            )
            WHERE id IN (SELECT id FROM $RECENT_VIEWED_SERIES_TABLE)
        """
    )
    suspend fun syncRecentViewedTime()

    @Query(
        """
            SELECT * 
            FROM $SERIES_TABLE
            WHERE id IN (SELECT id FROM $RECENT_VIEWED_SERIES_TABLE)
            ORDER BY recentViewedAt ASC
        LIMIT :pageSize OFFSET (:page - 1) * :pageSize
        """
    )
    fun getRecentSeries(page: Int, pageSize: Int): Flow<List<SeriesCacheDto>>

    @Query(
        """
        SELECT * 
        FROM $SERIES_TABLE
        WHERE id IN (SELECT id FROM $POPULAR_SERIES_TABLE)
        ORDER BY popularity DESC
        LIMIT :limit
        """
    )
    fun getPopularitySeries(limit: Int): List<SeriesCacheDto>

    @Query(
        """
        SELECT * 
        FROM $SERIES_TABLE
        WHERE id IN (SELECT id FROM $RECENTLY_RELEASED_SERIES_TABLE)
        LIMIT :limit
        """
    )
    fun getRecentlyReleasedSeries(limit: Int): List<SeriesCacheDto>

    @Query(
        """
        SELECT * 
        FROM $SERIES_TABLE
        WHERE id IN (SELECT id FROM $TOP_RATED_SERIES_TABLE)
        ORDER BY rate DESC 
        LIMIT :limit
        """
    )
    fun getTopRatedSeries(limit: Int): List<SeriesCacheDto>

    @Query(
        """
        SELECT * 
        FROM $SERIES_TABLE
        WHERE id IN (SELECT id FROM $MATCHES_YOUR_VIBE_SERIES_TABLE)
        LIMIT :limit
        """
    )
    fun getMatchesYourVibeSeries(limit: Int): List<SeriesCacheDto>


    @Query("DELETE FROM $RECENT_VIEWED_SERIES_TABLE WHERE id = :seriesId")
    suspend fun deleteSeriesFromHistoryById(seriesId: Int)

    @Query("SELECT * FROM $SERIES_GENRE_TABLE  ORDER BY count DESC")
    suspend fun getGenres(): List<SeriesGenreCacheDto>

    @Query("SELECT * FROM $SERIES_GENRE_TABLE  WHERE id IN (:genreIds)")
    suspend fun getGenresByIds(genreIds: List<Int>): List<SeriesGenreCacheDto>

    @Query("UPDATE series_genre SET count = count + 1 WHERE id IN (:genreIds)")
    suspend fun incrementInteractionCountForGenres(genreIds: List<Int>)

    @Query("SELECT * FROM $SERIES_GENRE_TABLE WHERE count > 0 ORDER BY count DESC LIMIT 1")
    suspend fun getTopGenreCount(): SeriesGenreCacheDto?


    @Query(
        """
        DELETE FROM $SERIES_TABLE 
        WHERE id NOT IN (SELECT id FROM $RECENT_VIEWED_SERIES_TABLE)
        """
    )
    suspend fun clearSeriesExceptRecentViewed()

    @Query("DELETE FROM $SERIES_GENRE_TABLE")
    suspend fun clearGenres()

    @Query("DELETE FROM $RECENT_VIEWED_SERIES_TABLE")
    suspend fun clearRecentSeries()

    @Query("DELETE FROM $POPULAR_SERIES_TABLE")
    suspend fun clearPopularSeriesTable()

    @Query("DELETE FROM $RECENTLY_RELEASED_SERIES_TABLE")
    suspend fun clearRecentlyReleasedSeriesTable()

    @Query("DELETE FROM $TOP_RATED_SERIES_TABLE")
    suspend fun clearTopRatedSeriesTable()

    @Query("DELETE FROM $MATCHES_YOUR_VIBE_SERIES_TABLE")
    suspend fun clearMatchesYourVibeSeriesTable()
}