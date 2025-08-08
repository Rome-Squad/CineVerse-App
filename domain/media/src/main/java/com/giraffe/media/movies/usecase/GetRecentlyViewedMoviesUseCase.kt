package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.repository.MoviesRepository
import javax.inject.Inject

class GetRecentlyViewedMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    operator fun invoke() = repository.getRecentlyViewedMovies()
}