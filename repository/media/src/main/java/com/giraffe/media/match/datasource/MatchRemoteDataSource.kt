package com.giraffe.media.match.datasource

import com.giraffe.media.movie.datasource.remote.dto.MovieDto
import com.giraffe.media.series.datasource.remote.dto.SeriesDto


interface MatchRemoteDataSource {
    suspend fun getMatchingMovies(
        genreIds: String,
        minRuntime: Int?,
        maxRuntime: Int?,
        earliestFirstAirDate: String?,
        latestFirstAirDate: String?
    ): List<MovieDto>

    suspend fun getMatchingSeries(
        genreIds: String,
        minRuntime: Int?,
        maxRuntime: Int?,
        earliestFirstAirDate: String?,
        latestFirstAirDate: String?
    ): List<SeriesDto>

}