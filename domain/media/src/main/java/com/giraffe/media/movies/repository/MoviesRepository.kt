package com.giraffe.media.movies.repository

import com.giraffe.media.movies.entity.GenrePreference
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.entity.MovieGenre
import com.giraffe.media.movies.entity.MovieReview

interface MoviesRepository {

    suspend fun searchMovieByName(movieName: String): List<Movie>

    suspend fun getMovieGenres(genreIds: List<Int>): List<MovieGenre>

    suspend fun getMoviesGenres(): List<MovieGenre>

    suspend fun getMoviesByGenres(genreIds: List<Int>): List<Movie>

    suspend fun insertMovies(movie: List<Movie>)

    suspend fun insertGenres(genres: List<MovieGenre>)

    suspend fun setMovieRecent(
        movie: Movie,
        isRecent: Boolean
    )

    suspend fun clearCache()

    suspend fun getRecentlyMovies(): List<Movie>

    suspend fun clearRecentlyMovies()

    suspend fun getMovieDetails(movieId: Int): Movie

    suspend fun getMovieReviews(movieId: Int): List<MovieReview>

    suspend fun addRating(movieId: Int, ratingValue: Float)

    suspend fun getUserMovieRating(movieId: Int): Float

    suspend fun getUserGenrePreferences(): List<GenrePreference>

}
