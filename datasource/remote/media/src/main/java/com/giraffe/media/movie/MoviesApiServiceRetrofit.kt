package com.giraffe.media.movie

import com.giraffe.media.movie.datasource.remote.dto.*
import com.giraffe.media.movie.response.*
import retrofit2.Response
import retrofit2.http.*

interface MoviesApiServiceRetrofit {
    @GET("movie/{id}")
    suspend fun getMovieById(@Path("id") movieId: Int): Response<MovieDto>

    @GET("search/movie")
    suspend fun getMoviesByName(@Query("query") name: String): Response<MoviesListResponse>

    @GET("genre/movie/list")
    suspend fun getGenres(): Response<GenreResponse>

    @GET("discover/movie")
    suspend fun getMoviesByGenre(@Query("with_genres") genreId: String): Response<MoviesListResponse>

    @GET("movie/{id}/reviews")
    suspend fun getMovieReviews(@Path("id") movieId: Int): Response<ReviewsResponseDto>

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendations(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int
    ): Response<MoviesListResponse>

    @POST("movie/{movie_id}/rating")
    suspend fun rateMovie(
        @Path("movie_id") movieId: Int,
        @Query("guest_session_id") sessionId: String,
        @Body request: RatingRequest
    ): Response<Unit>

    @GET("movie/{movie_id}/account_states")
    suspend fun getMovieRating(
        @Path("movie_id") movieId: Int,
        @Query("guest_session_id") sessionId: String
    ):  Response<RatedMoviesResponse>
}