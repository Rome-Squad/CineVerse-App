
package com.giraffe.movie

import MovieDao
import com.giraffe.movie.datasource.local.cacheDto.MovieCacheDto
import com.giraffe.movie.datasource.local.cacheDto.MovieGenreCacheDto
import com.giraffe.movie.datasource.local.MoviesLocalDataSource


class MovieLocalDataSourceImp(
    private val movieDao : MovieDao
) : MoviesLocalDataSource {

    override suspend fun getMovieById(movieId: Int): MovieCacheDto {
        return movieDao.getMovieById(movieId)
    }

    override suspend fun insertMovies(movies: List<MovieCacheDto>) {
         movieDao.insertMovies(movies)
    }

    override suspend fun insertMovieGenres(movieGenres: List<MovieGenreCacheDto>) {
        movieDao.insertMovieGenres(movieGenres)
    }

    override suspend fun getMoviesByName(movieName: String): List<MovieCacheDto> {
        return movieDao.getMovieByName(movieName)
    }

    override suspend fun getMoviesByGenre(genreId: Int): List<MovieCacheDto> {
        return movieDao.getMoviesByGenre(genreId)
    }

    override suspend fun getMovieGenres(): List<MovieGenreCacheDto> {
        return movieDao.getMovieGenres()
    }

    override suspend fun getMovieGenreById(genreId: Int): MovieGenreCacheDto {
        return movieDao.getMovieGenreById(genreId)
    }

    override suspend fun getMovieGenresById(ids: List<Int>): List<MovieGenreCacheDto> {
        return movieDao.getMovieGenresByIds(ids)
    }

    override suspend fun clearMovieCache() {
        movieDao.clearMovieCache()
    }

    override suspend fun clearMovieGenreCache() {
        movieDao.clearMovieGenreCache()
    }
}

