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

    // region Popular
    @Upsert
    suspend fun upsertPopularSeriesIDs(seriesIDs: List<PopularSeriesCacheDto>)

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

    @Query("DELETE FROM $POPULAR_SERIES_TABLE")
    suspend fun clearPopularSeriesTable()
    // endregion

    // region Recently Released
    @Upsert
    suspend fun upsertRecentlyReleasedSeriesIDs(seriesIDs: List<RecentlyReleasedSeriesCacheDto>)

    @Query(
        """
        SELECT * 
        FROM $SERIES_TABLE
        WHERE id IN (SELECT id FROM $RECENTLY_RELEASED_SERIES_TABLE)
        LIMIT :limit
        """
    )
    fun getRecentlyReleasedSeries(limit: Int): List<SeriesCacheDto>

    @Query("DELETE FROM $RECENTLY_RELEASED_SERIES_TABLE")
    suspend fun clearRecentlyReleasedSeriesTable()
    // endregion

    // region Top Rated
    @Upsert
    suspend fun upsertTopRatedSeriesIDs(seriesIDs: List<TopRatedSeriesCacheDto>)

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

    @Query("DELETE FROM $TOP_RATED_SERIES_TABLE")
    suspend fun clearTopRatedSeriesTable()
    // endregion

    // region Matches Your Vibe
    @Upsert
    suspend fun upsertMatchesYourVibeSeries(seriesIDs: List<MatchesYourVibeSeriesCacheDto>)

    @Query(
        """
        SELECT * 
        FROM $SERIES_TABLE
        WHERE id IN (SELECT id FROM $MATCHES_YOUR_VIBE_SERIES_TABLE)
        LIMIT :limit
        """
    )
    fun getMatchesYourVibeSeries(limit: Int): List<SeriesCacheDto>

    @Query("DELETE FROM $MATCHES_YOUR_VIBE_SERIES_TABLE")
    suspend fun clearMatchesYourVibeSeriesTable()
    // endregion

    // region Recent Viewed
    @Upsert
    suspend fun upsertRecentViewedSeries(series: RecentViewedSeriesCacheDto)

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
            ORDER BY recentViewedAt DESC
        LIMIT :pageSize OFFSET (:page - 1) * :pageSize
        """
    )
    fun getRecentlyViewedSeries(page: Int, pageSize: Int): Flow<List<SeriesCacheDto>>

    @Query("DELETE FROM $RECENT_VIEWED_SERIES_TABLE WHERE id = :seriesId")
    suspend fun deleteRecentlyViewedSeriesById(seriesId: Int)

    @Query("DELETE FROM $RECENT_VIEWED_SERIES_TABLE")
    suspend fun clearRecentlyViewedSeries()
    // endregion

    // region Genres
    @Upsert
    suspend fun upsertGenres(genres: List<SeriesGenreCacheDto>)

    @Query("UPDATE $SERIES_GENRE_TABLE SET name = :name WHERE id = :id")
    suspend fun updateGenreNameOnly(id: Int, name: String)

    @Query("UPDATE $SERIES_GENRE_TABLE SET count = count + 1 WHERE id IN (:genreIds)")
    suspend fun incrementInteractionCountForGenres(genreIds: List<Int>)

    @Query("SELECT * FROM $SERIES_GENRE_TABLE  WHERE id IN (:genreIds)")
    fun getGenresByIds(genreIds: List<Int>): Flow<List<SeriesGenreCacheDto>>

    @Query("SELECT * FROM $SERIES_GENRE_TABLE WHERE id = :id")
    suspend fun getGenreById(id: Int): SeriesGenreCacheDto?

    @Query("SELECT * FROM $SERIES_GENRE_TABLE  ORDER BY count DESC")
    fun getGenres(): Flow<List<SeriesGenreCacheDto>>

    @Query("SELECT * FROM $SERIES_GENRE_TABLE WHERE count > 0 ORDER BY count DESC LIMIT 1")
    suspend fun getTopGenreCount(): SeriesGenreCacheDto?

    @Query("DELETE FROM $SERIES_GENRE_TABLE")
    suspend fun clearGenres()
    // endregion

    @Query(
        """
        DELETE FROM $SERIES_TABLE 
        WHERE id NOT IN (SELECT id FROM $RECENT_VIEWED_SERIES_TABLE)
        """
    )
    suspend fun clearSeriesExceptRecentViewed()

}