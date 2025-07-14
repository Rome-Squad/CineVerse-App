package com.giraffe.movie.datasource.remote

import com.giraffe.movie.datasource.remote.dto.MovieDetailsDto
import com.giraffe.movie.datasource.remote.dto.MovieDto
import com.giraffe.movie.datasource.remote.dto.MovieGenreDto
import com.giraffe.movie.datasource.remote.dto.MovieReviewDto
import com.giraffe.movie.datasource.remote.dto.RatingRequest

interface MoviesRemoteDataSource {

    suspend fun getMovieById(
        movieId: Int
    ): MovieDetailsDto

    suspend fun getMoviesByName(movieName: String): List<MovieDto>

    suspend fun getMovieGenres(): List<MovieGenreDto>

    suspend fun getMoviesByGenre(genreId: Int): List<MovieDto>

    suspend fun getMovieReviews(movieId: Int): List<MovieReviewDto>

    suspend fun addRating(movieId: Int, sessionId: String, request: RatingRequest)
    suspend fun getUserMovieRating(
        movieId: Int,
        guestSessionId: String
    ): Float
}