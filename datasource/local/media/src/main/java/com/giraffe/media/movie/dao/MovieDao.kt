package com.giraffe.media.movie.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.giraffe.media.movie.datasource.local.cacheDto.MovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.MovieGenreCacheDto
import com.giraffe.media.utils.DatabaseConstants.MOVIE_GENRE_TABLE
import com.giraffe.media.utils.DatabaseConstants.MOVIE_TABLE
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieGenres(movies: List<MovieGenreCacheDto>)

    @Upsert
    suspend fun upsertMovies(movies: List<MovieCacheDto>)

    @Upsert
    suspend fun upsertMovie(movie: MovieCacheDto)

    @Query("UPDATE movie_genre SET count = count + 1 WHERE id IN (:genreIds)")
    suspend fun incrementInteractionCountForGenres(genreIds: List<Int>)


    @Query("SELECT * FROM $MOVIE_TABLE WHERE id IN (:ids)")
    suspend fun getMoviesByIds(ids: List<Int>): List<MovieCacheDto>

    @Query("SELECT * FROM $MOVIE_TABLE WHERE id =:movieId")
    suspend fun getMovieById(movieId: Int): MovieCacheDto?

    @Query("SELECT * FROM $MOVIE_TABLE WHERE title LIKE '%' || :movieName || '%'")
    suspend fun getMovieByName(movieName: String): List<MovieCacheDto>

    @Query("SELECT * FROM $MOVIE_TABLE ORDER BY popularity DESC LIMIT :limit")
    suspend fun getPopularityMovies(limit: Int): List<MovieCacheDto>

    @Query("SELECT * FROM $MOVIE_TABLE WHERE recentReleasedAt IS NOT NULL AND recentReleasedAt > 0 ORDER BY recentReleasedAt DESC LIMIT :limit")
    suspend fun getRecentlyReleasedMovies(limit: Int): List<MovieCacheDto>

    @Query("SELECT * FROM $MOVIE_TABLE WHERE recommendedId IS NOT NULL AND recommendedId = :movieId LIMIT :limit")
    suspend fun getRecommendedMovies(movieId: Int, limit: Int): List<MovieCacheDto>

    @Query("SELECT * FROM $MOVIE_GENRE_TABLE WHERE id IN (:ids)")
    suspend fun getMovieGenresByIds(ids: List<Int>): List<MovieGenreCacheDto>

    @Query("SELECT * FROM $MOVIE_GENRE_TABLE WHERE ID =:id")
    suspend fun getMovieGenreById(id: Int): MovieGenreCacheDto

    @Query("SELECT * FROM $MOVIE_GENRE_TABLE ORDER BY count DESC")
    suspend fun getMoviesGenres(): List<MovieGenreCacheDto>

    @Query("SELECT * FROM $MOVIE_TABLE WHERE genresID =:genreId")
    suspend fun getMoviesByGenre(genreId: Int): List<MovieCacheDto>

    @Query("SELECT * FROM $MOVIE_TABLE WHERE upcomingAt IS NOT NULL AND upcomingAt > 0 ORDER BY upcomingAt DESC LIMIT :limit")
    fun getUpcomingMovies(limit: Int): List<MovieCacheDto>

    @Query("SELECT * FROM $MOVIE_TABLE WHERE recentViewedAt > 0 ORDER BY recentReleasedAt DESC")
    fun getRecentlyViewedMovies(): Flow<List<MovieCacheDto>>

    @Query("DELETE FROM $MOVIE_TABLE")
    suspend fun clearMovieCache()

    @Query("DELETE FROM $MOVIE_GENRE_TABLE")
    suspend fun clearMovieGenreCache()

    @Query("DELETE FROM $MOVIE_TABLE WHERE recentReleasedAt > 0")
    suspend fun clearRecentlyMovies()

    @Query(
        """
    DELETE FROM $MOVIE_TABLE 
    WHERE recentReleasedAt = 0 
    AND cachedAt <= :currentTime - 3600000
"""
    )
    suspend fun clearMovieCache(currentTime: Long)

}