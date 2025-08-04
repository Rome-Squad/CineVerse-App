package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.repository.MoviesRepository
import javax.inject.Inject

class GetRecentlyReleasedMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(page: Int, limit: Int = 10, useRemoteOnly: Boolean = false) =
        repository.getRecentlyReleasedMovies(page, limit, useRemoteOnly)
}