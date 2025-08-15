package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import javax.inject.Inject

class GetUpcomingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    fun getLocalUpcoming(limit: Int = 10) =
        movieRepository.getLocalUpcoming(limit = limit)

    suspend fun getRemoteUpcoming(page: Int = 1, limit: Int = 20) =
        movieRepository.getRemoteUpcoming(page = page, limit = limit)
}