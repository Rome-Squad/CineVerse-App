package com.giraffe.media.movie.usecase.recentlyReleased

import com.giraffe.media.movie.repository.MovieRepository
import javax.inject.Inject

class ObserveRecentlyReleasedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    fun invoke(limit: Int = 10) =
        movieRepository.observeRecentlyReleased(limit = limit)
}