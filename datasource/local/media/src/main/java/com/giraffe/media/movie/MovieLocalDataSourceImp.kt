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

    override suspend fun upsertMovies(movies: List<MovieCacheDto>) = safeCall {
        movieDao.upsertMovies(movies)
    }

    override suspend fun upsertMovie(movie: MovieCacheDto) = safeCall {
        movieDao.upsertMovie(movie)
    }

    override suspend fun incrementInteractionCountForGenres(genreIds: List<Int>) = safeCall {
        movieDao.incrementInteractionCountForGenres(genreIds)
    }

    override suspend fun getMovieById(movieId: Int) = safeCall {
        val movie = movieDao.getMovieById(movieId)
        movie
    }

    override suspend fun getMoviesByName(movieName: String) =
        safeCall {
            movieDao.getMovieByName(movieName)
        }

    override suspend fun getPopularityMovies(limit: Int) = safeCall {
        movieDao.getPopularityMovies(limit = limit)
    }

    override suspend fun getRecentlyReleasedMovies(limit: Int) = safeCall {
        movieDao.getRecentlyReleasedMovies(limit)
    }

    override suspend fun getMovieGenresById(ids: List<Int>) = safeCall {
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

    override suspend fun getMovieGenres(genreIds: List<Int>) = safeCall {
        movieDao.getMovieGenres(genreIds)
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