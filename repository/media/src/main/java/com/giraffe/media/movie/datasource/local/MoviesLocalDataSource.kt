package com.giraffe.media.movie.datasource.local

import com.giraffe.media.movie.datasource.local.cacheDto.MovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.MovieGenreCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.MovieWithRecentlyViewedAt
import kotlinx.coroutines.flow.Flow

interface MoviesLocalDataSource {
    suspend fun addMovie(movie: MovieCacheDto)

    suspend fun syncMovieGenres(movieGenres: List<MovieGenreCacheDto>)

    suspend fun incrementInteractionCountForGenres(genreIds: List<Int>)

    fun getMovieGenresByIds(ids: List<Int>): Flow<List<MovieGenreCacheDto>>

    fun getMoviesGenres(): Flow<List<MovieGenreCacheDto>>

    suspend fun getTopGenre(): MovieGenreCacheDto?

    suspend fun clearMovieGenres()

    suspend fun addPopularityMovies(movies: List<MovieCacheDto>)

    fun getPopularityMovies(limit: Int): Flow<List<MovieCacheDto>>

    suspend fun clearPopularMovies()

    suspend fun addRecentlyReleasedMovies(movies: List<MovieCacheDto>)

    fun getRecentlyReleasedMovies(limit: Int): Flow<List<MovieCacheDto>>

    suspend fun clearRecentlyReleasedMovies()

    suspend fun addUpcomingMovies(movies: List<MovieCacheDto>)

    fun getUpcomingMovies(limit: Int): Flow<List<MovieCacheDto>>

    suspend fun clearUpcomingMovies()

    suspend fun addMatchesYourVibeMovies(movies: List<MovieCacheDto>)

    fun getMatchesYourVibeMovies(limit: Int): Flow<List<MovieCacheDto>>

    suspend fun clearMatchesYourVibeMovies()

    suspend fun addRecentlyViewedMovie(movie: MovieCacheDto)

    fun getRecentlyViewedMovies(page: Int, pageSize: Int): Flow<List<MovieWithRecentlyViewedAt>>

    suspend fun getAllRecentlyViewedMovies(): List<MovieWithRecentlyViewedAt>

    suspend fun deleteRecentlyViewedMovieById(movieId: Int)

    suspend fun clearRecentlyViewedMovies()

    suspend fun clearMovieCache()

    suspend fun clearExceptRecentlyViewed()
}