package com.giraffe.media.movie.usecase.matchesYourVibe

import com.giraffe.media.movie.repository.MovieRepository
import javax.inject.Inject

class GetMatchesYourVibeMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(page: Int, limit: Int) =
        movieRepository.getMatchesYourVibe(page = page, limit = limit)
}