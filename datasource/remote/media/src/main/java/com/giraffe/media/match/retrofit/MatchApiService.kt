package com.giraffe.media.match.retrofit

import com.giraffe.media.match.response.MatchResponse
import com.giraffe.media.movie.datasource.remote.dto.MovieDto
import com.giraffe.media.series.datasource.remote.dto.SeriesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MatchApiService {

    @GET("discover/movie")
    suspend fun getMatchingMovies(
        @Query("with_genres") genreIds: String,
        @Query("with_runtime.gte") minRuntime: Int?,
        @Query("with_runtime.lte") maxRuntime: Int?,
        @Query("primary_release_date.gte") earliestFirstAirDate: String?,
        @Query("primary_release_date.lte") latestFirstAirDate: String?,
        @Query("with_keywords") moodId: String? = null,
    ): Response<MatchResponse<MovieDto>>

    @GET("discover/tv")
    suspend fun getMatchingSeries(
        @Query("with_genres") genreIds: String,
        @Query("with_runtime.gte") minRuntime: Int?,
        @Query("with_runtime.lte") maxRuntime: Int?,
        @Query("primary_release_date.gte") earliestFirstAirDate: String?,
        @Query("primary_release_date.lte") latestFirstAirDate: String?,
        @Query("with_keywords") moodId: String? = null,
    ): Response<MatchResponse<SeriesDto>>

}