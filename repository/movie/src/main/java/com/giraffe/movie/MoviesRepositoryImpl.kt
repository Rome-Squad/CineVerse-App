package com.giraffe.movie

import com.giraffe.movie.datasource.local.MoviesLocalDataSource
import com.giraffe.movie.datasource.remote.MoviesRemoteDataSource
import com.giraffe.movies.entity.Movie
import com.giraffe.movies.entity.MovieGenre
import com.giraffe.movies.repository.MoviesRepository

class MoviesRepositoryImpl(
    private val cache: MoviesLocalDataSource,
    private val remote: MoviesRemoteDataSource
): MoviesRepository {
    override suspend fun searchMovieByName(movieName: String): List<Movie> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieGenres(): List<MovieGenre> {
        TODO("Not yet implemented")
    }

    override suspend fun getMoviesByGenre(genreId: Int): List<Movie> {
        TODO("Not yet implemented")
    }

    override suspend fun insertMovies(movie: List<Movie>) {
        TODO("Not yet implemented")
    }

    override suspend fun insertGenres(genres: List<MovieGenre>) {
        TODO("Not yet implemented")
    }

    override suspend fun clearCache() {
        TODO("Not yet implemented")
    }

}