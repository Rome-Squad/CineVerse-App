package com.giraffe.movie

import android.util.Log
import com.giraffe.movie.datasource.local.MoviesLocalDataSource
import com.giraffe.movie.datasource.local.MoviesSearchHistoryDataSource
import com.giraffe.movie.datasource.remote.MoviesRemoteDataSource
import com.giraffe.movie.mapper.toEntity
import com.giraffe.movie.mapper.toMovie
import com.giraffe.movie.mapper.toMovieCacheDto
import com.giraffe.movie.mapper.toMovieGenreDto
import com.giraffe.movie.utils.safeCall
import com.giraffe.movies.entity.Movie
import com.giraffe.movies.entity.MovieGenre
import com.giraffe.movies.entity.MovieReview
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
                Log.d("fix", "searchMovieByName: here")
                val remoteMovies = remote.getMoviesByName(movieName).map { it.toMovie() }
                val distinctMovies = (remoteMovies + cachedMovies).distinct()

                insertMovies(distinctMovies)

                return distinctMovies
            }

            return cachedMovies

        }
    }

    override suspend fun getMovieGenres(genreIds: List<Int>): List<MovieGenre> {
        return safeCall {
            cache.getMovieGenres(genreIds).map { it.toEntity() }.ifEmpty {
                remote.getMovieGenres().map { it.toEntity() }
            }
        }
    }



    override suspend fun getMoviesGenres(): List<MovieGenre> {
        return safeCall {
            val cachedMovieGenres = cache.getMoviesGenres().map { it.toEntity() }
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

    override suspend fun setMovieRecent(
        movie: Movie,
        isRecent: Boolean
    ) {
        cache.updateMovie(
            movie = movie.toMovieCacheDto().copy(isRecent = isRecent)
        )
    }

    override suspend fun clearCache() {
        cache.clearMovieCache()
    }

    override suspend fun getRecentlyMovies(): List<Movie> {
        return safeCall {
             cache.getRecentlyMovies().map { it.toMovie() }
        }
    }

    override suspend fun clearRecentlyMovies() {
        safeCall {
            cache.clearRecentlyMovies()
        }
    }

    override suspend fun getMovieDetails(movieId: Int): Movie {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieReviews(
        movieId: Int,
        pageNumber: Int,
        pageSize: Int
    ): List<MovieReview> {
        TODO("Not yet implemented")
    }


    override suspend fun addMovieReview(review: MovieReview) {
        TODO("Not yet implemented")
    }
}