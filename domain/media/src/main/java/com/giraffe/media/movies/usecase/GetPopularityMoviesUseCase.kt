package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.repository.MoviesRepository
import javax.inject.Inject

class GetPopularityMoviesUseCase @Inject constructor(private val repository: MoviesRepository) {
    suspend operator fun invoke(page: Int = 1, limit: Int = 10, useRemoteOnly: Boolean = false) =
        repository.getPopularityMovies(page, limit, useRemoteOnly)
}