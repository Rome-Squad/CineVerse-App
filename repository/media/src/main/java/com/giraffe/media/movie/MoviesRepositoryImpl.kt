package com.giraffe.media.movie

import com.giraffe.media.entity.Genre
import com.giraffe.media.exception.NoInternetDataException
import com.giraffe.media.movie.datasource.local.MoviesLocalDataSource
import com.giraffe.media.movie.datasource.local.cacheDto.MovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.MovieGenreCacheDto
import com.giraffe.media.movie.datasource.remote.MoviesRemoteDataSource
import com.giraffe.media.movie.datasource.remote.dto.MovieDto
import com.giraffe.media.movie.datasource.remote.dto.MovieGenreDto
import com.giraffe.media.movie.datasource.remote.dto.MovieReviewDto
import com.giraffe.media.movie.datasource.remote.dto.RatingRequest
import com.giraffe.media.movie.mapper.toDto
import com.giraffe.media.movie.mapper.toEntity
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.repository.MoviesRepository
import com.giraffe.media.utils.SafeCall
import com.giraffe.user.SessionManager

class MoviesRepositoryImpl(
    private val cache: MoviesLocalDataSource,
    private val remote: MoviesRemoteDataSource,
    private val sessionManager: SessionManager
) : MoviesRepository {

    override suspend fun searchMovieByName(movieName: String) = SafeCall {
        cache.getMoviesByName(movieName)
            .map(MovieCacheDto::toEntity)
            .ifEmpty {
                val remoteMovies = remote.getMoviesByName(movieName).map(MovieDto::toEntity)
                insertMovies(remoteMovies)
                remoteMovies
            }
    }


    override suspend fun getMovieGenres(genreIds: List<Int>) = SafeCall {
        if (genreIds.isNotEmpty()) {
            cache.incrementInteractionCountForGenres(genreIds)
        }
        cache.getMovieGenres(genreIds).filter { it.id in genreIds }.map { it.toEntity() }.ifEmpty {
            remote.getMovieGenres().filter { it.id in genreIds }.map(MovieGenreDto::toEntity)
        }
    }


    override suspend fun getMoviesGenres() = SafeCall {
        cache.getMoviesGenres()
            .map(MovieGenreCacheDto::toEntity)
            .ifEmpty {
                remote.getMovieGenres()
                    .map(MovieGenreDto::toEntity)
                    .also { insertGenres(it) }
            }
    }


    override suspend fun getMoviesByGenre(genreId: Int) = SafeCall {
        cache.getMoviesByGenre(genreId).map(MovieCacheDto::toEntity)
            .ifEmpty {
                remote.getMoviesByGenre(genreId)
                    .map(MovieDto::toEntity)
                    .also { insertMovies(it) }
            }
    }

    override suspend fun insertMovies(movie: List<Movie>) = SafeCall {
        cache.insertMovies(movie.map(Movie::toDto))
    }

    override suspend fun insertGenres(genres: List<Genre>) = SafeCall {
        cache.insertMovieGenres(genres.map(Genre::toDto))
    }

    override suspend fun setMovieRecent(movie: Movie, isRecent: Boolean) = SafeCall {
        cache.updateMovie(movie.toDto().copy(isRecent = isRecent))
    }

    override suspend fun clearCache() = SafeCall {
        cache.clearMovieCache()
    }

    override suspend fun getRecentlyMovies() =
        SafeCall { cache.getRecentlyMovies().map(MovieCacheDto::toEntity) }

    override suspend fun clearRecentlyMovies() = SafeCall { cache.clearRecentlyMovies() }

    override suspend fun getMovieDetails(movieId: Int) = SafeCall {
//        Log.d("TAG", "getMovieDetails: ${cache.getMovieById(movieId)?.toEntity()}")
//        Log.d("TAG", "getMovieDetails: ${remote.getMovieById(movieId)}")
        cache.getMovieById(movieId)?.toEntity() ?: remote.getMovieById(movieId)
            .toEntity()
            .also { insertMovies(listOf(it)) }
    }

    override suspend fun getRecommendedMovie(
        movieId: Int,
        page: Int
    ): List<Movie> = SafeCall {
        remote.getMovieRecommendations(movieId, page).map(MovieDto::toEntity)
    }


    override suspend fun getMovieReviews(
        movieId: Int
    ) = SafeCall { remote.getMovieReviews(movieId).map(MovieReviewDto::toEntity) }

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

    override suspend fun getPopularityMovies(): List<Movie> {
        TODO("Not yet implemented")
    }

    override suspend fun getRecentlyReleasedMovies(): List<Movie> {
        TODO("Not yet implemented")
    }

    override suspend fun getUpcomingMovies(): List<Movie> {
        TODO("Not yet implemented")
    }

    private suspend fun getSessionId() = SafeCall {
        sessionManager.createGuestSessionId() ?: throw NoInternetDataException()
    }
}