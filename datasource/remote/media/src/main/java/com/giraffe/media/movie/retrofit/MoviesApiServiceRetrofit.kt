package com.giraffe.media.movie.retrofit

import com.giraffe.media.movie.datasource.remote.dto.*
import com.giraffe.media.movie.response.*
import retrofit2.Response
import retrofit2.http.*

interface MoviesApiServiceRetrofit {

    @GET("$MOVIE_END_POINT/{$ID}")
    suspend fun getMovieById(@Path(ID) movieId: Int): Response<MovieDto>

    @GET(MOVIES_BY_NAME_URL)
    suspend fun getMoviesByName(@Query(QUERY) name: String): Response<MoviesListResponse>

    @GET(GENRES_URL)
    suspend fun getGenres(): Response<GenreResponse>

    @GET(MOVIES_BY_GENRE_URL)
    suspend fun getMoviesByGenre(@Query(WITH_GENRES) genreId: String): Response<MoviesListResponse>

    @GET("$MOVIE_END_POINT/{$ID}/$REVIEWS_END_POINT")
    suspend fun getMovieReviews(@Path(ID) movieId: Int): Response<ReviewsResponseDto>

    @GET("$MOVIE_END_POINT/{$MOVIE_ID}/$RECOMMENDATIONS")
    suspend fun getRecommendations(
        @Path(MOVIE_ID) movieId: Int,
        @Query(PAGE) page: Int
    ): Response<MoviesListResponse>

    @POST("$MOVIE_END_POINT/{$MOVIE_ID}/{$RATING}")
    suspend fun rateMovie(
        @Path(MOVIE_ID) movieId: Int,
        @Query(SESSION_ID) sessionId: String,
        @Body request: RatingRequest
    ): Response<Unit>

    @GET("$MOVIE_END_POINT/{$MOVIE_ID}/{$ACCOUNT_STATES}")
    suspend fun getMovieRating(
        @Path(MOVIE_ID) movieId: Int,
        @Query(SESSION_ID) sessionId: String
    ): Response<RatedMoviesResponse>

    companion object {
        const val MOVIE_END_POINT = "movie"
        const val REVIEWS_END_POINT = "reviews"
        const val MOVIES_BY_NAME_URL = "search/movie"
        const val GENRES_URL = "genre/movie/list"
        const val MOVIES_BY_GENRE_URL = "discover/movie"
        const val WITH_GENRES = "with_genres"
        const val PAGE = "page"
        const val QUERY = "query"
        const val RECOMMENDATIONS = "recommendations"
        const val RATING = "rating"
        const val ACCOUNT_STATES = "account_states"
        const val ID = "id"
        const val MOVIE_ID = "movie_id"
        const val SESSION_ID = "session_id"
    }
}