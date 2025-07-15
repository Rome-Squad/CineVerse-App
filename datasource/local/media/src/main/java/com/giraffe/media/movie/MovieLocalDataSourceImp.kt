package  com.giraffe.media.movie

import MovieDao
import  com.giraffe.media.movie.datasource.local.MoviesLocalDataSource
import  com.giraffe.media.movie.datasource.local.cacheDto.MovieCacheDto
import  com.giraffe.media.movie.datasource.local.cacheDto.MovieGenreCacheDto


class MovieLocalDataSourceImp(
    private val movieDao: MovieDao
) : MoviesLocalDataSource {

    override suspend fun getMovieById(movieId: Int): MovieCacheDto = movieDao.getMovieById(movieId)

    override suspend fun insertMovies(movies: List<MovieCacheDto>) = movieDao.insertMovies(movies)

    override suspend fun insertMovieGenres(movieGenres: List<MovieGenreCacheDto>) =
        movieDao.insertMovieGenres(movieGenres)

    override suspend fun updateMovie(movie: MovieCacheDto) = movieDao.updateMovie(movie)

    override suspend fun getMoviesByName(movieName: String): List<MovieCacheDto> =
        movieDao.getMovieByName(movieName)

    override suspend fun getMoviesByGenre(genreId: Int): List<MovieCacheDto> =
        movieDao.getMoviesByGenre(genreId)


    override suspend fun getMovieGenres(genreIds: List<Int>): List<MovieGenreCacheDto> {
       return movieDao.getMovieGenres(genreIds)
    }

    override suspend fun getMoviesGenres(): List<MovieGenreCacheDto> = movieDao.getMoviesGenres()

    override suspend fun getMovieGenreById(genreId: Int): MovieGenreCacheDto =
        movieDao.getMovieGenreById(genreId)

    override suspend fun getMovieGenresById(ids: List<Int>): List<MovieGenreCacheDto> =
        movieDao.getMovieGenresByIds(ids)

    override suspend fun clearMovieCache() = movieDao.clearMovieCache()

    override suspend fun clearRecentlyMovies() = movieDao.clearRecentlyMovies()

    override suspend fun getRecentlyMovies(): List<MovieCacheDto> = movieDao.getRecentlyMovies()

    override suspend fun clearMovieGenreCache() = movieDao.clearMovieGenreCache()
}

