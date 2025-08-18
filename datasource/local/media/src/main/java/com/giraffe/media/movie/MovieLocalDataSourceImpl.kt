package  com.giraffe.media.movie

import com.giraffe.media.movie.dao.MovieDao
import com.giraffe.media.movie.datasource.local.MoviesLocalDataSource
import com.giraffe.media.movie.datasource.local.cacheDto.MovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.MovieGenreCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.MovieWithRecentlyViewedAt
import com.giraffe.media.movie.mapper.toMatchesYourVibeMovieCacheDto
import com.giraffe.media.movie.mapper.toPopularMovieCacheDto
import com.giraffe.media.movie.mapper.toRecentReleasedMovieCacheDto
import com.giraffe.media.movie.mapper.toRecentlyViewedMovieCacheDto
import com.giraffe.media.movie.mapper.toUpcomingMovieCacheDto
import com.giraffe.media.util.safeCall
import com.giraffe.media.util.safeFlow
import javax.inject.Inject


class MovieLocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao
) : MoviesLocalDataSource {
    override suspend fun addMovie(movie: MovieCacheDto) {
        movieDao.insertMovie(movie)
    }

    // region Movie Genres
    override suspend fun syncMovieGenres(movieGenres: List<MovieGenreCacheDto>) {
        safeCall {
            movieGenres.forEach { genre ->
                movieDao.getGenreById(genre.id)?.let {
                    movieDao.updateGenreNameOnly(genre.id, genre.name)
                } ?: movieDao.insertMovieGenre(genre)
            }
        }
    }

    override suspend fun incrementInteractionCountForGenres(genreIds: List<Int>) =
        safeCall { movieDao.incrementInteractionCountForGenres(genreIds) }

    override fun getMovieGenresByIds(ids: List<Int>) =
        safeFlow { movieDao.getMovieGenresByIds(ids) }

    override fun getMoviesGenres() =
        safeFlow { movieDao.getMoviesGenres() }

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

    override fun getPopularityMovies(limit: Int) =
        safeFlow { movieDao.getPopularityMovies(limit = limit) }

    override suspend fun clearPopularMovies() =
        safeCall { movieDao.clearPopularMovies() }

    override suspend fun addRecentlyReleasedMovies(movies: List<MovieCacheDto>) {
        safeCall {
            movieDao.insertMovies(movies)
            movieDao.insertRecentlyReleasedMovies(movies.map(MovieCacheDto::toRecentReleasedMovieCacheDto))
        }
    }

    override fun getRecentlyReleasedMovies(limit: Int) =
        safeFlow { movieDao.getRecentlyReleasedMovies(limit) }

    override suspend fun clearRecentlyReleasedMovies() =
        safeCall { movieDao.clearRecentlyReleasedMovies() }

    override suspend fun addUpcomingMovies(movies: List<MovieCacheDto>) {
        safeCall {
            movieDao.insertMovies(movies)
            movieDao.insertUpcomingMovies(movies.map(MovieCacheDto::toUpcomingMovieCacheDto))
        }
    }

    override fun getUpcomingMovies(limit: Int) =
        safeFlow { movieDao.getUpcomingMovies(limit) }

    override suspend fun clearUpcomingMovies() =
        safeCall { movieDao.clearUpcomingMovies() }

    override suspend fun addMatchesYourVibeMovies(movies: List<MovieCacheDto>) {
        safeCall {
            movieDao.insertMovies(movies)
            movieDao.insertMatchesYourVibeMovies(movies.map(MovieCacheDto::toMatchesYourVibeMovieCacheDto))
        }
    }

    override fun getMatchesYourVibeMovies(limit: Int) =
        safeFlow { movieDao.getMatchesYourVibeMovies(limit) }

    override suspend fun clearMatchesYourVibeMovies() =
        safeCall { movieDao.clearMatchesYourVibeMovies() }

    override suspend fun addRecentlyViewedMovie(movie: MovieCacheDto) {
        safeCall {
            addMovie(movie)
            movieDao.insertRecentlyViewedMovie(movie.toRecentlyViewedMovieCacheDto())
        }
    }

    override fun getRecentlyViewedMovies(page: Int, pageSize: Int) =
        safeFlow { movieDao.getRecentlyViewedMovies(page, pageSize) }

    override suspend fun getAllRecentlyViewedMovies(): List<MovieWithRecentlyViewedAt> =
        safeCall { movieDao.getAllRecentlyViewedMovies() }


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