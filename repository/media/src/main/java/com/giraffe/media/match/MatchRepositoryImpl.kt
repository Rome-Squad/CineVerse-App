package com.giraffe.media.match

import com.giraffe.media.match.datasource.MatchRemoteDataSource
import com.giraffe.media.match.repository.MatchRepository
import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.movie.mapper.toEntity
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.mapper.toEntity
import com.giraffe.media.utils.safeCall
import jakarta.inject.Inject

class MatchRepositoryImpl @Inject constructor(
    private val remoteDataSource: MatchRemoteDataSource,
) : MatchRepository {
    override suspend fun getMatchingMovies(
        genreIds: String,
        minRuntime: Int?,
        maxRuntime: Int?,
        earliestFirstAirDate: String?,
        latestFirstAirDate: String?
    ): List<Movie> = safeCall {
        remoteDataSource.getMatchingMovies(
            genreIds, minRuntime, maxRuntime, earliestFirstAirDate, latestFirstAirDate
        ).map { it.toEntity() }
    }

    override suspend fun getMatchingSeries(
        genreIds: String,
        minRuntime: Int?,
        maxRuntime: Int?,
        earliestFirstAirDate: String?,
        latestFirstAirDate: String?
    ): List<Series> = safeCall {
        remoteDataSource.getMatchingSeries(
            genreIds, minRuntime, maxRuntime, earliestFirstAirDate, latestFirstAirDate
        ).map { it.toEntity() }
    }
}