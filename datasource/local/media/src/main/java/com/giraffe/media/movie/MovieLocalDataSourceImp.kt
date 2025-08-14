package  com.giraffe.media.movie

import com.giraffe.media.movie.dao.MovieDao
import com.giraffe.media.movie.datasource.local.MoviesLocalDataSource
import com.giraffe.media.movie.datasource.local.cacheDto.MovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.MovieGenreCacheDto
import com.giraffe.media.movie.mapper.toMatchesYourVibeMovieCacheDto
import com.giraffe.media.movie.mapper.toPopularMovieCacheDto
import com.giraffe.media.movie.mapper.toRecentReleasedMovieCacheDto
import com.giraffe.media.movie.mapper.toRecentlyViewedMovieCacheDto
import com.giraffe.media.movie.mapper.toUpcomingMovieCacheDto
import com.giraffe.media.util.safeCall
import com.giraffe.media.util.safeFlow
import javax.inject.Inject


class MovieLocalDataSourceImp @Inject constructor(
    private val movieDao: MovieDao
) : MoviesLocalDataSource {

    // region Movie Genres
    override suspend fun addMovieGenres(movieGenres: List<MovieGenreCacheDto>) =
        safeCall { movieDao.insertMovieGenres(movieGenres) }

    override suspend fun incrementInteractionCountForGenres(genreIds: List<Int>) =
        safeCall { movieDao.incrementInteractionCountForGenres(genreIds) }

    override suspend fun getMovieGenresByIds(ids: List<Int>) =
        safeCall { movieDao.getMovieGenresByIds(ids) }

    override suspend fun getMoviesGenres() =
        safeCall { movieDao.getMoviesGenres() }

    override suspend fun getTopGenre() =
        safeCall { movieDao.getTopGenre() }

    override suspend fun clearMovieGenres() =
        safeCall { movieDao.clearMovieGenres() }
    // endregion Movie Genres

    // region Popularity, Recently Released, Upcoming, Match, Recently Viewed
    override suspend fun addPopularityMovies(movies: List<MovieCacheDto>) {
        safeCall {
            movieDao.insertMovies(movies)
            movieDao.insertPopularMovies(movies.map(MovieCacheDto::toPopularMovieCacheDto))
        }
    }

    override suspend fun getPopularityMovies(limit: Int) =
        safeCall { movieDao.getPopularityMovies(limit = limit) }

    override suspend fun clearPopularMovies() =
        safeCall { movieDao.clearPopularMovies() }

    override suspend fun addRecentlyReleasedMovies(movies: List<MovieCacheDto>) {
        safeCall {
            movieDao.insertMovies(movies)
            movieDao.insertRecentlyReleasedMovies(movies.map(MovieCacheDto::toRecentReleasedMovieCacheDto))
        }
    }

    override suspend fun getRecentlyReleasedMovies(limit: Int) =
        safeCall { movieDao.getRecentlyReleasedMovies(limit) }

    override suspend fun clearRecentlyReleasedMovies() =
        safeCall { movieDao.clearRecentlyReleasedMovies() }

    override suspend fun addUpcomingMovies(movies: List<MovieCacheDto>) {
        safeCall {
            movieDao.insertMovies(movies)
            movieDao.insertUpcomingMovies(movies.map(MovieCacheDto::toUpcomingMovieCacheDto))
        }
    }

    override suspend fun getUpcomingMovies(limit: Int) =
        safeCall { movieDao.getUpcomingMovies(limit) }

    override suspend fun clearUpcomingMovies() =
        safeCall { movieDao.clearUpcomingMovies() }

    override suspend fun addMatchesYourVibeMovies(movies: List<MovieCacheDto>) {
        safeCall {
            movieDao.insertMovies(movies)
            movieDao.insertMatchesYourVibeMovies(movies.map(MovieCacheDto::toMatchesYourVibeMovieCacheDto))
        }
    }

    override suspend fun getMatchesYourVibeMovies(limit: Int) =
        safeCall { movieDao.getMatchesYourVibeMovies(limit) }

    override suspend fun clearMatchesYourVibeMovies() =
        safeCall { movieDao.clearMatchesYourVibeMovies() }

    override suspend fun addRecentlyViewedMovie(movie: MovieCacheDto) {
        safeCall {
            movieDao.insertMovie(movie)
            movieDao.insertRecentlyViewedMovie(movie.toRecentlyViewedMovieCacheDto())
        }
    }

    override fun getRecentlyViewedMovies() =
        safeFlow { movieDao.getRecentlyViewedMovies() }

    override suspend fun deleteRecentlyViewedMovieById(movieId: Int) =
        safeCall { movieDao.deleteRecentlyViewedMovieById(movieId) }

    override suspend fun clearRecentlyViewedMovies() =
        safeCall { movieDao.clearRecentlyViewedMovies() }

    // endregion Popularity, Recently Released, Upcoming, Match, Recently Viewed

    override suspend fun clearMovieCache() =
        safeCall { movieDao.clearMovieCache() }

    override suspend fun clearExceptRecentlyViewed() =
        safeCall { movieDao.clearExceptRecentlyViewed() }
}