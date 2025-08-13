package com.giraffe.media.series.retrofit

import com.giraffe.media.movie.datasource.remote.dto.RatingRequest
import com.giraffe.media.movie.response.MovieRateResponse
import com.giraffe.media.response.AllReviewsResponse
import com.giraffe.media.response.TrailerResponse
import com.giraffe.media.series.datasource.remote.dto.SeriesDetailsDto
import com.giraffe.media.series.datasource.remote.dto.SeriesDto
import com.giraffe.media.series.response.GenresResponse
import com.giraffe.media.series.response.SeriesResponse
import com.giraffe.media.util.NetworkConstants.ACCOUNT_ID_PATH
import com.giraffe.media.util.NetworkConstants.NEEDS_SESSION
import com.giraffe.media.util.NetworkConstants.RATING
import com.giraffe.media.util.NetworkConstants.TV_END_POINT
import com.giraffe.media.util.NetworkConstants.USER_END_POINT
import com.giraffe.media.util.NetworkConstants.VIDEOS_END_POINT
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SeriesApiServiceRetrofit {

    @GET(SEARCH_TV)
    suspend fun getSeriesByName(
        @Query(QUERY) name: String,
        @Query(PAGE) page: Int = 1
    ): Response<SeriesResponse<SeriesDto>>

    @GET(DISCOVER_TV)
    suspend fun getSeriesByGenre(
        @Query(WITH_GENRES) genreId: String,
        @Query(PAGE) page: Int = 1
    ): Response<SeriesResponse<SeriesDto>>

    @GET(GENRE_TV_LIST)
    suspend fun getGenres(): Response<GenresResponse>

    @GET("$TV/{$SERIES_ID}")
    suspend fun getSeriesDetails(
        @Path(SERIES_ID) seriesId: Int
    ): Response<SeriesDetailsDto>

    @GET("$TV/{$SERIES_ID}/$REVIEWS")
    suspend fun getSeriesReviews(
        @Path(SERIES_ID) seriesId: Int,
        @Query(PAGE) page: Int = 1
    ): Response<AllReviewsResponse>

    @GET("$TV/{$SERIES_ID}/$RECOMMENDATIONS")
    suspend fun getSeriesRecommendations(
        @Path(SERIES_ID) seriesId: Int,
        @Query(PAGE) page: Int = 1
    ): Response<SeriesResponse<SeriesDto>>


    @GET(POPULAR_TV_URL)
    suspend fun getPopularSeries(
        @Query(PAGE) page: Int = 1
    ): Response<SeriesResponse<SeriesDto>>

    @GET(ON_THE_AIR_TV_URL)
    suspend fun getRecentlyReleasedSeries(
        @Query(PAGE) page: Int = 1
    ): Response<SeriesResponse<SeriesDto>>

    @GET(TOP_RATED_TV_URL)
    suspend fun getTopRatedSeries(
        @Query(PAGE) page: Int = 1
    ): Response<SeriesResponse<SeriesDto>>

    @GET("$TV/{$SERIES_ID}/$VIDEOS_END_POINT")
    suspend fun getSeriesTrailerUrl(
        @Path(SERIES_ID) seriesId: Int
    ): Response<SeriesResponse<TrailerResponse>>


    @GET("$USER_END_POINT/{$ACCOUNT_ID_PATH}/$RATED/$TV")
    @Headers("$NEEDS_SESSION: true")
    suspend fun getRatedSeries(
        @Path(ACCOUNT_ID_PATH) accountId: Int
    ): Response<SeriesResponse<SeriesDto>>


    @DELETE("$TV/{$SERIES_ID}/$RATING")
    @Headers("$NEEDS_SESSION: true")
    suspend fun deleteSeriesRating(
        @Path(SERIES_ID) seriesId: Int,
    ): Response<Unit>


    @POST("$TV_END_POINT/{$SERIES_ID}/$RATING")
    @Headers("$NEEDS_SESSION: true")
    suspend fun rateSeries(
        @Path(SERIES_ID) seriesId: Int,
        @Body request: RatingRequest
    ): Response<Unit>

    @GET("$TV/{$SERIES_ID}/account_states")
    @Headers("$NEEDS_SESSION: true")
    suspend fun getUserSeriesRating(
        @Path(SERIES_ID) seriesId: Int
    ): Response<MovieRateResponse>

    companion object {
        const val SEARCH_TV = "search/tv"
        const val DISCOVER_TV = "discover/tv"
        const val GENRE_TV_LIST = "genre/tv/list"
        const val TV = "tv"
        const val POPULAR_TV_URL = "tv/popular"
        const val ON_THE_AIR_TV_URL = "tv/on_the_air"
        const val TOP_RATED_TV_URL = "tv/top_rated"

        const val QUERY = "query"
        const val PAGE = "page"
        const val WITH_GENRES = "with_genres"

        const val SERIES_ID = "seriesId"
        const val REVIEWS = "reviews"
        const val RECOMMENDATIONS = "recommendations"
        const val RATED = "rated"

        const val SERIES_ID_PATH = "series_id"
    }
}
