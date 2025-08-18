package com.giraffe.media.movie.usecase.matchesYourVibe

import com.giraffe.media.movie.repository.MovieRepository
import javax.inject.Inject

class ObserveMatchesYourVibeMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(limit: Int = 10) =
        movieRepository.observeMatchesYourVibe(limit)
}