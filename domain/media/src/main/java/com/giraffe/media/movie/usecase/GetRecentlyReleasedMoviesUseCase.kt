package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import javax.inject.Inject

class GetRecentlyReleasedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    fun getLocalRecentlyReleased(limit: Int = 10) =
        movieRepository.getLocalRecentlyReleased(limit = limit)

    suspend fun getRemoteRecentlyReleased(page: Int, limit: Int = 20) =
        movieRepository.getRemoteRecentlyReleased(page = page, limit = limit)
}