package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MoviesRepository
import javax.inject.Inject

class GetRecentlyViewedMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    operator fun invoke() = repository.getRecentlyViewed()
}