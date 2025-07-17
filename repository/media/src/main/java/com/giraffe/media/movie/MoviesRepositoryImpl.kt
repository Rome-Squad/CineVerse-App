package com.giraffe.media.movie

import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.movie.datasource.local.MoviesLocalDataSource
import com.giraffe.media.movie.datasource.remote.MoviesRemoteDataSource
import com.giraffe.media.movie.mapper.toEntity
import com.giraffe.media.movie.mapper.toMovie
import com.giraffe.media.movie.mapper.toMovieCacheDto
import com.giraffe.media.movie.mapper.toMovieGenreDto
import com.giraffe.media.movie.model.cacheDto.MovieCacheDto
import com.giraffe.media.movie.model.cacheDto.MovieGenreCacheDto
import com.giraffe.media.movie.model.dto.MovieDto
import com.giraffe.media.movie.model.dto.MovieGenreDto
import com.giraffe.media.movie.model.dto.MovieReviewDto
import com.giraffe.media.movie.model.dto.RatingRequest
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.entity.MovieGenre
import com.giraffe.media.movies.repository.MoviesRepository
import com.giraffe.media.utils.SafeCall
import com.giraffe.user.SessionManager
import com.giraffe.media.entity.Review


class MoviesRepositoryImpl(
    private val cache: MoviesLocalDataSource,
    private val remote: MoviesRemoteDataSource,
    private val sessionManager: SessionManager
) : MoviesRepository {

    override suspend fun searchMovieByName(movieName: String) = SafeCall {
        cache.getMoviesByName(movieName)
            .map(MovieCacheDto::toMovie)
            .ifEmpty {
                val remoteMovies = remote.getMoviesByName(movieName).map(MovieDto::toMovie)
                insertMovies(remoteMovies)
                remoteMovies
            }
    }


    override suspend fun getMovieGenres(genreIds: List<Int>) = SafeCall {
        cache.getMovieGenres(genreIds).map { it.toEntity() }.ifEmpty {
            remote.getMovieGenres().map (MovieGenreDto::toEntity)
        }
    }


    override suspend fun getMoviesGenres() = SafeCall {
        cache.getMoviesGenres()
            .map (MovieGenreCacheDto::toEntity)
            .ifEmpty {
                remote.getMovieGenres()
                    .map (MovieGenreDto::toEntity)
                    .also { insertGenres(it) }
            }
    }

    }

    override suspend fun getMoviesByGenres(genreIds: List<Int>) = SafeCall {
        cache.getMoviesByGenre(0).map (MovieCacheDto::toMovie)
            .ifEmpty {
                val remoteMovies = remote.getMoviesByGenres(genreIds).map ( MovieDto::toMovie)
                insertMovies(remoteMovies)
                remoteMovies
            }
    }

    override suspend fun insertMovies(movie: List<Movie>) = SafeCall {
        cache.insertMovies(movie.map (Movie::toMovieCacheDto))
    }

    override suspend fun insertGenres(genres: List<MovieGenre>) = SafeCall {
        cache.insertMovieGenres(genres.map (MovieGenre::toMovieGenreDto))
    }

    override suspend fun setMovieRecent(movie: Movie, isRecent: Boolean) = SafeCall {
        cache.updateMovie(movie.toMovieCacheDto().copy(isRecent = isRecent))
    }

    override suspend fun clearCache() = SafeCall {
        cache.clearMovieCache()
    }

    override suspend fun getRecentlyMovies() =
        SafeCall { cache.getRecentlyMovies().map(MovieCacheDto::toMovie) }

    override suspend fun clearRecentlyMovies() = SafeCall { cache.clearRecentlyMovies() }

    override suspend fun getMovieDetails(movieId: Int) = SafeCall {
        cache.getMovieById(movieId)?.toMovie() ?: remote.getMovieById(movieId)
            .toEntity()
            .also { insertMovies(listOf(it)) }
    }

    override suspend fun getMovieDetails(movieId: Int): Movie {
        return SafeCall {
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
    ) = SafeCall { remote.getMovieReviews(movieId).map (MovieReviewDto::toEntity) }
    ): List<Review> {
        return SafeCall {
            remote.getMovieReviews(movieId).map { it.toEntity() }
        }
    }

    override suspend fun addRating(
        movieId: Int,
        ratingValue: Float
    ) = SafeCall {
        val sessionId = getSessionId()
        val requestBody = RatingRequest(value = ratingValue)
        remote.addRating(movieId, sessionId, requestBody)
    }

    override suspend fun getUserMovieRating(movieId: Int) = SafeCall {
        val sessionId = getSessionId()
        remote.getUserMovieRating(movieId, sessionId)
    }

    private suspend fun getSessionId() = SafeCall {
        sessionManager.createGuestSessionId() ?: throw NoInternetException()
    }
}