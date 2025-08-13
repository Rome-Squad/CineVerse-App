package com.giraffe.media.movie.datasource.local

import com.giraffe.media.movie.datasource.local.cacheDto.MovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.MovieGenreCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.MovieWithRecentlyViewedAt
import com.giraffe.media.movie.datasource.local.cacheDto.PopularMovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.RecentReleasedMovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.RecentlyViewedMovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.UpcomingMovieCacheDto
import kotlinx.coroutines.flow.Flow

interface MoviesLocalDataSource {
    suspend fun addMovies(movies: List<MovieCacheDto>)

    suspend fun addMovie(movie: MovieCacheDto)

    suspend fun addMovieGenres(movieGenres: List<MovieGenreCacheDto>)

    suspend fun incrementInteractionCountForGenres(genreIds: List<Int>)

    suspend fun getMovieGenresByIds(ids: List<Int>): List<MovieGenreCacheDto>

    suspend fun getMoviesGenres(): List<MovieGenreCacheDto>

    suspend fun getTopGenre(): MovieGenreCacheDto?

    suspend fun clearMovieGenres()

    suspend fun addPopularityMovies(movies: List<PopularMovieCacheDto>)

    suspend fun getPopularityMovies(limit: Int): List<MovieCacheDto>

    suspend fun addRecentlyReleasedMovies(movies: List<RecentReleasedMovieCacheDto>)

    suspend fun getRecentlyReleasedMovies(limit: Int): List<MovieCacheDto>

    suspend fun addUpcomingMovies(movies: List<UpcomingMovieCacheDto>)

    suspend fun getUpcomingMovies(limit: Int): List<MovieCacheDto>

    suspend fun addRecentlyViewedMovie(movie: RecentlyViewedMovieCacheDto)

    fun getRecentlyViewedMovies(): Flow<List<MovieWithRecentlyViewedAt>>

    suspend fun clearMovieCache()

    suspend fun clearExceptRecentlyViewed()

    suspend fun clearRecentlyViewedMovies()

    suspend fun deleteMovieById(movieId: Int)
}