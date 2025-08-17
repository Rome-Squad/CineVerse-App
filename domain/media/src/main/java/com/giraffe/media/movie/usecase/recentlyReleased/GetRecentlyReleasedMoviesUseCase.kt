package com.giraffe.media.movie.usecase.recentlyReleased

import com.giraffe.media.movie.repository.MovieRepository
import javax.inject.Inject

class GetRecentlyReleasedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun invoke(page: Int, limit: Int) =
        movieRepository.getRecentlyReleased(page = page, limit = limit)
}