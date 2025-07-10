package com.giraffe.movie.datasource.local

import com.giraffe.movie.datasource.local.cacheDto.MovieCacheDto
import com.giraffe.movie.datasource.local.cacheDto.MovieGenreCacheDto

interface MoviesLocalDataSource {
    suspend fun getMovieById(
        movieId: Int
    ): MovieCacheDto

    suspend fun insertMovies(movies: List<MovieCacheDto>)

    suspend fun insertMovieGenres(movies: List<MovieGenreCacheDto>)

    suspend fun getMoviesByName(movieName: String): List<MovieCacheDto>

    suspend fun getMoviesByGenre(genreId: Int): List<MovieCacheDto>

    suspend fun getMovieGenres(): List<MovieGenreCacheDto>

    suspend fun getMovieGenreById(genreId: Int): MovieGenreCacheDto

    suspend fun getMovieGenresById(ids : List<Int>) : List<MovieGenreCacheDto>

    suspend fun clearCache()


}