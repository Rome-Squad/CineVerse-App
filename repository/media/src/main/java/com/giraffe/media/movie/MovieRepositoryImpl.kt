package com.giraffe.media.movie

import com.giraffe.media.dto.ReviewDto
import com.giraffe.media.entity.Genre
import com.giraffe.media.mapper.toEntity
import com.giraffe.media.movie.datasource.local.MoviesLocalDataSource
import com.giraffe.media.movie.datasource.local.cacheDto.MovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.MovieGenreCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.MovieWithRecentlyViewedAt
import com.giraffe.media.movie.datasource.remote.MoviesRemoteDataSource
import com.giraffe.media.movie.datasource.remote.dto.MovieDto
import com.giraffe.media.movie.datasource.remote.dto.MovieGenreDto
import com.giraffe.media.movie.datasource.remote.dto.RatingRequest
import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.movie.mapper.toCacheDto
import com.giraffe.media.movie.mapper.toDto
import com.giraffe.media.movie.mapper.toEntity
import com.giraffe.media.movie.mapper.toPopularMovieCacheDto
import com.giraffe.media.movie.mapper.toRecentReleasedMovieCacheDto
import com.giraffe.media.movie.mapper.toRecentlyViewedMovieCacheDto
import com.giraffe.media.movie.mapper.toUpcomingMovieCacheDto
import com.giraffe.media.movie.repository.MovieRepository
import com.giraffe.media.utils.SafeCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
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

    // region Movie Genres
    private suspend fun addMovieGenres(genres: List<Genre>) =
        SafeCall { movieLocal.addMovieGenres(genres.map(Genre::toDto)) }

    override suspend fun getGenres(): List<Genre> {
        return SafeCall {
            getLocalGenres()
                .ifEmpty {
                    getRemoteGenres()
                }
        }
    }

    private suspend fun getLocalGenres(): List<Genre> {
        return movieLocal.getMoviesGenres()
            .map(MovieGenreCacheDto::toEntity)
    }

    private suspend fun getRemoteGenres(): List<Genre> {
        return movieRemote.getMovieGenres()
            .map(MovieGenreDto::toEntity)
            .also { addMovieGenres(it) }
    }

    override suspend fun getGenresByIds(genreIds: List<Int>): List<Genre> {
        return SafeCall {
            if (genreIds.isEmpty()) return@SafeCall emptyList()
            movieLocal.getMovieGenresByIds(genreIds)
                .map(MovieGenreCacheDto::toEntity)
                .ifEmpty {
                    getRemoteGenres().filter { it.id in genreIds }
                }
        }
    }

    override suspend fun getTopGenre() =
        movieLocal.getTopGenre()?.toEntity()

    override suspend fun clearGenres() =
        movieLocal.clearMovieGenres()
    // endregion

    override suspend fun getByName(name: String, page: Int) = SafeCall {
        movieRemote.getMoviesByName(name, page).map(MovieDto::toEntity)
    }

    override suspend fun getByGenreId(genreId: Int, page: Int) = SafeCall {
        movieRemote.getMoviesByGenre(genreId, page).map(MovieDto::toEntity)
    }

    override suspend fun getDetails(movieId: Int) = SafeCall {
        withContext(Dispatchers.IO) {
            val youtubeVideoId = async { movieRemote.getMovieTrailerUrl(movieId) }
            val movieDetails = async { movieRemote.getMovieById(movieId) }.await()
            movieDetails.youtubeVideoId = youtubeVideoId.await()
            val movie = movieDetails.toEntity()
            movieLocal.incrementInteractionCountForGenres(movie.genresID)
            addRecentlyViewed(movie)
            movie
        }
    }

    override suspend fun getRecommended(movieId: Int, page: Int): List<Movie> {
        return SafeCall {
            movieRemote.getMovieRecommendations(movieId, page).map(MovieDto::toEntity)
        }
    }

    override suspend fun getReviews(
        movieId: Int,
        page: Int
    ) = SafeCall { movieRemote.getMovieReviews(movieId, page = page).map(ReviewDto::toEntity) }

    override suspend fun getUserRatedById(movieId: Int) = SafeCall {
        movieRemote.getUserMovieRating(movieId)
    }

    override suspend fun deleteById(movieId: Int) {
        movieLocal.deleteMovieById(movieId)
    }

    override suspend fun getUserRated(accountId: Int) = SafeCall {
        movieRemote.getRatedMovies(accountId).map(MovieDto::toEntity)
    }

    // region Popular
    override suspend fun getPopular(page: Int, limit: Int): List<Movie> {
        return SafeCall {
            when (page) {
                1 -> {
                    getLocalPopular(limit).ifEmpty {
                        getRemotePopular(page = page, limit = limit)
                    }
                }

                else -> {
                    getRemotePopular(page = page, limit = limit)
                }
            }
        }
    }

    private suspend fun addPopular(page: Int, movies: List<Movie>) {
        if (page == 1) {
            movieLocal.addMovies(movies.map(Movie::toCacheDto))
            movieLocal.addPopularityMovies(movies.map(Movie::toPopularMovieCacheDto))
        }
    }

    private suspend fun getLocalPopular(limit: Int): List<Movie> {
        return movieLocal.getPopularityMovies(limit = limit)
            .map(MovieCacheDto::toEntity)
    }

    private suspend fun getRemotePopular(page: Int, limit: Int): List<Movie> {
        return movieRemote.getPopularityMovies(page)
            .take(limit)
            .map(MovieDto::toEntity)
            .also {
                addPopular(page, it)
            }
    }
    // endregion

    // region Recently Released
    override suspend fun getRecentlyReleased(page: Int, limit: Int): List<Movie> {
        return SafeCall {
            when (page) {
                1 -> {
                    getLocalRecentlyReleased(limit).ifEmpty {
                        getRemoteRecentlyReleased(page = page, limit = limit)
                    }
                }

                else -> {
                    getRemoteRecentlyReleased(page = page, limit = limit)
                }
            }
        }
    }

    private suspend fun addRecentlyReleased(page: Int, movies: List<Movie>) {
        if (page == 1) {
            movieLocal.addMovies(movies.map(Movie::toCacheDto))
            movieLocal.addRecentlyReleasedMovies(movies.map(Movie::toRecentReleasedMovieCacheDto))
        }
    }

    private suspend fun getLocalRecentlyReleased(limit: Int): List<Movie> {
        return movieLocal.getRecentlyReleasedMovies(limit)
            .map(MovieCacheDto::toEntity)
    }

    private suspend fun getRemoteRecentlyReleased(page: Int, limit: Int): List<Movie> {
        return movieRemote.getRecentlyReleasedMovies(page)
            .take(limit)
            .map(MovieDto::toEntity)
            .also {
                addRecentlyReleased(page, it)
            }
    }
    // endregion

    // region Upcoming
    override suspend fun getUpcoming(page: Int, limit: Int): List<Movie> {
        return SafeCall {
            when (page) {
                1 -> getLocalUpcoming(limit).ifEmpty {
                    getRemoteUpcoming(page = page, limit = limit)
                }

                else -> {
                    getRemoteUpcoming(page, limit)
                }
            }
        }
    }

    private suspend fun addUpcoming(page: Int, movies: List<Movie>) {
        if (page == 1) {
            movieLocal.addMovies(movies.map(Movie::toCacheDto))
            movieLocal.addUpcomingMovies(movies.map(Movie::toUpcomingMovieCacheDto))
        }
    }

    private suspend fun getLocalUpcoming(limit: Int): List<Movie> {
        return movieLocal.getUpcomingMovies(limit)
            .map(MovieCacheDto::toEntity)
    }

    private suspend fun getRemoteUpcoming(page: Int, limit: Int): List<Movie> {
        return movieRemote.getUpcomingMovies(page)
            .take(limit)
            .map(MovieDto::toEntity)
            .also {
                addUpcoming(page = page, movies = it)
            }
    }
    // endregion

    // region Match

    // endregion

    // region Recently Viewed
    private suspend fun addRecentlyViewed(movie: Movie) {
        movieLocal.addMovie(movie.toCacheDto())
        movieLocal.addRecentlyViewedMovie(movie.toRecentlyViewedMovieCacheDto())
    }

    override fun getRecentlyViewed(): Flow<List<Movie>> {
        return movieLocal.getRecentlyViewedMovies().map { movies ->
            movies.map(MovieWithRecentlyViewedAt::toEntity)
        }
    }
    // endregion

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
        movieLocal.clearExceptRecentlyViewed()
    }

}
