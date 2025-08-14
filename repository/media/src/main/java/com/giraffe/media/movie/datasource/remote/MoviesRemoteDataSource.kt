package com.giraffe.media.movie.datasource.remote

import com.giraffe.media.dto.ReviewDto
import com.giraffe.media.movie.datasource.remote.dto.MovieDto
import com.giraffe.media.movie.datasource.remote.dto.MovieGenreDto
import com.giraffe.media.movie.datasource.remote.dto.RatingRequest

interface MoviesRemoteDataSource {

    suspend fun getMovieById(movieId: Int): MovieDto

    suspend fun getMoviesByName(movieName: String, page: Int): List<MovieDto>

    suspend fun getMovieRecommendations(movieId: Int, page: Int): List<MovieDto>

    suspend fun getMovieGenres(): List<MovieGenreDto>

    suspend fun getMoviesByGenre(genreId: Int, page: Int): List<MovieDto>

    suspend fun addRating(movieId: Int, request: RatingRequest)

    suspend fun discoverMovies(
        genreId: List<Int>? = null,
        keywords: String? = null,
        sortBy: String = "popularity.desc",
        page: Int
    ): List<MovieDto>

    suspend fun getUserMovieRating(movieId: Int): Float?

    suspend fun getRatedMovies(accountId: Int): List<MovieDto>

    suspend fun getMovieReviews(movieId: Int, page: Int): List<ReviewDto>

    suspend fun getPopularityMovies(page: Int): List<MovieDto>

    suspend fun getRecentlyReleasedMovies(page: Int): List<MovieDto>

    suspend fun getUpcomingMovies(page: Int): List<MovieDto>

    suspend fun getMovieTrailerUrl(movieId: Int): String

    suspend fun deleteMovieRating(movieId: Int)
}