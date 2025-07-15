package com.giraffe.media.movie.datasource.local

import com.giraffe.movie.datasource.local.cacheDto.MovieCacheDto
import com.giraffe.movie.datasource.local.cacheDto.MovieGenreCacheDto

interface MoviesLocalDataSource {
    suspend fun getMovieById(
        movieId: Int
    ): MovieCacheDto?

    suspend fun insertMovies(movies: List<MovieCacheDto>)

    suspend fun insertMovieGenres(movieGenres: List<MovieGenreCacheDto>)

    suspend fun updateMovie(movie: MovieCacheDto)

    suspend fun getMoviesByName(movieName: String): List<MovieCacheDto>

    suspend fun getMoviesByGenre(genreId: Int): List<MovieCacheDto>

    suspend fun getMoviesGenres(): List<MovieGenreCacheDto>

    suspend fun getMovieGenres(genreIds : List<Int>): List<MovieGenreCacheDto>

    suspend fun getMovieGenreById(genreId: Int): MovieGenreCacheDto

    suspend fun getMovieGenresById(ids : List<Int>) : List<MovieGenreCacheDto>

    suspend fun clearMovieCache()

    suspend fun clearRecentlyMovies()

    suspend fun getRecentlyMovies() : List<MovieCacheDto>

    suspend fun clearMovieGenreCache()
}