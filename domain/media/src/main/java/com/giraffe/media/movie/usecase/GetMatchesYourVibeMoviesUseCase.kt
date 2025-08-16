package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import javax.inject.Inject

class GetMatchesYourVibeMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun invoke(page: Int = 1, limit: Int = 10) =
        movieRepository.getMatchesYourVibe(page = page, limit = limit)
}