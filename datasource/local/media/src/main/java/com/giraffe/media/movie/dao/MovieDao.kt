package com.giraffe.media.movie.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.giraffe.media.movie.datasource.local.cacheDto.MovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.MovieGenreCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.MovieWithRecentlyViewedAt
import com.giraffe.media.movie.datasource.local.cacheDto.PopularMovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.RecentReleasedMovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.RecentlyViewedMovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.UpcomingMovieCacheDto
import com.giraffe.media.utils.DatabaseConstants.MOVIE_GENRE_TABLE
import com.giraffe.media.utils.DatabaseConstants.MOVIE_TABLE
import com.giraffe.media.utils.DatabaseConstants.POPULAR_MOVIE_TABLE
import com.giraffe.media.utils.DatabaseConstants.RECENTLY_RELEASED_MOVIE_TABLE
import com.giraffe.media.utils.DatabaseConstants.RECENTLY_VIEWED_MOVIE_TABLE
import com.giraffe.media.utils.DatabaseConstants.UPCOMING_MOVIE_TABLE
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieCacheDto>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieCacheDto)

    // region Popular
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularMovies(movies: List<PopularMovieCacheDto>)

    @Query(
        """
        SELECT m.* FROM $MOVIE_TABLE m
        INNER JOIN $POPULAR_MOVIE_TABLE p ON m.id = p.id
        ORDER BY m.popularity DESC
        LIMIT :limit
        """
    )
    suspend fun getPopularityMovies(limit: Int): List<MovieCacheDto>

    // endregion

    // region Recently Released
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentlyReleasedMovies(movies: List<RecentReleasedMovieCacheDto>)

    @Query(
        """
        SELECT m.* FROM $MOVIE_TABLE m
        INNER JOIN $RECENTLY_RELEASED_MOVIE_TABLE r ON m.id = r.id
        ORDER BY r.createdAt DESC
        LIMIT :limit
        """
    )
    suspend fun getRecentlyReleasedMovies(limit: Int): List<MovieCacheDto>

    // endregion

    // region Upcoming
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpcomingMovies(movies: List<UpcomingMovieCacheDto>)

    @Query(
        """
        SELECT m.* FROM $MOVIE_TABLE m
        INNER JOIN $UPCOMING_MOVIE_TABLE u ON m.id = u.id
        ORDER BY u.createdAt DESC
        LIMIT :limit
        """
    )
    suspend fun getUpcomingMovies(limit: Int): List<MovieCacheDto>

    // endregion

    //region Recently Viewed
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentlyViewedMovie(movies: RecentlyViewedMovieCacheDto)

    @Query(
        """
        SELECT m.*, r.createdAt AS recentViewedAt
        FROM $MOVIE_TABLE AS m
        INNER JOIN $RECENTLY_VIEWED_MOVIE_TABLE AS r 
        ON m.id = r.id
        ORDER BY r.createdAt DESC
        """
    )
    fun getRecentlyViewedMovies(): Flow<List<MovieWithRecentlyViewedAt>>
    //endregion

    // region Movie Genres
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieGenres(movies: List<MovieGenreCacheDto>)

    @Query("UPDATE movie_genre SET rank = rank + 1 WHERE id IN (:genreIds)")
    suspend fun incrementInteractionCountForGenres(genreIds: List<Int>)

    @Query("SELECT * FROM $MOVIE_GENRE_TABLE WHERE id IN (:ids)")
    suspend fun getMovieGenresByIds(ids: List<Int>): List<MovieGenreCacheDto>

    @Query("SELECT * FROM $MOVIE_GENRE_TABLE ORDER BY rank DESC")
    suspend fun getMoviesGenres(): List<MovieGenreCacheDto>

    @Query("SELECT * FROM $MOVIE_GENRE_TABLE ORDER BY rank DESC LIMIT 1")
    suspend fun getTopGenre(): MovieGenreCacheDto?

    @Query("DELETE FROM $MOVIE_GENRE_TABLE")
    suspend fun clearMovieGenres()
    // endregion

    @Query("DELETE FROM $MOVIE_TABLE WHERE id = :movieId")
    suspend fun deleteMovieById(movieId: Int)

    @Query("SELECT * FROM $MOVIE_TABLE WHERE id IN (:ids)")
    suspend fun getMoviesByIds(ids: List<Int>): List<MovieCacheDto>

    @Query("SELECT * FROM $MOVIE_TABLE WHERE id =:movieId")
    suspend fun getMovieById(movieId: Int): MovieCacheDto?

    @Query("SELECT * FROM $MOVIE_TABLE WHERE title LIKE '%' || :movieName || '%'")
    suspend fun getMovieByName(movieName: String): List<MovieCacheDto>

    @Query("DELETE FROM $MOVIE_TABLE")
    suspend fun clearMovieCache()

    @Query(
        """
        DELETE FROM $MOVIE_TABLE
        WHERE id NOT IN (
            SELECT id FROM $RECENTLY_VIEWED_MOVIE_TABLE
        )
        """
    )
    suspend fun clearExceptRecentlyViewed()

    @Query("DELETE FROM $RECENTLY_VIEWED_MOVIE_TABLE")
    suspend fun clearRecentlyViewedMovies()
}