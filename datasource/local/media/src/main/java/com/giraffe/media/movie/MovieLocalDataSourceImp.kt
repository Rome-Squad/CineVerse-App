package  com.giraffe.media.movie

import com.giraffe.media.movie.dao.MovieDao
import com.giraffe.media.movie.datasource.local.MoviesLocalDataSource
import com.giraffe.media.movie.datasource.local.cacheDto.MatchesYourVibeMovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.MovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.MovieGenreCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.PopularMovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.RecentReleasedMovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.RecentlyViewedMovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.UpcomingMovieCacheDto
import com.giraffe.media.util.safeCall
import com.giraffe.media.util.safeFlow
import javax.inject.Inject


class MovieLocalDataSourceImp @Inject constructor(
    private val movieDao: MovieDao
) : MoviesLocalDataSource {
    override suspend fun addMovies(movies: List<MovieCacheDto>) =
        safeCall { movieDao.insertMovies(movies) }

    override suspend fun addMovie(movie: MovieCacheDto) =
        safeCall { movieDao.insertMovie(movie) }

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
    override suspend fun addPopularityMovies(movies: List<PopularMovieCacheDto>) =
        safeCall { movieDao.insertPopularMovies(movies) }

    override suspend fun getPopularityMovies(limit: Int) =
        safeCall { movieDao.getPopularityMovies(limit = limit) }

    override suspend fun clearPopularMovies() =
        safeCall { movieDao.clearPopularMovies() }

    override suspend fun addRecentlyReleasedMovies(movies: List<RecentReleasedMovieCacheDto>) =
        safeCall { movieDao.insertRecentlyReleasedMovies(movies) }

    override suspend fun getRecentlyReleasedMovies(limit: Int) =
        safeCall { movieDao.getRecentlyReleasedMovies(limit) }

    override suspend fun clearRecentlyReleasedMovies() =
        safeCall { movieDao.clearRecentlyReleasedMovies() }

    override suspend fun addUpcomingMovies(movies: List<UpcomingMovieCacheDto>) =
        safeCall { movieDao.insertUpcomingMovies(movies) }

    override suspend fun getUpcomingMovies(limit: Int) =
        safeCall { movieDao.getUpcomingMovies(limit) }

    override suspend fun clearUpcomingMovies() =
        safeCall { movieDao.clearUpcomingMovies() }

    override suspend fun addMatchesYourVibeMovies(movies: List<MatchesYourVibeMovieCacheDto>) =
        safeCall { movieDao.insertMatchesYourVibeMovies(movies) }

    override suspend fun getMatchesYourVibeMovies(limit: Int) =
        safeCall { movieDao.getMatchesYourVibeMovies(limit) }

    override suspend fun clearMatchesYourVibeMovies() =
        safeCall { movieDao.clearMatchesYourVibeMovies() }

    override suspend fun addRecentlyViewedMovie(movie: RecentlyViewedMovieCacheDto) =
        safeCall { movieDao.insertRecentlyViewedMovie(movie) }

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