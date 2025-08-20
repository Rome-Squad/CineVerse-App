package com.giraffe.media.movie

import com.giraffe.media.dto.ReviewDto
import com.giraffe.media.entity.Genre
import com.giraffe.media.entity.Review
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
import com.giraffe.media.movie.mapper.toEntity
import com.giraffe.media.movie.repository.MovieRepository
import com.giraffe.media.utils.safeCall
import com.giraffe.media.utils.safeFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieLocal: MoviesLocalDataSource,
    private val movieRemote: MoviesRemoteDataSource,
) : MovieRepository {

    override suspend fun getByName(name: String, page: Int): List<Movie> {
        return safeCall {
            movieRemote.getMoviesByName(name, page)
                .map(MovieDto::toEntity)
        }
    }

    override suspend fun getByGenreId(genreId: Int, page: Int): List<Movie> {
        return safeCall {
            movieRemote.getMoviesByGenre(genreId, page)
                .map(MovieDto::toEntity)
        }
    }

    override suspend fun getDetails(movieId: Int): Movie {
        return safeCall {
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
    }

    override suspend fun getByGenreIds(genreIds: List<Int>, page: Int): List<Movie> {
        return safeCall {
            movieRemote.getMoviesByGenreIds(
                genreIds = genreIds,
                page = page
            ).map(MovieDto::toEntity)
        }
    }

    override suspend fun getByKeywordsId(keywords: Int, page: Int): List<Movie> {
        return safeCall {
            movieRemote.getMoviesByKeywordsId(
                keywords = keywords,
                page = page
            ).map(MovieDto::toEntity)
        }
    }

    override suspend fun getBySort(sortBy: String, page: Int): List<Movie> {
        return safeCall {
            movieRemote.getMoviesBySort(
                sortBy = sortBy,
                page = page
            ).map(MovieDto::toEntity)
        }
    }

    override suspend fun getRecommended(movieId: Int, page: Int): List<Movie> {
        return safeCall {
            movieRemote.getMovieRecommendations(movieId, page)
                .map(MovieDto::toEntity)
        }
    }

    // region Movie Genres
    override fun observeGenres(): Flow<List<Genre>> {
        return safeFlow {
            movieLocal.getMoviesGenres()
                .map { it.map(MovieGenreCacheDto::toEntity) }
                .onEach { it.ifEmpty { getGenres() } }
        }
    }

    override suspend fun getGenres(): List<Genre> {
        return safeCall {
            movieRemote.getMovieGenres()
                .map(MovieGenreDto::toEntity)
                .also { syncMovieGenres(it) }
        }
    }

    private suspend fun syncMovieGenres(genres: List<Genre>) =
        movieLocal.syncMovieGenres(genres.map(Genre::toCacheDto))

    override fun getGenresByIds(genreIds: List<Int>): Flow<List<Genre>> {
        if (genreIds.isEmpty()) return flowOf(emptyList())
        return safeFlow {
            movieLocal.getMovieGenresByIds(genreIds)
                .map { it.map(MovieGenreCacheDto::toEntity) }
                .onEach {
                    getGenres().filter { it.id in genreIds }
                }
        }
    }

    override suspend fun getTopGenre() =
        safeCall { movieLocal.getTopGenre()?.toEntity() }

    override suspend fun clearGenres() =
        safeCall { movieLocal.clearMovieGenres() }
    // endregion

    // region Rating
    override suspend fun addRating(movieId: Int, rating: Float) {
        safeCall {
            val requestBody = RatingRequest(value = rating)
            movieRemote.addRating(movieId, requestBody)
        }
    }

    override suspend fun getReviews(movieId: Int, page: Int): List<Review> {
        return safeCall {
            movieRemote.getMovieReviews(movieId, page = page)
                .map(ReviewDto::toEntity)
        }
    }

    override suspend fun getUserRatedById(movieId: Int) =
        safeCall { movieRemote.getUserMovieRating(movieId) }

    override suspend fun getUserRated(accountId: Int): List<Movie> {
        return safeCall {
            movieRemote.getRatedMovies(accountId)
                .map(MovieDto::toEntity)
        }
    }

    override suspend fun deleteRating(movieId: Int) =
        safeCall { movieRemote.deleteMovieRating(movieId) }
    // endregion

    // region Popular
    override fun observePopular(limit: Int): Flow<List<Movie>> {
        return safeFlow {
            movieLocal.getPopularityMovies(limit = limit)
                .map { it.map(MovieCacheDto::toEntity) }
                .onEach {
                    it.ifEmpty {
                        getRemotePopular(page = 1, limit = limit)
                            .also { movies ->
                                addPopular(movies)
                            }
                    }
                }
        }
    }

    private suspend fun addPopular(movies: List<Movie>) {
        if (movies.isEmpty()) return
        movieLocal.addPopularityMovies(movies.map(Movie::toCacheDto))
    }

    private suspend fun getRemotePopular(page: Int, limit: Int): List<Movie> {
        return safeCall {
            movieRemote.getPopularityMovies(page)
                .take(limit)
                .map(MovieDto::toEntity)
        }
    }
    // endregion

    // region Recently Released
    override fun observeRecentlyReleased(limit: Int): Flow<List<Movie>> {
        return safeFlow {
            movieLocal.getRecentlyReleasedMovies(limit)
                .map { it.map(MovieCacheDto::toEntity) }
                .onEach {
                    it.ifEmpty {
                        getRecentlyReleased(page = 1, limit = limit)
                            .also { movies ->
                                addRecentlyReleased(movies)
                            }
                    }
                }
        }
    }

    private suspend fun addRecentlyReleased(movies: List<Movie>) {
        if (movies.isEmpty()) return
        movieLocal.addRecentlyReleasedMovies(movies.map(Movie::toCacheDto))
    }

    override suspend fun getRecentlyReleased(page: Int, limit: Int): List<Movie> {
        return safeCall {
            movieRemote.getRecentlyReleasedMovies(page)
                .take(limit)
                .map(MovieDto::toEntity)
        }
    }
    // endregion

    // region Upcoming
    override fun observeUpcoming(limit: Int): Flow<List<Movie>> {
        return safeFlow {
            movieLocal.getUpcomingMovies(limit)
                .map { it.map(MovieCacheDto::toEntity) }
                .onEach {
                    it.ifEmpty {
                        getUpcoming(page = 1, limit = limit)
                            .also { movies ->
                                addUpcoming(movies = movies)
                            }
                    }
                }
        }
    }

    private suspend fun addUpcoming(movies: List<Movie>) {
        if (movies.isEmpty()) return
        movieLocal.addUpcomingMovies(movies.map(Movie::toCacheDto))
    }

    override suspend fun getUpcoming(page: Int, limit: Int): List<Movie> {
        return safeCall {
            movieRemote.getUpcomingMovies(page)
                .take(limit)
                .map(MovieDto::toEntity)
        }
    }
    // endregion

    // region Match
    override fun observeMatchesYourVibe(limit: Int): Flow<List<Movie>> {
        return safeFlow {
            movieLocal.getMatchesYourVibeMovies(limit)
                .map { it.map(MovieCacheDto::toEntity) }
                .onEach {
                    it.ifEmpty {
                        getMatchesYourVibe(page = 1, limit = limit)
                            .also { movies ->
                                addMatchesYourVibe(movies = movies)
                            }
                    }
                }
        }
    }

    private suspend fun addMatchesYourVibe(movies: List<Movie>) {
        if (movies.isEmpty()) return
        movieLocal.addMatchesYourVibeMovies(movies.map(Movie::toCacheDto))
    }

    override suspend fun getMatchesYourVibe(page: Int, limit: Int): List<Movie> {
        return safeCall {
            getTopGenre()?.let { topGenre ->
                getByGenreId(genreId = topGenre.id, page = page).take(limit)
            } ?: emptyList()
        }
    }
    // endregion

    // region Recently Viewed
    private suspend fun addRecentlyViewed(movie: Movie) =
        movieLocal.addRecentlyViewedMovie(movie.toCacheDto())

    override fun observeRecentlyViewed(page: Int, pageSize: Int): Flow<List<Movie>> {
        return safeFlow {
            movieLocal.getRecentlyViewedMovies(page, pageSize)
                .map { movies ->
                    movies.map(MovieWithRecentlyViewedAt::toEntity)
                }
        }
    }

    override suspend fun syncRecentlyViewedMovies() {
        return safeCall {
            movieLocal.getRecentlyViewedMovieIds().forEach {
                movieRemote.getMovieById(it)
                    .toCacheDto()
                    .also { movie ->
                        movieLocal.addMovie(movie)
                    }
            }
        }
    }

    override suspend fun deleteRecentlyViewedMovieById(movieId: Int) =
        safeCall { movieLocal.deleteRecentlyViewedMovieById(movieId) }

    override suspend fun clearRecentlyViewed() =
        safeCall { movieLocal.clearRecentlyViewedMovies() }
    // endregion

    override suspend fun clearAll() {
        safeCall {
            movieLocal.clearMovieCache()
            movieLocal.clearMovieGenres()
            movieLocal.clearPopularMovies()
            movieLocal.clearRecentlyReleasedMovies()
            movieLocal.clearUpcomingMovies()
            movieLocal.clearMatchesYourVibeMovies()
            movieLocal.clearRecentlyViewedMovies()
        }
    }

    override suspend fun clearExceptRecentlyViewed() {
        safeCall {
            movieLocal.clearExceptRecentlyViewed()
            movieLocal.clearPopularMovies()
            movieLocal.clearRecentlyReleasedMovies()
            movieLocal.clearUpcomingMovies()
            movieLocal.clearMatchesYourVibeMovies()
        }
    }
}
