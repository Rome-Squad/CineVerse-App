package com.giraffe.movie

import com.giraffe.movie.datasource.local.MoviesLocalDataSource
import com.giraffe.movie.datasource.local.MoviesSearchHistoryDataSource
import com.giraffe.movie.datasource.remote.MoviesRemoteDataSource
import com.giraffe.movie.mapper.toEntity
import com.giraffe.movie.mapper.toMovie
import com.giraffe.movie.mapper.toMovieCacheDto
import com.giraffe.movie.mapper.toMovieGenre
import com.giraffe.movie.mapper.toMovieGenreDto
import com.giraffe.movie.utils.safeCall
import com.giraffe.movies.entity.Movie
import com.giraffe.movies.entity.MovieGenre
import com.giraffe.movies.repository.MoviesRepository

class MoviesRepositoryImpl(
    private val cache: MoviesLocalDataSource,
    private val remote: MoviesRemoteDataSource,
    private val searchHistory: MoviesSearchHistoryDataSource
) : MoviesRepository {
    override suspend fun searchMovieByName(movieName: String): List<Movie> {
        return safeCall {
            val lastHourKeywords = searchHistory.getLastHourSearchHistory()
            val isSearchedWithinLastHour = lastHourKeywords.any { it.keyword == movieName }

            val cachedMovies = cache.getMoviesByName(movieName).map { it.toMovie() }
            val isCached = cachedMovies.isNotEmpty()

            if (!(isSearchedWithinLastHour && isCached)) {
                val remoteMovies = remote.getMovieByName(movieName).map { it.toMovie() }
                val distinctMovies = (remoteMovies + cachedMovies).distinct()

                insertMovies(distinctMovies)

                return distinctMovies
            }

            return cachedMovies

        }
    }

    override suspend fun getMovieGenres(): List<MovieGenre> {
        return safeCall {
            val cachedMovieGenres = cache.getMovieGenres().map { it.toMovieGenre() }
            val isCached = cachedMovieGenres.isNotEmpty()
            if (!isCached) {
                val remoteMovieGenres = remote.getMovieGenres().map { it.toEntity() }

                insertGenres(remoteMovieGenres)

                return remoteMovieGenres
            }

            return cachedMovieGenres
        }

    }

    override suspend fun getMoviesByGenre(genreId: Int): List<Movie> {
        return safeCall {

            val cachedMovies = cache.getMoviesByGenre(genreId).map { it.toMovie() }
            val isCached = cachedMovies.isNotEmpty()

            if (!isCached) {
                val remoteMovies = remote.getMoviesByGenre(genreId).map { it.toMovie() }

                insertMovies(remoteMovies)

                return (remoteMovies + cachedMovies).distinct()
            }

            return cachedMovies

        }
    }

    override suspend fun insertMovies(movie: List<Movie>) {
        cache.insertMovies(
            movie.map {
                it.toMovieCacheDto()
            }
        )
    }

    override suspend fun insertGenres(genres: List<MovieGenre>) {
        cache.insertMovieGenres(
            genres.map {
                it.toMovieGenreDto()
            }
        )
    }

    override suspend fun clearCache() {
        cache.clearMovieCache()
    }

}