package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MoviesRepository
import javax.inject.Inject

class GetRecentlyReleasedMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(page: Int, limit: Int = 10) =
        repository.getRecentlyReleased(page, limit)
}