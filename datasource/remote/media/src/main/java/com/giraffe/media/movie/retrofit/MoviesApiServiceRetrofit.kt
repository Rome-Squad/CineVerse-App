package com.giraffe.media.movie.retrofit

import com.giraffe.media.movie.datasource.remote.dto.MovieDto
import com.giraffe.media.movie.datasource.remote.dto.RatedMoviesResponse
import com.giraffe.media.movie.datasource.remote.dto.RatingRequest
import com.giraffe.media.movie.response.GenreResponse
import com.giraffe.media.movie.response.MovieRateResponse
import com.giraffe.media.movie.response.MoviesListResponse
import com.giraffe.media.response.AllReviewsResponse
import com.giraffe.media.response.TrailerResponse
import com.giraffe.media.util.NetworkConstants.ACCOUNT_ID_PATH
import com.giraffe.media.util.NetworkConstants.ACCOUNT_STATES
import com.giraffe.media.util.NetworkConstants.GENRES_URL
import com.giraffe.media.util.NetworkConstants.ID
import com.giraffe.media.util.NetworkConstants.MOVIES_BY_GENRE_URL
import com.giraffe.media.util.NetworkConstants.MOVIES_BY_NAME_URL
import com.giraffe.media.util.NetworkConstants.MOVIE_END_POINT
import com.giraffe.media.util.NetworkConstants.MOVIE_ID
import com.giraffe.media.util.NetworkConstants.NEEDS_SESSION
import com.giraffe.media.util.NetworkConstants.NOW_PLAYING_MOVIES_URL
import com.giraffe.media.util.NetworkConstants.PAGE
import com.giraffe.media.util.NetworkConstants.POPULAR_MOVIES_URL
import com.giraffe.media.util.NetworkConstants.QUERY
import com.giraffe.media.util.NetworkConstants.RATED_END_POINT
import com.giraffe.media.util.NetworkConstants.RATING
import com.giraffe.media.util.NetworkConstants.RECOMMENDATIONS
import com.giraffe.media.util.NetworkConstants.REVIEWS_END_POINT
import com.giraffe.media.util.NetworkConstants.UPCOMING_MOVIES_URL
import com.giraffe.media.util.NetworkConstants.USER_END_POINT
import com.giraffe.media.util.NetworkConstants.VIDEOS_END_POINT
import com.giraffe.media.util.NetworkConstants.WITH_GENRES
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiServiceRetrofit {

    @GET("$MOVIE_END_POINT/{$ID}")
    suspend fun getMovieById(
        @Path(ID) movieId: Int
    ): Response<MovieDto>

    @GET(MOVIES_BY_NAME_URL)
    suspend fun getMoviesByName(
        @Query(QUERY) name: String,
        @Query(PAGE) page: Int
    ): Response<MoviesListResponse<MovieDto>>

    @GET(GENRES_URL)
    suspend fun getGenres(): Response<GenreResponse>

    @GET(MOVIES_BY_GENRE_URL)
    suspend fun getMoviesByGenre(
        @Query(WITH_GENRES) genreId: String,
        @Query(PAGE) page: Int
    ): Response<MoviesListResponse<MovieDto>>

    @GET("$MOVIE_END_POINT/{$ID}/$REVIEWS_END_POINT")
    suspend fun getMovieReviews(
        @Path(ID) movieId: Int,
        @Query(PAGE) page: Int
    ): Response<AllReviewsResponse>

    @GET("$MOVIE_END_POINT/{$MOVIE_ID}/$RECOMMENDATIONS")
    suspend fun getRecommendations(
        @Path(MOVIE_ID) movieId: Int,
        @Query(PAGE) page: Int
    ): Response<MoviesListResponse<MovieDto>>

    @POST("$MOVIE_END_POINT/{$MOVIE_ID}/$RATING")
    @Headers("$NEEDS_SESSION: true")
    suspend fun rateMovie(
        @Path(MOVIE_ID) movieId: Int,
        @Body request: RatingRequest
    ): Response<Unit>

    @DELETE("$MOVIE_END_POINT/{$MOVIE_ID}/$RATING")
    @Headers("$NEEDS_SESSION: true")
    suspend fun deleteMovieRating(
        @Path(MOVIE_ID) movieId: Int,
    ): Response<Unit>

    @GET("$MOVIE_END_POINT/{$MOVIE_ID}/$ACCOUNT_STATES")
    @Headers("$NEEDS_SESSION: true")
    suspend fun getMovieRating(
        @Path(MOVIE_ID) movieId: Int,
    ): Response<RatedMoviesResponse>

    @GET(POPULAR_MOVIES_URL)
    suspend fun getPopularMovies(
        @Query(PAGE) page: Int
    ): Response<MoviesListResponse<MovieDto>>

    @GET(UPCOMING_MOVIES_URL)
    suspend fun getUpcomingMovies(
        @Query(PAGE) page: Int
    ): Response<MoviesListResponse<MovieDto>>

    @GET(NOW_PLAYING_MOVIES_URL)
    suspend fun getRecentlyReleasedMovies(
        @Query(PAGE) page: Int
    ): Response<MoviesListResponse<MovieDto>>

    @GET("$MOVIE_END_POINT/{$MOVIE_ID}/$VIDEOS_END_POINT")
    suspend fun getMovieTrailerUrl(
        @Path(MOVIE_ID) movieId: Int
    ): Response<MoviesListResponse<TrailerResponse>>

    @GET("$USER_END_POINT/{$ACCOUNT_ID_PATH}/$RATED_END_POINT")
    @Headers("$NEEDS_SESSION: true")
    suspend fun getRatedMovies(
        @Path(ACCOUNT_ID_PATH) accountId: Int
    ): Response<MoviesListResponse<MovieDto>>

    @GET("$MOVIE_END_POINT/{$MOVIE_ID}/account_states")
    @Headers("$NEEDS_SESSION: true")
    suspend fun getUserMovieRating(
        @Path(MOVIE_ID) movieId: Int
    ): Response<MovieRateResponse>
}