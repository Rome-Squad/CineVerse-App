package com.giraffe.media.match.repository

import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.series.entity.Series

interface MatchRepository {
    suspend fun getMatchingMovies(
        genreIds: String,
        minRuntime: Int?,
        maxRuntime: Int?,
        earliestFirstAirDate: String?,
        latestFirstAirDate: String?,
        moodId: String? = null,
    ): List<Movie>

    suspend fun getMatchingSeries(
        genreIds: String,
        minRuntime: Int?,
        maxRuntime: Int?,
        earliestFirstAirDate: String?,
        latestFirstAirDate: String?,
        moodId: String? = null,
    ): List<Series>
}