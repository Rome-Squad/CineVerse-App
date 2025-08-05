package  com.giraffe.media.movie

import com.giraffe.media.movie.dao.MovieDao
import com.giraffe.media.movie.datasource.local.MoviesLocalDataSource
import com.giraffe.media.movie.datasource.local.cacheDto.MovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.MovieGenreCacheDto
import com.giraffe.media.util.safeCall
import com.giraffe.media.util.safeFlow
import javax.inject.Inject


class MovieLocalDataSourceImp @Inject constructor(
    private val movieDao: MovieDao
) : MoviesLocalDataSource {
    override suspend fun insertMovieGenres(movieGenres: List<MovieGenreCacheDto>) = safeCall {
        movieDao.insertMovieGenres(movieGenres)
    }

    override suspend fun upsertMovie(
        movie: MovieCacheDto,
        transformer: ((MovieCacheDto) -> MovieCacheDto)?
    ) = safeCall {
        val existingMovie = movieDao.getMovieById(movie.id)
        val mergedMovie = movie.mergeWith(existingMovie)
        val finalMovie = transformer?.invoke(mergedMovie) ?: mergedMovie
        movieDao.upsertMovie(finalMovie)
    }

    override suspend fun upsertMovies(
        movies: List<MovieCacheDto>,
        transformer: ((MovieCacheDto) -> MovieCacheDto)?
    ) {
        if (movies.isEmpty()) return
        val incomingMovieIds = movies.map { it.id }
        val existingMoviesById = movieDao.getMoviesByIds(incomingMovieIds).associateBy { it.id }
        val mergedMovies = if (existingMoviesById.isEmpty()) {
            movies
        } else {
            movies.map { incomingMovie ->
                existingMoviesById[incomingMovie.id]?.let { existingMovie ->
                    incomingMovie.mergeWith(existingMovie)
                } ?: incomingMovie
            }
        }
        val finalMovies = transformer?.let { mergedMovies.map(it) } ?: mergedMovies
        movieDao.upsertMovies(finalMovies)
    }

    // need description to understand function
    private fun MovieCacheDto.mergeWith(existing: MovieCacheDto?): MovieCacheDto {
        if (existing == null) return this
        return this.copy(
            posterPath = this.posterPath ?: existing.posterPath,
            backdropPath = this.backdropPath ?: existing.backdropPath,
            youtubeVideoId = this.youtubeVideoId ?: existing.youtubeVideoId,
            releaseDate = this.releaseDate ?: existing.releaseDate,
            duration = this.duration ?: existing.duration,
            recentViewedAt = existing.recentViewedAt,
            recentReleasedAt = existing.recentReleasedAt,
            upcomingAt = existing.upcomingAt
        )
    }

    override suspend fun incrementInteractionCountForGenres(genreIds: List<Int>) = safeCall {
        movieDao.incrementInteractionCountForGenres(genreIds)
    }

    override suspend fun getMovieById(movieId: Int) = safeCall {
        movieDao.getMovieById(movieId)
    }

    override suspend fun getMoviesByName(movieName: String) = safeCall {
        movieDao.getMovieByName(movieName)
    }

    override suspend fun getPopularityMovies(limit: Int) = safeCall {
        movieDao.getPopularityMovies(limit = limit)
    }

    override suspend fun getRecentlyReleasedMovies(limit: Int) = safeCall {
        movieDao.getRecentlyReleasedMovies(limit)
    }

    override suspend fun getRecommendedMovies(movieId: Int, limit: Int) = safeCall {
        movieDao.getRecommendedMovies(movieId, limit)
    }

    override suspend fun getMovieGenresByIds(ids: List<Int>) = safeCall {
        movieDao.getMovieGenresByIds(ids)
    }

    override suspend fun getMovieGenreById(genreId: Int) = safeCall {
        movieDao.getMovieGenreById(genreId)
    }

    override suspend fun getMoviesByGenre(genreId: Int) = safeCall {
        movieDao.getMoviesByGenre(genreId)
    }

    override suspend fun getMoviesGenres() = safeCall {
        movieDao.getMoviesGenres()
    }

    override suspend fun getUpcomingMovies(limit: Int) = safeCall {
        movieDao.getUpcomingMovies(limit)
    }

    override fun getRecentlyViewedMovies() = safeFlow {
        movieDao.getRecentlyViewedMovies()
    }

    override suspend fun clearMovieCache() = safeCall {
        movieDao.clearMovieCache()
    }

    override suspend fun clearRecentlyMovies() = safeCall {
        movieDao.clearRecentlyMovies()
    }

    override suspend fun clearMovieGenreCache() = safeCall {
        movieDao.clearMovieGenreCache()
    }
}