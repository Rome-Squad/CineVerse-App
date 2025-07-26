package com.giraffe.media.series.retrofit

import com.giraffe.media.movie.datasource.remote.dto.RatedMoviesResponse
import com.giraffe.media.movie.datasource.remote.dto.RatingRequest
import com.giraffe.media.series.datasource.remote.dto.SeriesDetailsDto
import com.giraffe.media.series.response.GenresResponse
import com.giraffe.media.series.response.SeriesResponse
import com.giraffe.media.series.response.SeriesReviewsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SeriesApiServiceRetrofit {

    @GET(SEARCH_TV)
    suspend fun getSeriesByName(
        @Query(QUERY) name: String,
        @Query(PAGE) page: Int = 1
    ): Response<SeriesResponse>

    @GET(DISCOVER_TV)
    suspend fun getSeriesByGenre(
        @Query(WITH_GENRES) genreId: String
    ): Response<SeriesResponse>

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
    ): Response<SeriesReviewsResponse>

    @GET("$TV/{$SERIES_ID}/$RECOMMENDATIONS")
    suspend fun getSeriesRecommendations(
        @Path(SERIES_ID) seriesId: Long,
        @Query(PAGE) page: Int = 1
    ): Response<SeriesResponse>


    @GET(POPULAR_TV_URL)
    suspend fun getPopularSeries(
        @Query(PAGE) page: Int = 1
    ): Response<SeriesResponse>

    @GET(ON_THE_AIR_TV_URL)
    suspend fun getRecentlyReleasedSeries(
        @Query(PAGE) page: Int = 1
    ): Response<SeriesResponse>

    @GET(TOP_RATED_TV_URL)
    suspend fun getTopRatedSeries(
        @Query(PAGE) page: Int = 1
    ): Response<SeriesResponse>

    @POST("$TV/{$SERIES_ID}/$RATING")
    suspend fun rateSeries(
        @Path(SERIES_ID) seriesId: Int,
        @Query(SESSION_ID) sessionId: String,
        @Body request: RatingRequest
    ): Response<Unit>


    @GET("$TV/{$SERIES_ID}/$ACCOUNT_STATES")
    suspend fun getSeriesRating(
        @Path(SERIES_ID) seriesId: Int,
        @Query(SESSION_ID) sessionId: String
    ): Response<RatedMoviesResponse>

    @HTTP(method = "DELETE", path = "$TV/{$SERIES_ID}/$RATING", hasBody = false)
    suspend fun deleteSeriesRating(
        @Path(SERIES_ID) seriesId: Int,
        @Query(SESSION_ID) sessionId: String
    ): Response<Unit>

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

        const val RATING = "rating"
        const val ACCOUNT_STATES = "account_states"
        const val SESSION_ID = "session_id"
    }
}
