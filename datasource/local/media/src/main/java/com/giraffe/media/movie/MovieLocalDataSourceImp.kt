package  com.giraffe.media.movie

import MovieDao
import  com.giraffe.media.movie.datasource.local.MoviesLocalDataSource
import  com.giraffe.media.movie.model.cacheDto.MovieCacheDto
import  com.giraffe.media.movie.model.cacheDto.MovieGenreCacheDto
import com.giraffe.media.util.safeCall


class MovieLocalDataSourceImp(
    private val movieDao: MovieDao
) : MoviesLocalDataSource {
    override suspend fun getMovieById(movieId: Int): MovieCacheDto = safeCall {
        movieDao.getMovieById(movieId)
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

    override suspend fun getMoviesByName(movieName: String): List<MovieCacheDto> = safeCall {
        movieDao.getMovieByName(movieName)
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