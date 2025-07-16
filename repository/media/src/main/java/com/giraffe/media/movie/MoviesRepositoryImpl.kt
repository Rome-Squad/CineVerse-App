package com.giraffe.media.movie

import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.entity.MovieGenre
import com.giraffe.media.movies.entity.MovieReview
import com.giraffe.media.movies.exception.NetworkError
import com.giraffe.media.movies.repository.MoviesRepository
import  com.giraffe.media.movie.datasource.local.MoviesLocalDataSource
import  com.giraffe.media.movie.datasource.remote.MoviesRemoteDataSource
import  com.giraffe.media.movie.model.dto.RatingRequest
import  com.giraffe.media.movie.mapper.toEntity
import  com.giraffe.media.movie.mapper.toMovie
import  com.giraffe.media.movie.mapper.toMovieCacheDto
import  com.giraffe.media.movie.mapper.toMovieGenreDto
import  com.giraffe.media.utils.safeCall
import com.giraffe.user.SessionManager

class MoviesRepositoryImpl(
    private val cache: MoviesLocalDataSource,
    private val remote: MoviesRemoteDataSource,
    private val sessionManager: SessionManager
) : MoviesRepository {

    override suspend fun searchMovieByName(movieName: String): List<Movie> {
        return safeCall {

            return safeCall {
                val cachedMovies = cache.getMoviesByName(movieName).map { it.toMovie() }
                val isCached = cachedMovies.isNotEmpty()

                if (!isCached) {
                    val remoteMovies =
                        remote.getMoviesByName(movieName).map { it.toMovie() }
                    insertMovies(remoteMovies)
                    return remoteMovies
                }

                return cachedMovies
            }
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
        return safeCall {
            val cachedMovie = cache.getMovieById(movieId)
            if (cachedMovie != null) {
                return cachedMovie.toMovie()
            } else {
                val remoteMovieDetails = remote.getMovieById(movieId)
                val remoteEntity = remoteMovieDetails.toEntity()
                insertMovies(listOf(remoteEntity))
                return remoteEntity
            }
        }
    }

    override suspend fun getMovieReviews(
        movieId: Int
    ): List<MovieReview> {
        return safeCall {
            remote.getMovieReviews(movieId).map { it.toEntity() }
        }
    }

    override suspend fun addRating(
        movieId: Int,
        ratingValue: Float
    ) {
        return safeCall {
            val sessionId = getSessionId()

            val requestBody = RatingRequest(value = ratingValue)
            remote.addRating(movieId, sessionId, requestBody)
        }
    }


    override suspend fun getUserMovieRating(movieId: Int): Float {
        return safeCall {
            val sessionId = getSessionId()
            remote.getUserMovieRating(movieId, sessionId)
        }
    }

    private suspend fun getSessionId(): String {
        return safeCall {
            sessionManager.createGuestSessionId() ?: throw NetworkError()
        }
    }
}