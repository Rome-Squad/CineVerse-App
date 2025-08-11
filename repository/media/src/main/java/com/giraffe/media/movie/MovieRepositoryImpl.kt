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
import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.movie.mapper.toCacheDto
import com.giraffe.media.movie.mapper.toDto
import com.giraffe.media.movie.mapper.toEntity
import com.giraffe.media.movie.repository.MovieRepository
import com.giraffe.media.utils.SafeCall
import com.giraffe.media.utils.SafeCall.mapToDomainException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieLocal: MoviesLocalDataSource,
    private val movieRemote: MoviesRemoteDataSource,
) : MovieRepository {
    override suspend fun addRating(movieId: Int, rating: Float) = SafeCall {
        val requestBody = RatingRequest(value = rating)
        movieRemote.addRating(movieId, requestBody)
    }

    private suspend fun addMovieGenres(genres: List<Genre>) = SafeCall {
        movieLocal.addMovieGenres(genres.map(Genre::toDto))
    }

    private suspend fun setMovieRecent(movie: Movie) {
        movieLocal.setMovie(
            movie.toCacheDto(),
            transformer = { it.copy(recentViewedAt = System.currentTimeMillis()) })
    }

    override suspend fun getByName(name: String, page: Int) = SafeCall {
        movieRemote.getMoviesByName(name, page).map(MovieDto::toEntity)
    }

    override suspend fun getGenresByIds(genreIds: List<Int>) = SafeCall {
        if (genreIds.isNotEmpty()) {
            movieLocal.incrementInteractionCountForGenres(genreIds)
        }
        movieLocal.getMovieGenresByIds(genreIds).filter { it.id in genreIds }.map { it.toEntity() }
            .ifEmpty {
                movieRemote.getMovieGenres().filter { it.id in genreIds }.map(MovieGenreDto::toEntity)
            }
    }

    override suspend fun getGenres() = SafeCall {
        movieLocal.getMoviesGenres()
            .map(MovieGenreCacheDto::toEntity)
            .ifEmpty {
                movieRemote.getMovieGenres()
                    .map(MovieGenreDto::toEntity)
                    .also { addMovieGenres(it) }
            }
    }

    override suspend fun getByGenreId(genreId: Int, page: Int) = SafeCall {
        movieRemote.getMoviesByGenre(genreId, page).map(MovieDto::toEntity)
    }

    override fun getRecentlyViewed() =
        movieLocal.getRecentlyViewedMovies().map { movies ->
            movies.map(MovieCacheDto::toEntity)
        }.catch { throw mapToDomainException(it) }

    override suspend fun getDetails(movieId: Int) = SafeCall {
        withContext(Dispatchers.IO) {
            val youtubeVideoId = async { movieRemote.getMovieTrailerUrl(movieId) }
            val movieDetails = async { movieRemote.getMovieById(movieId) }.await()
            movieDetails.youtubeVideoId = youtubeVideoId.await()
            val movie = movieDetails.toEntity()
            setMovieRecent(movie)
            movie
        }
    }

    override suspend fun getRecommended(movieId: Int, page: Int, limit: Int) =
        SafeCall {
            when {
                page > 1 -> getRecommendedFromRemote(movieId, page, limit)
                else -> {
                    movieLocal.getRecommendedMovies(movieId, limit).map(MovieCacheDto::toEntity)
                        .ifEmpty {
                            getRecommendedFromRemote(movieId, page, limit).also { movies ->
                                movieLocal.setMovies(
                                    movies = movies.map(Movie::toCacheDto),
                                    transformer = { it.copy(recommendedId = movieId) }
                                )
                            }
                        }
                }
            }
        }

    private suspend fun getRecommendedFromRemote(movieId: Int, page: Int, limit: Int) =
        movieRemote.getMovieRecommendations(movieId, page).take(limit).map(MovieDto::toEntity)

    override suspend fun getReviews(
        movieId: Int,
        page: Int
    ) = SafeCall { movieRemote.getMovieReviews(movieId, page = page).map(ReviewDto::toEntity) }

    override suspend fun getUserRatedById(movieId: Int) = SafeCall {
        movieRemote.getUserMovieRating(movieId)
    }

    override suspend fun getPopular(page: Int, limit: Int) =
        SafeCall {
            when {
                page > 1 -> getPopularityMoviesFromRemote(page, limit)
                else -> {
                    movieLocal.getPopularityMovies(limit = limit).map(MovieCacheDto::toEntity).ifEmpty {
                        getPopularityMoviesFromRemote(page, limit).also {
                            movieLocal.setMovies(it.map(Movie::toCacheDto))
                        }
                    }
                }
            }
        }

    private suspend fun getPopularityMoviesFromRemote(page: Int, limit: Int) =
        movieRemote.getPopularityMovies(page).take(limit).map(MovieDto::toEntity)

    override suspend fun getRecentlyReleased(page: Int, limit: Int) =
        SafeCall {
            when {
                page > 1 -> getRecentlyReleasedMoviesFromRemote(page, limit)
                else -> {
                    movieLocal.getRecentlyReleasedMovies(limit = limit).map(MovieCacheDto::toEntity)
                        .ifEmpty {
                            getRecentlyReleasedMoviesFromRemote(page, limit).also { movies ->
                                movieLocal.setMovies(
                                    movies = movies.map(Movie::toCacheDto),
                                    transformer = { it.copy(recentReleasedAt = System.currentTimeMillis()) }
                                )
                            }
                        }
                }
            }
        }

    private suspend fun getRecentlyReleasedMoviesFromRemote(page: Int, limit: Int) =
        movieRemote.getRecentlyReleasedMovies(page).take(limit).map(MovieDto::toEntity)

    override suspend fun getUpcoming(page: Int, limit: Int) =
        SafeCall {
            when {
                page > 1 -> getUpcomingMoviesFromRemote(page, limit)
                else -> {
                    movieLocal.getUpcomingMovies(limit = limit).map(MovieCacheDto::toEntity)
                        .ifEmpty {
                            getUpcomingMoviesFromRemote(page, limit).also { movies ->
                                movieLocal.setMovies(
                                    movies = movies.map(Movie::toCacheDto),
                                    transformer = { it.copy(upcomingAt = System.currentTimeMillis()) }
                                )
                            }
                        }
                }
            }
        }

    private suspend fun getUpcomingMoviesFromRemote(page: Int, limit: Int) =
        movieRemote.getUpcomingMovies(page).take(limit).map(MovieDto::toEntity)

    override suspend fun deleteById(movieId: Int) {
        movieLocal.deleteMovieById(movieId)
    }

    override suspend fun getUserRated(accountId: Int) = SafeCall {
        movieRemote.getRatedMovies(accountId).map(MovieDto::toEntity)
    }

    override suspend fun deleteRating(movieId: Int) = SafeCall {
        movieRemote.deleteMovieRating(movieId)
    }

    override suspend fun clearAll() = SafeCall {
        movieLocal.clearMovieCache()
    }

    override suspend fun clearRecentlyViewed() {
        movieLocal.clearRecentlyViewedMovies()
    }

    override suspend fun clearExceptRecentlyViewed() {
        movieLocal.clearMovieCacheWithOutRecentViewed()
    }

    override suspend fun clearGenres() {
        movieLocal.clearMovieGenres()
    }

}
