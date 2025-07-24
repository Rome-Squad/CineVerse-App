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


@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movies: List<MovieCacheDto>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieGenres(movies: List<MovieGenreCacheDto>)

    @Query("SELECT * FROM $MOVIE_TABLE WHERE title LIKE '%' || :movieName || '%'")
    suspend fun getMovieByName(movieName: String): List<MovieCacheDto>

    @Query("SELECT * FROM $MOVIE_TABLE WHERE id =:movieId")
    suspend fun getMovieById(movieId: Int): MovieCacheDto?

    @Query("SELECT * FROM $MOVIE_GENRE_TABLE WHERE id IN (:ids)")
    suspend fun getMovieGenresByIds(ids: List<Int>): List<MovieGenreCacheDto>

    @Query("SELECT * FROM $MOVIE_GENRE_TABLE WHERE ID =:id")
    suspend fun getMovieGenreById(id: Int): MovieGenreCacheDto

    @Query("SELECT * FROM $MOVIE_GENRE_TABLE ORDER BY count DESC")
    suspend fun getMoviesGenres(): List<MovieGenreCacheDto>

    @Query("SELECT * FROM $MOVIE_GENRE_TABLE WHERE id IN (:genreIds)")
    suspend fun getMovieGenres(genreIds: List<Int>): List<MovieGenreCacheDto>

    @Query("SELECT * FROM $MOVIE_TABLE WHERE genresID =:genreId")
    suspend fun getMoviesByGenre(genreId: Int): List<MovieCacheDto>

    @Query("DELETE FROM $MOVIE_TABLE")
    suspend fun clearMovieCache()

    @Query("DELETE FROM $MOVIE_GENRE_TABLE")
    suspend fun clearMovieGenreCache()

    @Query("DELETE FROM $MOVIE_TABLE WHERE isRecent = 1")
    suspend fun clearRecentlyMovies()

    @Query("SELECT * FROM $MOVIE_TABLE WHERE isRecent = 1")
    suspend fun getRecentlyMovies(): List<MovieCacheDto>

    @Upsert
    suspend fun updateMovie(movie: MovieCacheDto)

    @Query(
        """
    DELETE FROM $MOVIE_TABLE 
    WHERE isRecent = 0 
    AND cachedAt <= :currentTime - 3600000
"""
    )
    suspend fun clearMovieCache(currentTime: Long)

    @Query("UPDATE movie_genre SET count = count + 1 WHERE id IN (:genreIds)")
    suspend fun incrementInteractionCountForGenres(genreIds: List<Int>)

}