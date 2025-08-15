package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import javax.inject.Inject

class GetMatchesYourVibeMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    fun getLocalMatchesYourVibe(limit: Int = 10) =
        movieRepository.getLocalMatchesYourVibe(limit)

    suspend fun getRemoteMatchesYourVibe(page: Int = 1, limit: Int) =
        movieRepository.getRemoteMatchesYourVibe(page = page, limit = limit)
}