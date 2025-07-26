package com.giraffe.media.movie.retrofit

import com.giraffe.media.movie.datasource.remote.dto.MovieDto
import com.giraffe.media.movie.datasource.remote.dto.RatedMoviesResponse
import com.giraffe.media.movie.datasource.remote.dto.RatingRequest
import com.giraffe.media.movie.datasource.remote.dto.ReviewsResponseDto
import com.giraffe.media.movie.response.GenreResponse
import com.giraffe.media.movie.response.MoviesListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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

    @HTTP(method = "DELETE", path = "$MOVIE_END_POINT/{$MOVIE_ID}/$RATING", hasBody = false)
    suspend fun deleteMovieRating(
        @Path(MOVIE_ID) movieId: Int,
        @Query(SESSION_ID) sessionId: String
    ): Response<Unit>

    @GET(POPULAR_MOVIES_URL)
    suspend fun getPopularMovies(
        @Query(PAGE) page: Int
    ): Response<MoviesListResponse>

    @GET(UPCOMING_MOVIES_URL)
    suspend fun getUpcomingMovies(
        @Query(PAGE) page: Int
    ): Response<MoviesListResponse>

    @GET(NOW_PLAYING_MOVIES_URL)
    suspend fun getRecentlyReleasedMovies(
        @Query(PAGE) page: Int
    ): Response<MoviesListResponse>

    companion object {
        const val MOVIE_END_POINT = "movie"
        const val REVIEWS_END_POINT = "reviews"
        const val MOVIES_BY_NAME_URL = "search/movie"
        const val GENRES_URL = "genre/movie/list"
        const val MOVIES_BY_GENRE_URL = "discover/movie"
        const val POPULAR_MOVIES_URL = "movie/popular"
        const val UPCOMING_MOVIES_URL = "movie/upcoming"
        const val NOW_PLAYING_MOVIES_URL = "movie/now_playing"
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