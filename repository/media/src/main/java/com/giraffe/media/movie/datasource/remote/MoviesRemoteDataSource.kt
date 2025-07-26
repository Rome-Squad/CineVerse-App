package com.giraffe.media.movie.datasource.remote

import  com.giraffe.media.movie.datasource.remote.dto.MovieDto
import  com.giraffe.media.movie.datasource.remote.dto.MovieGenreDto
import  com.giraffe.media.movie.datasource.remote.dto.MovieReviewDto
import  com.giraffe.media.movie.datasource.remote.dto.RatingRequest

interface MoviesRemoteDataSource {

    suspend fun getMovieById(
        movieId: Int
    ): MovieDto

    suspend fun getMoviesByName(movieName: String, page: Int): List<MovieDto>

    suspend fun getMovieGenres(): List<MovieGenreDto>

    suspend fun getMoviesByGenre(genreId: Int, page: Int): List<MovieDto>

    suspend fun getMovieReviews(movieId: Int): List<MovieReviewDto>

    suspend fun getMovieRecommendations(movieId: Int, page: Int): List<MovieDto>

    suspend fun addRating(movieId: Int, sessionId: String, request: RatingRequest)
    suspend fun getUserMovieRating(
        movieId: Int,
        sessionId: String
    ): Float
    suspend fun deleteRating(movieId: Int, sessionId: String)

    suspend fun getPopularityMovies(page: Int): List<MovieDto>

    suspend fun getRecentlyReleasedMovies(page: Int): List<MovieDto>

    suspend fun getUpcomingMovies(page: Int): List<MovieDto>
}