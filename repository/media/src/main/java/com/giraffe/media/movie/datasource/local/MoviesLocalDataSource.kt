package com.giraffe.media.movie.datasource.local

import com.giraffe.media.movie.datasource.local.cacheDto.MovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.MovieGenreCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.MovieWithRecentlyViewedAt
import kotlinx.coroutines.flow.Flow

interface MoviesLocalDataSource {
    suspend fun addMovieGenres(movieGenres: List<MovieGenreCacheDto>)

    suspend fun incrementInteractionCountForGenres(genreIds: List<Int>)

    suspend fun getMovieGenresByIds(ids: List<Int>): List<MovieGenreCacheDto>

    suspend fun getMoviesGenres(): List<MovieGenreCacheDto>

    suspend fun getTopGenre(): MovieGenreCacheDto?

    suspend fun clearMovieGenres()

    suspend fun addPopularityMovies(movies: List<MovieCacheDto>)

    suspend fun getPopularityMovies(limit: Int): List<MovieCacheDto>

    suspend fun clearPopularMovies()

    suspend fun addRecentlyReleasedMovies(movies: List<MovieCacheDto>)

    suspend fun getRecentlyReleasedMovies(limit: Int): List<MovieCacheDto>

    suspend fun clearRecentlyReleasedMovies()

    suspend fun addUpcomingMovies(movies: List<MovieCacheDto>)

    suspend fun getUpcomingMovies(limit: Int): List<MovieCacheDto>

    suspend fun clearUpcomingMovies()

    suspend fun addMatchesYourVibeMovies(movies: List<MovieCacheDto>)

    suspend fun getMatchesYourVibeMovies(limit: Int): List<MovieCacheDto>

    suspend fun clearMatchesYourVibeMovies()

    suspend fun addRecentlyViewedMovie(movie: MovieCacheDto)

    fun getRecentlyViewedMovies(): Flow<List<MovieWithRecentlyViewedAt>>

    suspend fun deleteRecentlyViewedMovieById(movieId: Int)

    suspend fun clearRecentlyViewedMovies()

    suspend fun clearMovieCache()

    suspend fun clearExceptRecentlyViewed()
}