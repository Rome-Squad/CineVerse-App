package com.giraffe.media.movie.usecase.recentlyViewed

import com.giraffe.media.movie.repository.MovieRepository
import javax.inject.Inject

class ObserveRecentlyViewedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(page: Int = 1, pageSize: Int = 10) =
        movieRepository.observeRecentlyViewed(page, pageSize)
}