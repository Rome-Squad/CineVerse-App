package com.giraffe.media.match.usecase

import com.giraffe.media.match.repository.MatchRepository
import com.giraffe.media.series.entity.Series
import javax.inject.Inject

class GetMatchingSeriesUseCase @Inject constructor(
    private val repository: MatchRepository
) {
    suspend operator fun invoke(
        genreIds: String,
        minRuntime: Int? = null,
        maxRuntime: Int? = null,
        earliestFirstAirDate: String? = null,
        latestFirstAirDate: String? = null
    ): List<Series> {
        return repository.getMatchingSeries(
            genreIds = genreIds,
            minRuntime = minRuntime,
            maxRuntime = maxRuntime,
            earliestFirstAirDate = earliestFirstAirDate,
            latestFirstAirDate = latestFirstAirDate
        )
    }
}
