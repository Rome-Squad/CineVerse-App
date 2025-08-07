package com.giraffe.media.movie

import com.giraffe.media.dto.ReviewDto
import com.giraffe.media.entity.Genre
import com.giraffe.media.mapper.toEntity
import com.giraffe.media.movie.datasource.local.MoviesLocalDataSource
import com.giraffe.media.movie.datasource.local.cacheDto.MovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.MovieGenreCacheDto
import com.giraffe.media.movie.datasource.remote.MoviesRemoteDataSource
import com.giraffe.media.movie.datasource.remote.dto.MovieDto
import com.giraffe.media.movie.datasource.remote.dto.MovieGenreDto
import com.giraffe.media.movie.datasource.remote.dto.RatingRequest
import com.giraffe.media.movie.mapper.toCacheDto
import com.giraffe.media.movie.mapper.toDto
import com.giraffe.media.movie.mapper.toEntity
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.repository.MoviesRepository
import com.giraffe.media.utils.SafeCall
import com.giraffe.media.utils.SafeCall.mapToDomainException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val local: MoviesLocalDataSource,
    private val remote: MoviesRemoteDataSource,
) : MoviesRepository {
    override suspend fun addRating(movieId: Int, ratingValue: Float) = SafeCall {
        val requestBody = RatingRequest(value = ratingValue)
        remote.addRating(movieId, requestBody)
    }

    private suspend fun addMovieGenres(genres: List<Genre>) = SafeCall {
        local.addMovieGenres(genres.map(Genre::toDto))
    }

    private suspend fun setMovieRecent(movie: Movie) {
        local.setMovie(
            movie.toCacheDto(),
            transformer = { it.copy(recentViewedAt = System.currentTimeMillis()) })
    }

    override suspend fun searchMovieByName(movieName: String, page: Int) = SafeCall {
        remote.getMoviesByName(movieName, page).map(MovieDto::toEntity)
    }

    override suspend fun getMovieGenresByIds(genreIds: List<Int>) = SafeCall {
        if (genreIds.isNotEmpty()) {
            local.incrementInteractionCountForGenres(genreIds)
        }
        local.getMovieGenresByIds(genreIds).filter { it.id in genreIds }.map { it.toEntity() }
            .ifEmpty {
                remote.getMovieGenres().filter { it.id in genreIds }.map(MovieGenreDto::toEntity)
            }
    }

    override suspend fun getMoviesGenres() = SafeCall {
        local.getMoviesGenres()
            .map(MovieGenreCacheDto::toEntity)
            .ifEmpty {
                remote.getMovieGenres()
                    .map(MovieGenreDto::toEntity)
                    .also { addMovieGenres(it) }
            }
    }

    override suspend fun getMoviesByGenre(genreId: Int, page: Int) = SafeCall {
        remote.getMoviesByGenre(genreId, page).map(MovieDto::toEntity)
    }

    override fun getRecentlyViewedMovies() =
        local.getRecentlyViewedMovies().map { movies ->
            movies.map(MovieCacheDto::toEntity)
        }.catch { throw mapToDomainException(it) }

    override suspend fun getMovieDetails(movieId: Int) = SafeCall {
        withContext(Dispatchers.IO) {
            val youtubeVideoId = async { remote.getMovieTrailerUrl(movieId) }
            val movieDetails = async { remote.getMovieById(movieId) }.await()
            movieDetails.youtubeVideoId = youtubeVideoId.await()
            val movie = movieDetails.toEntity()
            setMovieRecent(movie)
            movie
        }
    }

    override suspend fun getRecommendedMovie(movieId: Int, page: Int, limit: Int) =
        SafeCall {
            when {
                page > 1 -> getRecommendedFromRemote(movieId, page, limit)
                else -> {
                    local.getRecommendedMovies(movieId, limit).map(MovieCacheDto::toEntity)
                        .ifEmpty {
                            getRecommendedFromRemote(movieId, page, limit).also { movies ->
                                local.setMovies(
                                    movies = movies.map(Movie::toCacheDto),
                                    transformer = { it.copy(recommendedId = movieId) }
                                )
                            }
                        }
                }
            }
        }

    private suspend fun getRecommendedFromRemote(movieId: Int, page: Int, limit: Int) =
        remote.getMovieRecommendations(movieId, page).take(limit).map(MovieDto::toEntity)

    override suspend fun getMovieReviews(
        movieId: Int,
        page: Int
    ) = SafeCall { remote.getMovieReviews(movieId, page = page).map(ReviewDto::toEntity) }

    override suspend fun getUserMovieRating(movieId: Int) = SafeCall {
        remote.getUserMovieRating(movieId)
    }

    override suspend fun getPopularityMovies(page: Int, limit: Int) =
        SafeCall {
            when {
                page > 1 -> getPopularityMoviesFromRemote(page, limit)
                else -> {
                    local.getPopularityMovies(limit = limit).map(MovieCacheDto::toEntity).ifEmpty {
                        getPopularityMoviesFromRemote(page, limit).also {
                            local.setMovies(it.map(Movie::toCacheDto))
                        }
                    }
                }
            }
        }

    private suspend fun getPopularityMoviesFromRemote(page: Int, limit: Int) =
        remote.getPopularityMovies(page).take(limit).map(MovieDto::toEntity)

    override suspend fun getRecentlyReleasedMovies(page: Int, limit: Int) =
        SafeCall {
            when {
                page > 1 -> getRecentlyReleasedMoviesFromRemote(page, limit)
                else -> {
                    local.getRecentlyReleasedMovies(limit = limit).map(MovieCacheDto::toEntity)
                        .ifEmpty {
                            getRecentlyReleasedMoviesFromRemote(page, limit).also { movies ->
                                local.setMovies(
                                    movies = movies.map(Movie::toCacheDto),
                                    transformer = { it.copy(recentReleasedAt = System.currentTimeMillis()) }
                                )
                            }
                        }
                }
            }
        }

    private suspend fun getRecentlyReleasedMoviesFromRemote(page: Int, limit: Int) =
        remote.getRecentlyReleasedMovies(page).take(limit).map(MovieDto::toEntity)

    override suspend fun getUpcomingMovies(page: Int, limit: Int) =
        SafeCall {
            when {
                page > 1 -> getUpcomingMoviesFromRemote(page, limit)
                else -> {
                    local.getUpcomingMovies(limit = limit).map(MovieCacheDto::toEntity)
                        .ifEmpty {
                            getUpcomingMoviesFromRemote(page, limit).also { movies ->
                                local.setMovies(
                                    movies = movies.map(Movie::toCacheDto),
                                    transformer = { it.copy(upcomingAt = System.currentTimeMillis()) }
                                )
                            }
                        }
                }
            }
        }

    private suspend fun getUpcomingMoviesFromRemote(page: Int, limit: Int) =
        remote.getUpcomingMovies(page).take(limit).map(MovieDto::toEntity)

    override suspend fun deleteMovieById(movieId: Int) {
        local.deleteMovieById(movieId)
    }

    override suspend fun getRatedMovies(accountId: Int): Map<Float, Movie> = SafeCall {
        remote.getRatedMovies(accountId)
            .filter { it.userRating != null }
            .associate { it.userRating!! to it.toEntity() }
    }

    override suspend fun deleteMovieRating(movieId: Int) = SafeCall {
        remote.deleteMovieRating(movieId)
    }

    override suspend fun clearMovieCache() = SafeCall {
        local.clearMovieCache()
    }

    override suspend fun clearRecentlyViewedMovies() {
        local.clearRecentlyViewedMovies()
    }

    override suspend fun clearMovieCacheWithOutRecentViewed() {
        local.clearMovieCacheWithOutRecentViewed()
    }

    override suspend fun clearMovieGenres() {
        local.clearMovieGenres()
    }

}
