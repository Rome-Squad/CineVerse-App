package com.giraffe.media.match.retrofit

import com.giraffe.media.match.datasource.MatchRemoteDataSource
import com.giraffe.media.movie.datasource.remote.dto.MovieDto
import com.giraffe.media.series.datasource.remote.dto.SeriesDto
import com.giraffe.media.util.safeCallRemote
import javax.inject.Inject

class MatchRemoteDataSourceImpl @Inject constructor(
    private val matchApiService: MatchApiService
) : MatchRemoteDataSource {

    override suspend fun getMatchingMovies(
        genreIds: String,
        minRuntime: Int?,
        maxRuntime: Int?,
        earliestDate: String?,
        latestDate: String?,
        moodId: String?
    ): List<MovieDto> = safeCallRemote {
        matchApiService.getMatchingMovies(
            genreIds = genreIds,
            minRuntime = minRuntime,
            maxRuntime = maxRuntime,
            earliestDate = earliestDate,
            latestDate = latestDate,
            moodId = moodId
        )
    }.results


    override suspend fun getMatchingSeries(
        genreIds: String,
        minRuntime: Int?,
        maxRuntime: Int?,
        earliestDate: String?,
        latestDate: String?,
        moodId: String?
    ): List<SeriesDto> = safeCallRemote {
        matchApiService.getMatchingSeries(
            genreIds = genreIds,
            minRuntime = minRuntime,
            maxRuntime = maxRuntime,
            earliestDate = earliestDate,
            latestDate = latestDate,
            moodId = moodId
        )
    }.results
}
