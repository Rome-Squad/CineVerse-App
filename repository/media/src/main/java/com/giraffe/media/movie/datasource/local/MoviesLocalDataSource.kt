package com.giraffe.media.movie.datasource.local

import  com.giraffe.media.movie.datasource.local.cacheDto.MovieCacheDto
import  com.giraffe.media.movie.datasource.local.cacheDto.MovieGenreCacheDto
import kotlinx.coroutines.flow.Flow

interface MoviesLocalDataSource {

    suspend fun insertMovieGenres(movieGenres: List<MovieGenreCacheDto>)
    suspend fun upsertMovies(
        movies: List<MovieCacheDto>,
        transformer: ((MovieCacheDto) -> MovieCacheDto)? = null
    )

    suspend fun upsertMovie(
        movie: MovieCacheDto,
        transformer: ((MovieCacheDto) -> MovieCacheDto)? = null
    )

    suspend fun incrementInteractionCountForGenres(genreIds: List<Int>)

    suspend fun getMovieById(movieId: Int): MovieCacheDto?

    suspend fun getMoviesByName(movieName: String): List<MovieCacheDto>

    suspend fun getPopularityMovies(limit: Int): List<MovieCacheDto>

    suspend fun getRecentlyReleasedMovies(limit: Int): List<MovieCacheDto>

    suspend fun getRecommendedMovies(movieId: Int, limit: Int): List<MovieCacheDto>

    suspend fun getMovieGenresByIds(ids: List<Int>): List<MovieGenreCacheDto>

    suspend fun getMovieGenreById(genreId: Int): MovieGenreCacheDto

    suspend fun getMoviesByGenre(genreId: Int): List<MovieCacheDto>

    suspend fun getMoviesGenres(): List<MovieGenreCacheDto>

    suspend fun getUpcomingMovies(limit: Int): List<MovieCacheDto>

    fun getRecentlyViewedMovies(): Flow<List<MovieCacheDto>>

    suspend fun clearMovieCache()

    suspend fun clearRecentlyMovies()

    suspend fun clearMovieGenreCache()
}