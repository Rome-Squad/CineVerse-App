package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import javax.inject.Inject

class GetRecentlyReleasedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(page: Int = 1, limit: Int = 10) =
        movieRepository.getRecentlyReleased(page = page, limit = limit)
}