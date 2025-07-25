package  com.giraffe.media.movie

import com.giraffe.media.movie.dao.MovieDao
import com.giraffe.media.movie.datasource.local.MoviesLocalDataSource
import com.giraffe.media.movie.datasource.local.cacheDto.MovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.MovieGenreCacheDto
import com.giraffe.media.util.safeCall


class MovieLocalDataSourceImp(
    private val movieDao: MovieDao
) : MoviesLocalDataSource {
    override suspend fun getMovieById(movieId: Int): MovieCacheDto? = safeCall {
        val movie = movieDao.getMovieById(movieId)
        movie
    }

    override suspend fun insertMovies(movies: List<MovieCacheDto>) = safeCall {
        movieDao.insertMovies(movies)
    }

    override suspend fun insertMovieGenres(movieGenres: List<MovieGenreCacheDto>) = safeCall {
        movieDao.insertMovieGenres(movieGenres)
    }

    override suspend fun updateMovie(movie: MovieCacheDto) = safeCall {
        movieDao.updateMovie(movie)
    }

    override suspend fun getMoviesByName(movieName: String, page: Int): List<MovieCacheDto> =
        safeCall {
            movieDao.getMovieByName(movieName, page)
        }

    override suspend fun getMoviesByGenre(genreId: Int): List<MovieCacheDto> = safeCall {
        movieDao.getMoviesByGenre(genreId)
    }

    override suspend fun getMovieGenres(genreIds: List<Int>): List<MovieGenreCacheDto> = safeCall {
        movieDao.getMovieGenres(genreIds)
    }

    override suspend fun getMoviesGenres(): List<MovieGenreCacheDto> = safeCall {
        movieDao.getMoviesGenres()
    }

    override suspend fun getMovieGenreById(genreId: Int): MovieGenreCacheDto = safeCall {
        movieDao.getMovieGenreById(genreId)
    }

    override suspend fun getMovieGenresById(ids: List<Int>): List<MovieGenreCacheDto> = safeCall {
        movieDao.getMovieGenresByIds(ids)
    }

    override suspend fun clearMovieCache() = safeCall {
        movieDao.clearMovieCache()
    }

    override suspend fun clearRecentlyMovies() = safeCall {
        movieDao.clearRecentlyMovies()
    }

    override suspend fun getRecentlyMovies(): List<MovieCacheDto> = safeCall {
        movieDao.getRecentlyMovies()
    }

    override suspend fun incrementInteractionCountForGenres(genreIds: List<Int>) = safeCall {
        movieDao.incrementInteractionCountForGenres(genreIds)
    }

    override suspend fun clearMovieGenreCache() = safeCall {
        movieDao.clearMovieGenreCache()
    }
}