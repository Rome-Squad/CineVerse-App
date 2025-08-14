package com.giraffe.media.movie.retrofit

import com.giraffe.media.movie.datasource.remote.dto.MovieDto
import com.giraffe.media.movie.datasource.remote.dto.RatingRequest
import com.giraffe.media.movie.response.GenreResponse
import com.giraffe.media.movie.response.MovieRateResponse
import com.giraffe.media.movie.response.MoviesListResponse
import com.giraffe.media.response.AllReviewsResponse
import com.giraffe.media.response.TrailerResponse
import com.giraffe.media.util.NetworkConstants.ACCOUNT
import com.giraffe.media.util.NetworkConstants.ACCOUNT_ID
import com.giraffe.media.util.NetworkConstants.ACCOUNT_STATES
import com.giraffe.media.util.NetworkConstants.DISCOVER
import com.giraffe.media.util.NetworkConstants.GENRES
import com.giraffe.media.util.NetworkConstants.DISCOVER_MOVIE_URL
import com.giraffe.media.util.NetworkConstants.GENRES_URL
import com.giraffe.media.util.NetworkConstants.ID
import com.giraffe.media.util.NetworkConstants.LIST
import com.giraffe.media.util.NetworkConstants.MOVIE
import com.giraffe.media.util.NetworkConstants.MOVIES
import com.giraffe.media.util.NetworkConstants.MOVIES_BY_NAME_URL
import com.giraffe.media.util.NetworkConstants.MOVIE_END_POINT
import com.giraffe.media.util.NetworkConstants.MOVIE_ID
import com.giraffe.media.util.NetworkConstants.NEEDS_SESSION
import com.giraffe.media.util.NetworkConstants.NOW_PLAYING
import com.giraffe.media.util.NetworkConstants.PAGE
import com.giraffe.media.util.NetworkConstants.POPULAR
import com.giraffe.media.util.NetworkConstants.QUERY
import com.giraffe.media.util.NetworkConstants.RATED
import com.giraffe.media.util.NetworkConstants.RATING
import com.giraffe.media.util.NetworkConstants.RECOMMENDATIONS
import com.giraffe.media.util.NetworkConstants.REVIEWS
import com.giraffe.media.util.NetworkConstants.SEARCH
import com.giraffe.media.util.NetworkConstants.UPCOMING
import com.giraffe.media.util.NetworkConstants.VIDEOS
import com.giraffe.media.util.NetworkConstants.REVIEWS_END_POINT
import com.giraffe.media.util.NetworkConstants.SORT_BY
import com.giraffe.media.util.NetworkConstants.SORT_BY_DEFAULT
import com.giraffe.media.util.NetworkConstants.UPCOMING_MOVIES_URL
import com.giraffe.media.util.NetworkConstants.USER_END_POINT
import com.giraffe.media.util.NetworkConstants.VIDEOS_END_POINT
import com.giraffe.media.util.NetworkConstants.WITH_GENRES
import com.giraffe.media.util.NetworkConstants.WITH_KEYWORDS
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiServiceRetrofit {

    @GET("$MOVIE/{$ID}")
    suspend fun getMovieById(
        @Path(ID) movieId: Int
    ): Response<MovieDto>

    @GET("$SEARCH/$MOVIE")
    suspend fun getMoviesByName(
        @Query(QUERY) name: String,
        @Query(PAGE) page: Int
    ): Response<MoviesListResponse<MovieDto>>

    @GET("$GENRES/$MOVIE/$LIST")
    suspend fun getGenres(): Response<GenreResponse>

    @GET("$DISCOVER/$MOVIE")
    suspend fun discoverMovies(
        @Query(WITH_GENRES) genreId: String? = null,
        @Query(WITH_KEYWORDS) keywords: String? = null,
        @Query(SORT_BY) sortBy: String? = SORT_BY_DEFAULT,
        @Query(PAGE) page: Int
    ): Response<MoviesListResponse<MovieDto>>

    @GET("$MOVIE/{$ID}/$REVIEWS")
    suspend fun getMovieReviews(
        @Path(ID) movieId: Int,
        @Query(PAGE) page: Int
    ): Response<AllReviewsResponse>

    @GET("$MOVIE/{$MOVIE_ID}/$RECOMMENDATIONS")
    suspend fun getRecommendations(
        @Path(MOVIE_ID) movieId: Int,
        @Query(PAGE) page: Int
    ): Response<MoviesListResponse<MovieDto>>

    @POST("$MOVIE/{$MOVIE_ID}/$RATING")
    @Headers("$NEEDS_SESSION: true")
    suspend fun rateMovie(
        @Path(MOVIE_ID) movieId: Int,
        @Body request: RatingRequest
    ): Response<Unit>

    @DELETE("$MOVIE/{$MOVIE_ID}/$RATING")
    @Headers("$NEEDS_SESSION: true")
    suspend fun deleteMovieRating(
        @Path(MOVIE_ID) movieId: Int,
    ): Response<Unit>

    @GET("$MOVIE/$POPULAR")
    suspend fun getPopularMovies(
        @Query(PAGE) page: Int
    ): Response<MoviesListResponse<MovieDto>>

    @GET("$MOVIE/$UPCOMING")
    suspend fun getUpcomingMovies(
        @Query(PAGE) page: Int
    ): Response<MoviesListResponse<MovieDto>>

    @GET("$MOVIE/$NOW_PLAYING")
    suspend fun getRecentlyReleasedMovies(
        @Query(PAGE) page: Int
    ): Response<MoviesListResponse<MovieDto>>

    @GET("$MOVIE/{$MOVIE_ID}/$VIDEOS")
    suspend fun getMovieTrailerUrl(
        @Path(MOVIE_ID) movieId: Int
    ): Response<MoviesListResponse<TrailerResponse>>

    @GET("$ACCOUNT/{$ACCOUNT_ID}/$RATED/$MOVIES")
    @Headers("$NEEDS_SESSION: true")
    suspend fun getRatedMovies(
        @Path(ACCOUNT_ID) accountId: Int
    ): Response<MoviesListResponse<MovieDto>>

    @GET("$MOVIE/{$MOVIE_ID}/$ACCOUNT_STATES")
    @Headers("$NEEDS_SESSION: true")
    suspend fun getUserMovieRating(
        @Path(MOVIE_ID) movieId: Int
    ): Response<MovieRateResponse>
}