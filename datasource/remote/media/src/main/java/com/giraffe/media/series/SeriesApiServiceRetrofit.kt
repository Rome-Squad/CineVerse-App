package com.giraffe.media.series

import com.giraffe.media.series.datasource.remote.dto.SeriesDetailsDto
import com.giraffe.media.series.response.GenresResponse
import com.giraffe.media.series.response.SeriesResponse
import com.giraffe.media.series.response.SeriesReviewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SeriesApiServiceRetrofit {

    @GET("search/tv")
    suspend fun getSeriesByName(
        @Query("query") name: String,
        @Query("page") page: Int = 1
    ): Response<SeriesResponse>

    @GET("discover/tv")
    suspend fun getSeriesByGenre(
        @Query("with_genres") genreId: String
    ): Response<SeriesResponse>

    @GET("genre/tv/list")
    suspend fun getGenres(): Response<GenresResponse>

    @GET("tv/{seriesId}")
    suspend fun getSeriesDetails(
        @Path("seriesId") seriesId: Int
    ): Response<SeriesDetailsDto>

    @GET("tv/{seriesId}/reviews")
    suspend fun getSeriesReviews(
        @Path("seriesId") seriesId: Int,
        @Query("page") page: Int = 1
    ): Response<SeriesReviewsResponse>

    @GET("tv/{seriesId}/recommendations")
    suspend fun getSeriesRecommendations(
        @Path("seriesId") seriesId: Long,
        @Query("page") page: Int = 1
    ): Response<SeriesResponse>
}
