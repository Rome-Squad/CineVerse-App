package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.repository.MoviesRepository
import javax.inject.Inject

class GetUpcomingMoviesUseCase @Inject constructor(private val repository: MoviesRepository) {
    suspend operator fun invoke(page: Int, limit: Int = 10) =
        repository.getUpcomingMovies(page, limit)
}