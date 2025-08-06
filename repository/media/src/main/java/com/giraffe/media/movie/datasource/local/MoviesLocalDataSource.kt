package com.giraffe.media.movie.datasource.local

import  com.giraffe.media.movie.datasource.local.cacheDto.MovieCacheDto
import  com.giraffe.media.movie.datasource.local.cacheDto.MovieGenreCacheDto
import kotlinx.coroutines.flow.Flow

interface MoviesLocalDataSource {

    suspend fun addMovieGenres(movieGenres: List<MovieGenreCacheDto>)
    suspend fun setMovies(
        movies: List<MovieCacheDto>,
        transformer: ((MovieCacheDto) -> MovieCacheDto)? = null
    )

    suspend fun setMovie(
        movie: MovieCacheDto,
        transformer: ((MovieCacheDto) -> MovieCacheDto)? = null
    )

    suspend fun incrementInteractionCountForGenres(genreIds: List<Int>)

    suspend fun getPopularityMovies(limit: Int): List<MovieCacheDto>

    suspend fun getRecentlyReleasedMovies(limit: Int): List<MovieCacheDto>

    suspend fun getRecommendedMovies(movieId: Int, limit: Int): List<MovieCacheDto>

    suspend fun getMovieGenresByIds(ids: List<Int>): List<MovieGenreCacheDto>

    suspend fun getMoviesGenres(): List<MovieGenreCacheDto>

    suspend fun getUpcomingMovies(limit: Int): List<MovieCacheDto>

    fun getRecentlyViewedMovies(): Flow<List<MovieCacheDto>>

    suspend fun clearMovieCache()

    suspend fun clearMovieCacheWithOutRecentViewed()

    suspend fun clearRecentlyViewedMovies()
}