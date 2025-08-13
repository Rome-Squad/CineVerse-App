package com.giraffe.media.match.usecase

import com.giraffe.media.match.repository.MatchRepository
import com.giraffe.media.movie.entity.Movie
import javax.inject.Inject

class GetMatchingMoviesUseCase @Inject constructor(
    private val repository: MatchRepository
) {
    suspend operator fun invoke(
        genreIds: String,
        minRuntime: Int? = null,
        maxRuntime: Int? = null,
        earliestFirstAirDate: String? = null,
        latestFirstAirDate: String? = null
    ): List<Movie> {
        return repository.getMatchingMovies(
            genreIds = genreIds,
            minRuntime = minRuntime,
            maxRuntime = maxRuntime,
            earliestFirstAirDate = earliestFirstAirDate,
            latestFirstAirDate = latestFirstAirDate
        )
    }
}
