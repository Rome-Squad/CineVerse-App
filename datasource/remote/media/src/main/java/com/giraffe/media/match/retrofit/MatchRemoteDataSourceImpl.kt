package com.giraffe.media.match.retrofit

import com.giraffe.media.match.datasource.MatchRemoteDataSource
import com.giraffe.media.movie.datasource.remote.dto.MovieDto
import com.giraffe.media.series.datasource.remote.dto.SeriesDto
import com.giraffe.media.util.RetrofitRequestBuilder.Companion.safeCall
import javax.inject.Inject

class MatchRemoteDataSourceImplRetrofit @Inject constructor(
    private val matchApiService: MatchApiService
) : MatchRemoteDataSource {

    override suspend fun getMatchingMovies(
        genreIds: String,
        minRuntime: Int?,
        maxRuntime: Int?,
        earliestFirstAirDate: String?,
        latestFirstAirDate: String?
    ): List<MovieDto> = safeCall {
        matchApiService.getMatchingMovies(
            genreIds = genreIds,
            minRuntime = minRuntime,
            maxRuntime = maxRuntime,
            earliestFirstAirDate = earliestFirstAirDate,
            latestFirstAirDate = latestFirstAirDate
        )
    }.results


    override suspend fun getMatchingSeries(
        genreIds: String,
        minRuntime: Int?,
        maxRuntime: Int?,
        earliestFirstAirDate: String?,
        latestFirstAirDate: String?
    ): List<SeriesDto> = safeCall {
        matchApiService.getMatchingSeries(
            genreIds = genreIds,
            minRuntime = minRuntime,
            maxRuntime = maxRuntime,
            earliestFirstAirDate = earliestFirstAirDate,
            latestFirstAirDate = latestFirstAirDate
        )
    }.results
}
